# Simple web server2

## Problem Statement

Create a simple web server that can sum up numbers in a multithreaded environment.

## Requirements
Create a java http server application that accepts up to 20 simultaneous requests. 
You can use jetty or tomcat web servers as the engine. Arriving requests will be http post requests.
If a request with a number arrives, keep the number around, don't respond yet.
If a request with they keyword "end" (without the quotes) arrives then respond with the sum of all received numbers to all open requests (e.g. if you requests with numbers 4 and 7 and end, all three requests should get response 11).
Requests can arrive in parallel at the same time, the system must not lose any numbers or requests.
Expected numbers are without decimal places, sum of them will not exceed 10 billion.
After doing the "end" calculation forget all the numbers and be ready for the repeat cycle of operation (getting new requests with new numbers,
giving out response on next end).
Provide the application to us as source with build and deploy instructions (preferably it should build into a war file that can be run with jetty or
tomcat).
Provide tests as well

## Prerequisite to build
	1. JAVA_HOME should be defined as a path under Environments. This should be pointed to JDK installation folder.
	
## How to Build?

	1. Navigate to the server folder of the project
	2. If you are on a linux machine, execute the following command in terminal to build the application.
		a. sh build-linux.sh 
		b. You can also double click the file if you are in Linux GUI.
		c. You should be able to see "BUILD SUCCESS" and a war file by name "simplewebserver.war" will be created under target folder.
	2. If you are on a windows machine, double click "build-windows.bat" file.
		a. You should be able to see "BUILD SUCCESS" and a war file by name "simplewebserver.war" will be created under target folder.
    
## How to deploy in tomcat server?

	1. After building the application, goto /server/target
	2. Look for "simplewebserver.war" WAR file and copy it.
	3. Navigate to %TOMCAT_PATH%/webapps and paste the WAR file.
	4. Now navigate to %TOMCAT_PATH%/bin and run the following command 
		a. catalina.bat run (on Windows)
		b. catalina.sh run (on Unix-based systems)
	5. Server will start working on http://localhost:%PORT%/simplewebserver/
	
## How to test?

	1. Test scripts are available \simplewebserver\testscripts folder
	3.To test in a tomcat server,
		a. goto \server\testscripts folder, 
		b. execute sh testdata-tomcat.sh in a terminal
		c. execute sh end-tomcat.sh in another terminal
	4. These commands work only in Linux/Unix environments
	5. In order to execute this in windows, download and install GIT Bash to execute the test scripts.
	6. Alternatively, we can test using REST clients such as postman. 
	7. Please note that browser based REST client do not allow more than 6 requests to the same host.

## Tech Stack

- Java
- Jersey

