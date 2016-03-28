#Java Client Library for Nunace OmniPage Server

Client library for Nuance OmniPage Server written in Python.

## Requirement

* Nuance OmniPage Server
* Java 8
* Maven

## Install

You can get Jar file, target/opsclient-0.0.1-SNAPSHOT.jar. However there are many depending libraries (please refer pom.xml). It's better to develop java client with maven.

## Sample Client

Please see AppTest.java under src/test/java/com/nuance/imaging/ops/opsclient. 
Some configuration are necessary in src/main/resources/applicationContext.xml. (omnipageServer, serviceUrl)

## Limitation

* Authentication is not implemented.
* There are some unimplemented methods.
  * ProcessOnCall
  * SetJobDataDescription
  * CreateLocalJob