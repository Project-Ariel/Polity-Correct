
# Polity-Correct Setup

# Setting up a new Android Studio workspace

------------------------------------------------------------------------

## Instructions below for setting up project **c:\\dev\\Polity-Correct**. Change accordingly!

1.  Download android studio - android-studio-2021.2.1.14-windows
    [Here](https://developer.android.com/studio?gclid=CjwKCAjwj42UBhAAEiwACIhADvIFBMeIzud_oGeKv0pjvq84LroFB_QTxNCQRjYRrCeNV44SWYvHkhoC-uQQAvD_BwE&gclsrc=aw.ds)
    (/or another way) and install.

2.  Make sure you have java_1.8 with JDK version 8u311 on your computer.
    (no need for JRE to use android studio)

  -   File -\> Project Structure -\> Modules
  ![alt text](https://github.com/Project-Ariel/Polity-Correct/blob/main/reademe_png/Project%20structure.png)

3.  Open Polity-Correct with android Studio.

-   File -\> Project Structure -\> Project
 ![alt text](https://github.com/Project-Ariel/Polity-Correct/blob/main/reademe_png/Project%20structure%20gradle.png)

-   Wait until all Gradle files complete installation (It\'s can take
    some time, you can see it in the down-right corner).

4.  Add configuration. (after all Gradle files are completely installed)

-   Run -\> Edit configuration

![alt text](https://github.com/Project-Ariel/Polity-Correct/blob/main/reademe_png/Edit%20configuration.png)

5.  Add Device.

-   Run -\> Select Device.. -\> Device Manager -\> Create device.

select Pixel 4
![alt text](https://github.com/Project-Ariel/Polity-Correct/blob/main/reademe_png/device%20manager1.png)

Select **R** (download if you need)
![alt text](https://github.com/Project-Ariel/Polity-Correct/blob/main/reademe_png/device%20manager2.png)

Next -\> Finish.

6.  Build

7.  Delete Polity-Correct -\> app -\> google-services.json file.

8.  Connect android studio to Firebase.

    -   Create a new project on your firebase account.
    -   Click Tools -\> Firebase to open the Assistant window.
    -   Click to expand one of the listed features (for example,
        Analytics), then click connect to Firebase.
    -   Choose your project on firebase and connect. (This will
        automatically generate the file google-services.json)

    ![alt text](https://github.com/Project-Ariel/Polity-Correct/blob/main/reademe_png/Connect%20to%20Firebase.png)

9.  Build and run. (Wait for the device to turn on)

## Setting Firebase (or add your mail to Polity-Correct Firebase to get permissions)

------------------------------------------------------------------------

1.  Create a new project on your firebase account.

2.  Create Firestore Database Project.

3.  Download your project\'s \"Firebase Admin SDK\" JSON (for python).

-   Project setting -\> Service accounts -\> select Python -\> Generate
    new private key -\> Generate key. (Save this JSON file somewhere on
    your computer)

![alt text](https://github.com/Project-Ariel/Polity-Correct/blob/main/reademe_png/Firebase%20Admin%20SDK.png)

4.Open the JSON file with a notepad and copy all its contents.

5.Open the file Polity-Correct -\> Setup -\> FB2NewFB.py and copy the
json contents into \"client_firebase_json_info\" (line 8).

![alt text](https://github.com/Project-Ariel/Polity-Correct/blob/main/reademe_png/client_firebase_json_info.png)

6.  Run - **pip install firebase-admin** & **pip install xmltodict**

7.  **Run FB2NewFB.py** in some python3.9 workspace.
 
## Scheduling Odata Updater

------------------------------------------------------------------------
 
1.  Open the JSON file your downloaded in the last step with a notepad
    and copy all its contents.

2.  Open the file Polity-Correct -\> Odata script -\> OdataUpdateData.py
    and copy the json contents into \"client_firebase_json_info\" (line
    13).

![alt text](https://github.com/Project-Ariel/Polity-Correct/blob/main/reademe_png/client_firebase_json_info_Odata.png)

3.  Open **Task Scheduler** -\> Action -\> Create Task \* -\> General,
    Choose action name. \* -\> Triggers -\> New. Choose youe scheduling
    details. \* -\> Actions -\> New. Upload **OdataUpdateData.py** file
    in Program/script. \* OK
:::
