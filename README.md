temi SDK
========

![temi](temi.jpg)

For more information please see [the website][1].


Download
--------

Download the latest AAR from [Maven Central][2] or grab via Gradle:
```groovy
implementation 'com.robotemi:sdk-usa:0.10.42'   // for USA version
implementation 'com.robotemi:sdk-china:0.10.42' // for Chinese version
```

or Maven:
```xml
<!-- for USA version -->
<dependency>
  <groupId>com.robotemi</groupId>
  <artifactId>sdk-usa</artifactId>
  <version>0.10.42</version>
</dependency>

<!-- for Chinese version -->
<dependency>
  <groupId>com.robotemi</groupId>
  <artifactId>sdk-china</artifactId>
  <version>0.10.42</version>
</dependency>
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].


Install your app on temi
--------

You can begin by downloading ADB (Android Debug Bridge) on the computer you wish to develop for temi. Please follow [this][3] tutorial on how to download and set up ADB on your computer.

Once you have adb set up on your computer, you can run your code on temi by:

Step 1: Make sure you are connected to the same WiFi network as your robot. 

Step 2: on temi - go to Settings -> temi Developer Tools -> tap on ADB Port Opening.

Step 3: on computer - Using the IP address on the top right of temi’s screen you can connect to the robot and test your code. Type this command into the terminal/command prompt. “adb connect <IP_ADDRESS>:5555”


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
[snap]: https://oss.sonatype.org/content/repositories/snapshots/