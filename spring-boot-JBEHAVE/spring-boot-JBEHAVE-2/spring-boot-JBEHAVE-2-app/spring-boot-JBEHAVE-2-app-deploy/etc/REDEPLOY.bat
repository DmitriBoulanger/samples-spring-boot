@echo off

set TOMCAR_WEBAPP=D:\tomcat\apache-tomcat-8.0.15\webapps
set SRC=D:\contex-license\git\license\license-web-reactor

cd %TOMCAR_WEBAPP%

del license-activation-service.war
del license_activation_client.war
del webmanager.war

rmdir license-activation-service /s /q
rmdir license_activation_client /s /q
rmdir webmanager /s /q

MOVE %SRC%\license-web-activation-service\target\license-activation-service.war  .
MOVE %SRC%\license-web-activation-client\target\license_activation_client.war  .
MOVE %SRC%\license-web-manager\target\webmanager.war  .

echo. 
echo Deployed WAR-files:
dir *.war /a /b