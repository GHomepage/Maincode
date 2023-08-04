from selenium import webdriver
import selenium
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
import bs4
from bs4 import BeautifulSoup
import time
import setup_db
from setup_db import *
from sql_database import execute_query

# Connecting to broswer
options = Options()
options.add_argument("--disable-gpu")
s = Service("./drivers/chromedriver")
driver = webdriver.Chrome()
driver.get("https://dumall.baidu.com/skill")
time.sleep(10)

# Switch to another type of skill
def switch_skill_type():
    try:
        type_bar = driver.find_elements(By.CLASS_NAME, "caregory-all")[1]
        type_bar = type_bar.find_element(
        By.CSS_SELECTOR, ".active ~ .skill-item"
        ).click()
        time.sleep(2)
    except BaseException as error:
        print(f"Error: {error}")


# Select cards
def select_card():
    try:
        cards = driver.find_elements(
            By.CSS_SELECTOR, ".skill-list-con .skill-card-wrap"
        )
        for card in cards:
            card.click()
            el = WebDriverWait(driver, 20).until(
                EC.presence_of_element_located((By.CLASS_NAME, "lm-ui-model"))
            )
            dialog = driver.find_element(By.CSS_SELECTOR, ".lm-ui-model")

            extract_content(dialog)
            close = driver.find_element(By.CSS_SELECTOR, ".dialog-head .header-close")
            time.sleep(0.5)
            close.click()
    except BaseException as error:
        print(f"Error: {error}")
    next_page()
    time.sleep(0.7)

# Extract content
def extract_content(dialog):
    skill_corpus = []
    soup = BeautifulSoup(dialog.get_attribute("innerHTML"), "html.parser")
    skill_name = soup.select_one(".dialog-content .skill .skill-name").string
    dev_type, dev_name = soup.select_one(
        ".dialog-content .skill .skill-company"
    ).string.split("|")

    skill_corpus1 = soup.select(".dialog-content .skill-example-wrap .example")
    for i in skill_corpus1:
        skill_corpus.append(i.string)

    skill_desc = soup.select_one(".dialog-content .skill-detail-wrap .detail").string
    skill_corpus = " ".join(skill_corpus)
    store_in_sql_db(skill_name, dev_type, dev_name, skill_corpus, skill_desc)


# Next page
def next_page():
    try:
        next_btn = driver.find_element(
            By.CSS_SELECTOR, ".page .rc-pagination-next .rc-pagination-item-link"
        )
        next_btn.click()
    except BaseException as error:
        pass


# Save data to MySQL table
def store_in_sql_db(name, dev_type, dev_name, corpus, desc):
    store_query = f""" INSERT INTO {output_table} (skill_name, developer_type, developer_name, skill_corpus, skill_description)
    VALUES
    ('{name}', '{dev_type}', '{dev_name}', '{corpus}', '{desc}')
    """
    execute_query(connection, store_query)


try:
    assert "小度商城-开启你的智能生活" in driver.title
    print("Page Loaded Successfully")
    # Waiting for all skills to load out
    el = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.CLASS_NAME, "skill-list-container-pc"))
    )
    # invoking function... Need to modify
    select_card()
    select_card()
    select_card()
    select_card()
    select_card()
    print("Done Scrapping Sleeping in 10 secs")
    time.sleep(10)
    driver.close()
except AssertionError:
    print("Couldn't open page")
    driver.close()
