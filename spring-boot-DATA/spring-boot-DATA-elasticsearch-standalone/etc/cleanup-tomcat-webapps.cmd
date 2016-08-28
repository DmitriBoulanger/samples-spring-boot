@echo off

set CURRENT_DIRECTORY=%~dp0
set WEBAPPS=%CATALINA_HOME%\webapps
set WEBAPPS_COPY=%CATALINA_HOME%\webapps-origin-min
echo.
echo Tomcat copy %WEBAPPS_COPY% 
cd %WEBAPPS_COPY%
dir /B
echo.

echo Removing %WEBAPPS% ...
pause

cd %CATALINA_HOME%
RMDIR %WEBAPPS% /S /Q
MKDIR %WEBAPPS% 
xcopy %WEBAPPS_COPY%\* %WEBAPPS% /s
cd %CURRENT_DIRECTORY%

 

