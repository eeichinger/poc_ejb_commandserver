Introduction

The WebSphere Application Server Version 6.1 Web Services Feature Pack Samples 
demonstrate simple message exchange patterns (MEP) using both a synchronous and 
asynchronous programming model. The Samples support both SOAP 1.1 and SOAP 1.2. 
These Samples incorporate Web service standards such as WS-Addressing (WS-A), 
WS-Reliable Messaging (WS-RM), and WS-Secure Conversation (WS-SC).  Using these 
Web service Samples, you can perform a broad range of interoperability tests.  
The Samples demonstrate the use of JavaBeans artifacts, static service endpoints, 
and proxy based clients. Additionally, a sample that uses the Message Transmission 
Optimization Mechanism (MTOM) standard is provided to demonstrate vendor 
interoperable attachment support for sending binary attachments. 

The following table describes the Sample files that are included.

Filename			Description

runSampleSei.bat		Batch file to run thin client Samples
runSampleSei.sh			Unix Script to run thin client Samples
runSampleSei			i5/OS Script to run thin client Samples
runSampleMTOM.bat		Batch file to run MTOM thin client
runSampleMTOM.sh		Unix Script to run MTOM thin client
runSampleMTOM			i5/OS Script to run MTOM thin client

WSSampleClientSei.ear		Contains Ping, Echo Sync, Echo Async clients 
				and GUI
WSSampleClientSei.jar		Contains Ping, Echo Sync, Echo Async thin 
				client Samples for command-line (no source)
WSSampleServicesSei.ear		Contains Ping and Echo services
WSSampleMTOMClient.jar		Contains MTOM thin client for command-line
WSSampleMTOMClient.ear		Contains MTOM client and GUI
WSSampleMTOMService.ear		Contains MTOM service

Note: The source for the Samples is included in the individual EAR 
and Java archive (JAR) files. You can load these files directly into the 
WebSphere Application Server Toolkit by pointing to the individual enterprise 
archive (EAR) files, which will then become separate projects. 

1.0 Service Endpoint Interface (SEI) Sample Applications 

The sample application message exchange patterns are described in the following section. 

1.1 One-way Request - "Ping"

The one-way request scenario demonstrates a one-way message exchange pattern to 
send a "ping" application message from a client to a service.

1.2 Two-way Request and Response - "Sync Echo"

The two-way request and response scenario demonstrates a two-way message exchange 
pattern to send an application message from a client to a service and receives an 
"echo" string response from the server on the same request-response channel.

1.3 Two-way Request and Response - "Asynchronous Echo with Sync Communication"

The two-way asynchronous request and response scenario demonstrates a two-way 
message exchange pattern that sends an "echo" string message from a client to 
a service and expects the "echo" string response from the server on the same 
request-response channel. Although this message exchange pattern is similar to 
the Sync Echo sample, this Sample implements the Java API for XML Web Services 
(JAX-WS) asynchronous programming model. In this scenario, a request is made 
but the application can continue to do additional work. When a response is received, 
a callback handler routine is used to process the response.  Over the wire, this 
Sample looks the same as Sync Echo.

1.4 Two-way (Request/Response) -"Asynchronous Echo with Async Communication"

The two-way asynchronous request and response with asynchronous communication 
scenario demonstrates a two-way message exchange pattern to send an "echo" string 
message from a client to a service.  The client expects the "echo" string 
response from the server on a different request-response channel. This sample 
implements the JAX-WS asynchronous programming model. In this scenario, a request 
is made to the service but the client application can continue to do additional work. 
When a response is received, a callback handler routine is then used to process 
the response.  The client listens on separate channel to receive the response 
messages from a server initiated channel.

1.5 MTOM Sample 

This scenario demonstrates using MTOM to transfer a binary object to and from 
the service endpoint. 


2.0 Installing the Sample applications

This section describes how to install and deploy the EAR file in the 
WebSphere Application Server Version 6.1 with following the WebSphere Web
Service FeaturePack installation. The Samples are installed on the file system 
with the Feature Pack for Web Services.

2.1 Installing the Sample applications using the sample install scripts

The Sample applications are provided with scripts to automate installation 
and deployment. 

Open a Windows system command prompt or a Unix system console. 

Change the directory to <install_root>/samples/lib/WebServicesSamples 

Depending on your operating system, the installation script is 
installapps.cmd or installapps.sh. The installation script uses 
all the defaults for installation. If any options are required, then use 
the Integrated Solution Console method.

Invoke the following installation script:
	Windows - installapps 
or
	Unix - ./installapps.sh 



The scripts have four optional parameters that you enter as necessary. 

profile - application server profile
cell - application server cell
node - application server node
server - application server name

For example:
./installapps.sh AppSrv02 LINK-T42Node01Cell LINK-T42Node01 server1
or
For example:
./installapps.sh AppSrv02 

You can use additional parameters on the command line. If your server has 
administrative security enabled, you must provide the user ID and password or 
the script fails. For example:

./installapps.sh  -user myuserid -password mypassword


2.2 Uninstalling the Sample applications using Sample uninstall scripts

The Sample applications include scripts to automate the uninstallation process.
 
Open a Windows system command prompt or a Unix system console. 

Change directory to <install_root>/samples/lib/WebServicesSamples. 

Depending on your operating system, the installation script is 
uninstallapps.cmd or uninstallapps.sh.

Invoke the following installation script:
	Windows - uninstallapps
or
	Unix - ./uninstallapps.sh 


The scripts have four optional parameters that you enter as necessary. 

profile - application server profile
cell - application server cell
node - application server node
server - application server name

For example:
./uninstallapps.sh AppSrv02 LINK-T42Node01Cell LINK-T42Node01 server1
or 
For example:
./uninstallapps.sh AppSrv02 

You can use additional parameters on the command line. If your server has 
administrative security enabled, you must provide the user ID and password or 
the script fails. For example:

./uninstallapps.sh -user myuserid -password mypassword


3.0 Running the Sample application demos

This section describes how to run the Sample application message exchange patterns 
and the MTOM Sample application.

3.1 Starting the MEP Sample application demo

After the Sample applications are deployed, point your browser the following 
location to start the Samples demo user interface:  

http://<host name>:port/wssamplesei/demo

e.g. http://localhost:9081/wssamplesei/demo

Note:  The port might vary based on your application server configuration.


The Message type field provides a list of the sample message exchange patterns. 

The selections include: One-Way Ping, Synchronous Echo, Asynchronous Echo with Sync
Communication, and Asynchronous Echo with Async Communication.

The Message string text field contains the text that is transmitted from the 
client to the service.

Use the Message count field to configure the number of times the MEP is used.

The Service URI is the host name and port number of the machine where the service 
endpoint is hosted. 

The SOAP field determines the SOAP version to use. To use the default value of 
SOAP 1.1, leave the check box unselected.  To use the SOAP 1.2 value, select the 
check box. 

The Send Message button is used to run the selected sample MEP application. 

The Response box shows the response from the service.


3.2 Asynchronous Echo with Sync Communication example

To run an example of the Asynchronous Echo with Sync communication message pattern, 
perform the following steps:

1.  Select Asynchronous Echo with Sync Communication in the Message Type list. 
2.  Enter  "test" for the Message String value.
3.  Set the Message Count to 1.
4.  Enter http://localhost:9081  for the Service URI.
5.  Determine the SOAP version to use.  Because the SOAP field is not checked, 
the default SOAP 1.1 value is used.
6.  Click the Send Message button.
   
The Response box shows the connection status, the message request, and the 
message response. In this example, the message response is JAX-WS== >>test.  
The service adds the text, JAX-WS==>>, to the beginning of the message request 
string, "test." 

If this example is modified to use SOAP 1.2, then the service adds the text, 
SOAP12==>>,  to the beginning of the message response string.


3.3 Starting the MTOM Sample application demo

After the Sample applications are deployed, point your browser the following 
location to start the MTOM Sample demo user interface:  

http://<host name>:port/wssamplemtom/demo

e.g. http://localhost:9081/wssamplemtom/demo

Note:  The port might vary based on your application server configuration.


The Service type field provides a list of the client service interfaces.

The selections include: Dispatch and Proxy.

The Source Filename is the fully-qualified name for the file to use for the 
test. This file must exist on the server, and should be specified using the 
notation used on the server.

The Service URI is the host name and port number of the machine where the service 
endpoint is hosted. 

The SOAP field determines the SOAP version to use. To use the default value of 
SOAP 1.1, leave the check box unselected.  To use the SOAP 1.2 value, select the 
check box. 

The Send Message button is used to run the selected MTOM sample application. 

The Response box shows the response from the service.



3.4 Running the WSSampleClientSei thin client

The WSSampleClientSei uses a command-line interface to initiate the service.

The WSSampleClientSei.jar includes the script files runSampleSei.bat and 
runSampleSei.sh. 

To run an example of the WSSampleClientSei thin client sample, perform the 
following steps:

1.  Open a Windows system command prompt using the Start Menu: Start-> Run -> cmd.
2.  Run the runSampleSei script  with the default values. Run the 
command, "runSampleSei -?" to display the options available to change any of the 
default values.

3.5 Running the MTOM Sample thin client

The WSSampleMTOMClient uses a command-line interface to initiate the service. 
To use this service, you need to provide a file such as a binary file in gif, 
jpeg, or bmp format. 


Included with the WSSampleMTOMClient.jar file are the script files runSampleMtom.bat, 
runSampleMtom.sh and runSampleMtom. 

To run an example of the MTOM sample client, perform the following steps:

1.  Open a Windows system command prompt using the Start Menu: Start-> Run -> cmd.
2. Run the command, "runSampleMtom -?" to display the options available to change 
any of the default values.
3. Run the command runSampleMtom with at least -i option and filename of binary file 
to be sent.
Note: based on your WAS configuration you may have to specify the -p port option
The default port value is 9080.


4.0 Enabling Reliable Messaging & Security

Policy sets are used to enable quality of service such as reliable messaging and 
secure conversation. 
Policy sets that are attached to an application define the 
quality of service for that application.  A set of scripts are provided to enable 
the policy sets for WS-Reliable Messaging (WS-RM), WS-Secure Conversation (WS-SC) 
and Reliable Asynchronous Messaging Profile (RAMP).  RAMP is a composition of 
both WS-RM and WS-SC.  These scripts are provided as examples. Policy sets 
can also be enabled through the administration console. These scripts are 
designed to work with the Sample applications only.

***
WS-Addressing (WS-A), WS-Reliable Messaging (WS-RM), and WS-Secure Conversation (WS-SC)
***

The following table describes the policy set Sample scripts.

Filename			Description
Windows systems Scripts	

enableRMQoS.bat			Enable reliable messaging version 1.1
enableRM1QoS.bat		Enable reliable messaging version 1.0
enableSCQoS.bat			Enable secure conversation
enableRAMPQoS.bat		Enable reliable messaging and secure conversation
enableWSAQoS.bat		Enable WSAddressing
disableRMQoS.bat		Disable reliable messaging version 1.1
disableRM1QoS.bat		Disable reliable messaging version 1.0
disableSCQoS.bat		Disable secure conversation
disableRAMPQoS.bat		Disable reliable messaging and secure conversation
disableWSAQoS.bat		Disable WSAddressing

Unix platform Scripts
	
enableRMQoS.sh			Enable reliable messaging version 1.1
enableRM1QoS.sh			Enable reliable messaging version 1.0
enableSCQoS.sh			Enable secure conversation
enableRAMPQoS.sh		Enable reliable messaging and secure conversation
enableWSAQoS.sh			Enable WSAddressing
disableRMQoS.sh			Disable reliable messaging version 1.1
disableRM1QoS.sh			Disable reliable messaging version 1.0
disableSCQoS.sh			Disable secure conversation
disableRAMPQoS.sh		Disable reliable messaging and secure conversation
disableWSAQoS.sh		Disable WSAddressing

i5/OS platform Scripts
	
enableRMQoS			Enable reliable messaging version 1.1
enableRM1QoS			Enable reliable messaging version 1.0
enableSCQoS			Enable secure conversation
enableRAMPQoS			Enable reliable messaging and secure conversation
enableWSAQoS			Enable WSAddressing
disableRMQoS			Disable reliable messaging version 1.1
disableRM1QoS			Disable reliable messaging version 1.0
disableSCQoS			Disable secure conversation
disableRAMPQoS			Disable reliable messaging and secure conversation
disableWSAQoS			Disable WSAddressing

To run the scripts on Windows systems, perform the following steps:

1.  Open a Windows system command prompt using the Start Menu: Start-> Run -> cmd.
2.  Change the directory to <install_root>/samples/lib/WebServicesSamples.

For example:
cd "C:/Program Files/IBM/WebSphere/AppServer/samples/lib/WebServiceSamples"

3. To run the enable reliable messaging scripts, enter the command:  enableRMQos.bat

4. To run the disable reliable messaging scripts, enter the command:  disableRMQos.bat 
 
The reliable messaging & security enable and disable scripts have four optional parameters: 
profile - application server profile
cell - application server cell
node - application server node
server - application server name
 
This example is used to run the enable reliable messaging script with the optional 
profile, cell, and node parameters specified:   
enableRMQoS.bat AppSrv02 LINK-T42Node01Cell LINK-T42Node01

This example is used to run the disable reliable messaging script with the optional 
profile, cell, and node parameters specified:   
disableRMQoS.bat AppSrv02 LINK-T42Node01Cell LINK-T42Node01 

 
5. You can use additional parameters on the command line. If your server has 
administrative security enabled, then you must provide the user ID and password or 
the script fails.

For example:

./enableRMQoS.sh -user myuserid -password mypassword

6. If you receive a warning message the sample applications may not have 
restarted correctly. Use your application server console to stop the 
sample applications and then restart them.


4.1 Applying QoS to The thinclient Sample

To test the runSampleSei sample with the QoS policy sets, you should follow these
guidelines.

1. First, apply the QoS you want to test to the WebSphere sample applications as
indicated in the previous chapter.

2. You will need to edit the runSampleSei.bat (or runSampleSei.sh if running on unix)

3. For RAMP and SecureConversation, add the following statement to include the 
path to the jaas login file:
Windows:
  set JAASLOGIN=-Djava.security.auth.login.config=<user_install_root>/properties/wsjaas_client.conf
Unix:
  export JAASLOGIN=-Djava.security.auth.login.config=<user_install_root>/properties/wsjaas_client.conf
Replace <user_install_root> above with the server directory where your profile
is located. For example:
  set JAASLOGIN=-Djava.security.auth.login.config=C:/IBM/WebSphere/profiles/AppSrv02/properties/wsjaas_client.conf

4. Change the classpath statement to:
Windows:
  set CLASSPATH=%WAS_HOME%/runtimes/com.ibm.jaxws.thinclient_6.1.0.jar;./WSSampleClientSei.jar;.
Unix:
  export CLASSPATH=${WAS_HOME}/runtimes/com.ibm.jaxws.thinclient_6.1.0.jar:./WSSampleClientSei.jar:.

(Note that "." is included at the end. This is because we'll need it so policy set loading code
finds META-INF directory we'll be creating in just a bit, so don't omit it!)

5. Add <JAASLOGIN> and -DUSER_INSTALL_ROOT="<user_install_root>" to
the java command. It should be called as:
Windows:
  java %JAASLOGIN% -DUSER_INSTALL_ROOT="<user_install_root>" -cp "%CLASSPATH%" com.ibm.was.wssample.sei.cli.SampleClient %*
Unix:
  java ${JAASLOGIN} -DUSER_INSTALL_ROOT="<user_install_root>" -cp "${CLASSPATH}" com.ibm.was.wssample.sei.cli.SampleClient $@

6. Create a dir named META-INF under <WAS_HOME>/samples/lib/WebServicesSamples,
then copy PolicySets directory under <user_install_root>/config/cells/<CELL_NAME>/ to
<WAS_HOME>/samples/lib/WebServicesSamples/META-INF.

7. Copy the bindings subdirectory and clientPolicyAttachment.xml file from
<user_install_root>/config/cells/<CELL_NAME>/applications/WSSampleClientSei.ear/deployments/WSSampleClientSei/META-INF 
to <WAS_HOME>/samples/lib/WebServicesSamples/META-INF. You should copy the entire 
bindings subdirectory and its contents, for example, SecureConversation123binding
or RAMP_client_default_bindings.

8. There is no need to restart the server.

9. Run the sample.


4.2 Using The Scripts In A Network Deployment Configuration

To use these scripts in a Network Deployment configuration please
ensure that you specify all optional parameters as follows
 profile - the deployment manager profile name
 cell -  
 node -  ... and
 server - for the application server (not the deployment manager) 
          where the applications are installed

This applies to the installation scripts, as well as the scripts to enable
ReliableMessaging and Security.