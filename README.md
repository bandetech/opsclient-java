#Java Client Library for Nunace OmniPage Server

Client library for Nuance OmniPage Server written in Java.

## Requirement

* Nuance OmniPage Server
* Java 8
* Maven

## Install

You can get Jar file, target/opsclient-0.0.2-SNAPSHOT.jar. However there are many depending libraries (please refer pom.xml). It's better to develop java client with maven.

## Sample Client

Please see AppTest.java and AppTestAuthenticate.java under src/test/java/com/nuance/imaging/ops/opsclient. 
Some configuration are necessary in src/main/resources/applicationContext.xml. (omnipageServer, serviceUrl)

## Limitation

* Implemented first version of NTLM authentication in branch ntlm-auth. This version will make some warning every calling server. To avoid the warnings, remove "Negotiate" from authentication provider list on IIS.
* There are some unimplemented methods.
  * ProcessOnCall
  * SetJobDataDescription
  * CreateLocalJob