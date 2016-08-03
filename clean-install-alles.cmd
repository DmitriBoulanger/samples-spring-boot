@echo off

echo.
java -version
echo.
call mvn -version
echo.
pause

set CURRENT_DIRECTORY=%~dp0
set REPOSITORY=E:\___TMP\m2\repository
echo.
echo Maven repository %REPOSITORY% 
cd %REPOSITORY%
dir /B
echo.

echo Removing all maven-artifacts in %REPOSITORY% ...
pause
cd ..
RMDIR %REPOSITORY% /S /Q
MKDIR %REPOSITORY% 
cd %REPOSITORY%
echo.
dir /B

cd %CURRENT_DIRECTORY%
dir /B
echo.
echo Runnig Maven clean install ...
pause
echo.

mvn clean install

