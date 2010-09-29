:# This program may be used, executed, copied, modified and distributed
:# without royalty for the purpose of developing, using, marketing, or distributing.

:# Simple tests
@if "%VSINSTALLDIR%"=="" goto NOTSET
@if not exist Ping.wsdl goto NOSOURCE

:# Process the wsdl files
svcutil Echo.wsdl /config:WSWindowsClient.exe.config /async
svcutil Ping.wsdl /config:WSWindowsClient.exe.config /mergeconfig

:# Compile and create .exe
csc WSWindowsClient.cs EchoService.cs PingService.cs /reference:"%ProgramFiles%\Reference Assemblies\Microsoft\Framework\v3.0\System.ServiceModel.dll"
dir 
@goto END

:NOTSET
@echo ERROR: The environment for Visual Studio is not set
@echo Please run this from a Visual Studio Command Prompt
@goto END

:NOSOURCE
@echo ERROR: The WSDL source files cannot be found in the current directory.
@goto END

:END
