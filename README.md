temi SDK
========

![temi](temi.jpg)

For more information please see [the website][1].


Download
--------

Download the latest AAR from [Maven Central][2] or grab via Gradle:
```groovy
implementation 'com.robotemi:sdk:0.10.53'   
```

or Maven:
```xml
<dependency>
  <groupId>com.robotemi</groupId>
  <artifactId>sdk</artifactId>
  <version>0.10.53</version>
</dependency>
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].


Installing Applications
--------

You can begin by downloading ADB (Android Debug Bridge) on the computer you wish to develop for temi. Please follow [this][3] tutorial on how to download and set up ADB on your computer.

Once you have ADB set up on your computer, you can run your code on temi by:

Step 1: Make sure you are connected to the same WiFi network as your robot. 

Step 2: On temi - go to Settings -> temi Developer Tools -> tap on ADB Port Opening.

Step 3: On computer - Using the IP address on the top right of temi’s screen you can connect to the robot and test your code. In order to establish a connection with the robot, type “```adb connect <IP_ADDRESS>:5555```” in Terminal on Mac or Command Prompt on Windows.


Uninstalling Applications
--------

Once you have ADB set up on your computer, you can uninstall your app on temi by:

Step 1: Make sure you are connected to the same WiFi network as your robot. 

Step 2: On temi - go to Settings -> temi Developer Tools -> tap on ADB Port Opening.

Step 3: On computer - Using the IP address on the top right of temi’s screen you can connect to the robot and uninstall your app. In order to establish a connection with the robot, type “```adb connect <IP_ADDRESS>:5555```” in Terminal/Command Prompt.

Step 4: Type "```adb uninstall PACKAGE_NAME```" in Terminal/Command Prompt. If you are not sure what your package name is, you can check from within your Android Project.


Documentation
--------

* [Getting Started][4]
* [中文文档][5]

License
-------

    Copyright 2019 temi USA inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[1]: https://www.robotemi.com/developers/
[2]: https://search.maven.org/search?q=g:com.robotemi
[3]: https://www.xda-developers.com/install-adb-windows-macos-linux/
[4]: https://github.com/robotemi/sdk/wiki
[5]: https://github.com/robotemi/sdk/wiki/Home_chn
[snap]: https://oss.sonatype.org/content/repositories/snapshots/
