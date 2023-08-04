import mysql.connector
from mysql.connector import Error

# Input the sql database details here
HOST_NAME = "localhost"
USER_NAME = "root"
USER_PASSWORD = "123"
DB_NAME = "CrawlSkill"


# Connect to the MySQL database
def create_server_connection(host_name=HOST_NAME, user_name=USER_NAME, user_password=USER_PASSWORD):
    connection = None
    try:
        connection = mysql.connector.connect(
            host=host_name,
            user=user_name,
            passwd=user_password
        )
        print("MySQL Server connection successful")
    except Error as err:
        print(f"Err: {err}")

    return connection

# Create the database
def create_database(connection, query):
    cursor = connection.cursor()
    try:
        cursor.execute(query)
        print("Database created successfully")
    except Error as err:
        print(f"Error: '{err}'")


# Connect to the CrawlSkill database
def create_db_connection(host_name=HOST_NAME, user_name=USER_NAME, user_password=USER_PASSWORD, db_name=DB_NAME):
    connection = None
    try:
        connection = mysql.connector.connect(
            host=host_name,
            user=user_name,
            passwd=user_password,
            database=db_name
        )
        print("MySQL Database connection successful")
    except Error as err:
        print(f"Error: '{err}'")

    return connection

def execute_query(connection, query):
    cursor = connection.cursor()
    try:
        cursor.execute(query)
        connection.commit()
        print("Query successful")
    except Error as err:
        print(f"Error: '{err}'")
