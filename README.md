FakeSMTP
========

FakeSMTP is a Free Fake SMTP Server with GUI for testing emails in applications easily.
It is written in Java.

<img src="http://nilhcem.github.com/FakeSMTP/images/screenshot_mac.png" width="664" height="463" />

Configure your application to use `localhost` as your SMTP server, and all
emails will be intercepted and displayed in this software.

FakeSMTP uses SubEthaSMTP: an easy-to-use server-side SMTP library for Java.

FakeSMTP is free to use for commercial and non-commercial projects and the
source code is provided.

It is licensed under the very free BSD or GPL license, whichever you prefer.


Requirements
------------

You need Java JVM 1.6 or newer installed on your machine.

If you are on a "Unix-like" machine (Mac, GNU/Linux, BSD...), you may have
to be "root" to start the port `25`, otherwise, try another port >= `1024`.


Usage
-----

The fakeSMTP.jar is auto-executable.
If your desktop environment supports it, you can directly double click
on the .jar file.
Otherwise, run the following command:

    java -jar fakeSMTP-VERSION.jar

If you want to specify the directory where emails will be saved when starting the application, you can use the `-o` argument:

    java -jar fakeSMTP-VERSION.jar -o output_directory_name
    java -jar fakeSMTP-VERSION.jar --output-dir output_directory_name

If you want to autostart the SMTP server at launch, you can use the `-s` argument:

    java -jar fakeSMTP-VERSION.jar -s
    java -jar fakeSMTP-VERSION.jar --start-server

If you want to autostart the SMTP server on a different port, you can also use the `-p` argument:

    java -jar fakeSMTP-VERSION.jar -s -p 2525
    java -jar fakeSMTP-VERSIon.jar --start-server --port 2525


Alternatives
------------

FakeSMTP was created because we couldn't find any free (as in freedom) and
cross-platform SMTP server with GUI for testing emails in applications or websites.
Listed below are some greats alternatives to Fake SMTP:


**[SMTP4dev](http://smtp4dev.codeplex.com/)**

* Nice features;
* Open source;
* Written for Windows in .net.


**[DevNull SMTP](http://www.aboutmyip.com/AboutMyXApp/DevNullSmtp.jsp)**

* Lightweight;
* Closed source;
* Written in Java 1.4 (cross platform).


Building it
-----------

You need to download and setup Maven.
Once installed, go to project directory and run the following command:

    mvn package -Dmaven.test.skip=true

This command will create an executable jar on the target folder.

We recommend you not to skip unit tests.

Once you know how to configure unit tests for this project, stop skipping them.


Running integration tests
-------------------------

To run integration tests, you will first need to launch the application
and start the server on port `2525`.

    java -jar fakeSMTP-VERSION.jar -p 2525 -s

You can then run the following command:

    mvn integration-test


Change the default port for unit/integration tests
--------------------------------------------------------

You need to modify the following file:
`src/test/java/com/nilhcem/fakesmtp/core/test/TestConfig.java`.

Please note that it is better to have two different ports for unit and integrations tests, to avoid any port binding exception while running Maven's `integration-test` goal.


Import the project on Eclipse IDE
---------------------------------

Run the following command:

    mvn eclipse:eclipse


Contact me
----------

Use my github's nickname (at) gmail (dot) com
