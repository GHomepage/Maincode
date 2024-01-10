# Exploring Skill Transfer of Mandarin-based Speech Misunderstanding on Chinese VPA
This project shows the core code and main experimental data (the dataset and all experimental results are saved in: [Alldata](https://github.com/GHomepage/Alldata)) of the paper "Exploring Skill Transfer of Mandarin-based Speech Misunderstanding on Chinese VPA".

# DuerOS Test Client
Baidu has previously released a set of APIs that allow developers to deploy DuerOS on any IoT device. We use the [DuerOS DCS Android SDK](http://open.duer.baidu.com/doc/dueros-conversational-service/sdk_markdown), which is a development kit of intelligent device voice interaction software based on the DCS protocol launched by DuerOS, to build a client. DCS protocol is divided into transport layer, message format layer and end capability layer. DuerOS SDK supports voice wake-up, basic voice input and output, and more. 

Through this client, we use DuerOS black box transcription service to test the Mandarin speech recognition ability of Baidu VPA. We developed two skills for returning transcription results. Audio as client voice input, the request is directly routed to the skill server. With custom code, the transcription results are automatically saved in a file.


**A. Dependencies**
- Android Studio 2.2+
- Gradle 2.2+
- Android SDK 5.0+
- JDK 1.6+

 **B. Run Client**
 
- Firstly, please register the smart device on the [DuerOS device console](https://developer.dueros.baidu.com/didp/product/listview), click [here](https://developer.dueros.baidu.com/doc/overall/console-guide_markdown) for the specific process. After successful registration, you can obtain the CLIENT_ID of the device, and then replace CLIENT_ID in the SDK.
 - Complete the corresponding configuration of authorization functions on the [DuerOS console](https://developer.dueros.baidu.com/didp/product/listview)
 - On the Authorization Callback page, click Security Settings.
 - Add authorization callback page in security settings 'https://xiaodu.baidu.com/saiya/device/oauthCallback?client_id= xxxxxx' (xxxxx represents the client_id applied by the developer). Add 'xiaodu.baidu.com' to the root domain binding.


# Skills Crawler Program
We use Python Selenium automation program to crawl 2,746 skills in the Baidu skills market, including skill name, developer type, developer name, sample sentences and skill description.


**A. Dependencies**
- Mysql & DBeaver
- Python 10+
- Chrome Browser (Download the [chromedriver](http://npm.taobao.org/mirrors/chromedriver/) corresponding to the browser version, Place chromedriver. exe in the Google directory and Python installation directory)

**B. Usage Steps**
- Open install-dependencies.bat. Wait until all is done.
- Modify the code in crawler. py and connect to the MySQL database.
- Now, you can start crawling by opening start.bat.


# PinYin Conversion 
The primary task for similarity comparison of skill names is to convert all Chinese to tonal PinYin. We use Python's pypinyin library to implement it, the code is as follows.

``` Python
df = pd.read_excel("xxxxx")   # Folder path
df.head()
pinyin_name = []
for i in df['Skill_name']:
    result = pypinyin.pinyin(i, style=pypinyin.STYLE_TONE)
    result_ = [i[0] for i in result]
    result2 = result_[0] + ' ' + ' '.join(result_[1:])
    result3 = ''.join([i[0].upper() for i in result_])
    print(result2, i, sep=' ')
    pinyin_name.append(result2)

 df['PinYin'] = pinyin_name
df.head()
```

# Main Experimental Data
This section displays the main data obtained from our experiment.

**A. DuerOS Transcription Results**

We tested a total of 1,088 pronunciations composed of different syllables and 13,863 high-frequency words composed of these pronunciations. The recognition error rates of DuerOS are 5.88% and 7.39%, we provide all incorrect results.

**B. Compare**

In order to find out whether the system misunderstandings in DuSmart Speaker also exist in other Chinese VPAs, we choose another three popular device "Xiaomi AI Speaker", "Alibaba TmallGenie" and "HUAWEI Celia" for comparative verification. The following table shows the recognition results, the number represents the percentage that make the same mistake as DuerOS.

<table>
<thead>
  <tr>
    <td colspan="2">Baidu DuSmart</td>
    <td>Xiaomi Speaker</td>
    <td>Alibaba TmallGenie</td>
    <td>HUAWEI Celia</td>
  </tr>
</thead>
<tbody>
  <tr>
    <td rowspan="2">Pronunciation</td>
    <td>Incorrect(31)</td>
    <td>87.10%(27)</td>
    <td>90.32%(28)</td>
    <td>83.87%(26)</td>
  </tr>
  <tr>
    <td>Unrecognized(27)</td>
    <td>77.78%(21)</td>
    <td>66.67%(18)</td>
    <td>81.48%(22)</td>
  </tr>
  <tr>
    <td rowspan="2">Words</td>
    <td>Incorrect(858)</td>
    <td>67.83%(582)</td>
    <td>66.20%(568)</td>
    <td>56.76%(487)</td>
  </tr>
  <tr>
    <td>Unrecognized(33)</td>
    <td>51.52%(17)</td>
    <td>3.03%(1)</td>
    <td>54.55%(18)</td>
  </tr>
  <tr>
    <td colspan="2">Overall Similarity</td>
    <td>68.18%</td>
    <td>64.59%</td>
    <td>58.27%</td>
  </tr>
</tbody>
</table>


**C. User Study**

We recruit 300 volunteers with at least one Baidu VPA on [Credamo](https://www.credamo.com//#/) to fill in the questionnaires we designed. We submit the user study results table.
