:# This program may be used, executed, copied, modified and distributed
:# without royalty for the purpose of developing, using, marketing, or distributing.

:# Simple tests
@if "%VSINSTALLDIR%"=="" goto NOTSET
@if not exist WSWSEClient.cs goto NOSOURCE

:# Process the wsdl files
wsdl Ping.wsdl
wsdl Echo.wsdl

:# Compile and create .exe
csc WSWSEClient.cs EchoService.cs PingService.cs
dir 
@goto END

:NOTSET
@echo ERROR: The environment for Visual Studio is not set
@echo Please run this from a Visual Studio Command Prompt
@goto END

:NOSOURCE
@echo ERROR: The Source files cannot be found in the current directory.
@goto END

:END