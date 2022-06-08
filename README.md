---
jupyter:
  colab:
    authorship_tag: ABX9TyM1Cil4UekoM03fuK2nRAjl
    collapsed_sections:
    - Y-GiTgqeSidp
    - De55i8arSWHL
    include_colab_link: true
    name: PolityCorrect_Setup.ipynb
  kernelspec:
    display_name: Python 3
    name: python3
  language_info:
    name: python
  nbformat: 4
  nbformat_minor: 0
---

::: {.cell .markdown colab_type="text" id="view-in-github"}
`<a href="https://colab.research.google.com/github/roei-birger/roei-birger/blob/main/PolityCorrect_Setup.ipynb" target="_parent">`{=html}`<img src="https://colab.research.google.com/assets/colab-badge.svg" alt="Open In Colab"/>`{=html}`</a>`{=html}
:::

::: {.cell .markdown id="Y-GiTgqeSidp"}
# Polity-Correct Setup
:::

::: {.cell .markdown id="De55i8arSWHL"}
# Setting up a new Android Studio workspace

------------------------------------------------------------------------

## Instructions below for setting up project **c:\\dev\\Polity-Correct**. Change accordingly! {#instructions-below-for-setting-up-project-cdevpolity-correct-change-accordingly}

1.  Download android studio - android-studio-2021.2.1.14-windows
    [Here](https://developer.android.com/studio?gclid=CjwKCAjwj42UBhAAEiwACIhADvIFBMeIzud_oGeKv0pjvq84LroFB_QTxNCQRjYRrCeNV44SWYvHkhoC-uQQAvD_BwE&gclsrc=aw.ds)
    (/or another way) and install.

2.  Make sure you have java_1.8 with JDK version 8u311 on your computer.
    (no need for JRE to use android studio)

-   File -\> Project Structure -\> Modules ![Project
    structure.png](vertopal_82e32a34038e4284a95a0a9b1564b519/dea5d7e14bbc657e26c189205900e041315ba4b0.png)

1.  Open Polity-Correct with android Studio.

-   File -\> Project Structure -\> Project ![Project structure
    gradle.png](vertopal_82e32a34038e4284a95a0a9b1564b519/75662aa1b366ffd1ceb51660bb520887584e116b.png)

-   Wait until all Gradle files complete installation (It\'s can take
    some time, you can see it in the down-right corner).

1.  Add configuration. (after all Gradle files are completely installed)

-   Run -\> Edit configuration

![Edit
configuration.png](vertopal_82e32a34038e4284a95a0a9b1564b519/d21b81feb2d575314bbc602c34ceb6b0bc91136b.png)

1.  Add Device.

-   Run -\> Select Device.. -\> Device Manager -\> Create device.

select Pixel 4 ![device
manager1.png](vertopal_82e32a34038e4284a95a0a9b1564b519/428084e9e98be1a14d2815b9f25a490590c3b7d2.png)

Select **R** (download if you need) ![device
manager2.png](vertopal_82e32a34038e4284a95a0a9b1564b519/d0f8934b0e62d547cb1eb469e019153b34a14bf2.png)

Next -\> Finish.

1.  Build

2.  Delete Polity-Correct -\> app -\> google-services.json file.

3.  Connect android studio to Firebase.

    -   Create a new project on your firebase account.
    -   Click Tools -\> Firebase to open the Assistant window.
    -   Click to expand one of the listed features (for example,
        Analytics), then click connect to Firebase.
    -   Choose your project on firebase and connect. (This will
        automatically generate the file google-services.json)

    ![Connect to
    Firebase.png](vertopal_82e32a34038e4284a95a0a9b1564b519/9a9e7a1072aaf07279b3f8f32a0682101bea844b.png)

4.  Build and run. (Wait for the device to turn on)
:::

::: {.cell .markdown id="kPc0ggeXTht9"}
\# Setting Firebase (or add your mail to Polity-Correct Firebase to get
permissions)

------------------------------------------------------------------------
:::

::: {.cell .markdown id="IQksH8ZLYJYP"}
1.  Create a new project on your firebase account.

2.  Create Firestore Database Project.

3.  Download your project\'s \"Firebase Admin SDK\" JSON (for python).

-   Project setting -\> Service accounts -\> select Python -\> Generate
    new private key -\> Generate key. (Save this JSON file somewhere on
    your computer)

![Firebase Admin
SDK.png](vertopal_82e32a34038e4284a95a0a9b1564b519/46279b009ee2b2eba5065ce877bdfa7ab60f2387.png)

3.Open the JSON file with a notepad and copy all its contents.

4.Open the file Polity-Correct -\> Setup -\> FB2NewFB.py and copy the
json contents into \"client_firebase_json_info\" (line 8).

![client_firebase_json_info.png](vertopal_82e32a34038e4284a95a0a9b1564b519/de78b68352b0b9c65876e18ffb9a0bab491d2301.png)

1.  Run - **pip install firebase-admin** & **pip install xmltodict**

2.  **Run FB2NewFB.py** in some python3.9 workspace.
:::

::: {.cell .markdown id="VbWtLNB-e60x"}
## \# Scheduling Odata Updater {#-scheduling-odata-updater}
:::

::: {.cell .markdown id="v2f7JG7jfku0"}
1.  Open the JSON file your downloaded in the last step with a notepad
    and copy all its contents.

2.  Open the file Polity-Correct -\> Odata script -\> OdataUpdateData.py
    and copy the json contents into \"client_firebase_json_info\" (line
    13).

![client_firebase_json_info_Odata.png](vertopal_82e32a34038e4284a95a0a9b1564b519/fc1671d7664c9584510961d1301be7c20be7421c.png)

1.  Open **Task Scheduler** -\> Action -\> Create Task \* -\> General,
    Choose action name. \* -\> Triggers -\> New. Choose youe scheduling
    details. \* -\> Actions -\> New. Upload **OdataUpdateData.py** file
    in Program/script. \* OK
:::
