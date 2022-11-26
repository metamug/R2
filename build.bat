REM remove parser
del  .\server\lib\openapi-rest-model-*.jar

REM remove console webapp
rmdir  .\server\webapps\console
del  .\server\webapps\console.war

REM remove root webapp
del  .\server\webapps\ROOT\dist
del /q  .\server\webapps\ROOT\assets
del  .\server\webapps\ROOT\static
del  .\server\webapps\ROOT\index.html

REM Add Empty Folders
mkdir .\server\temp
mkdir .\server\tempapps
mkdir .\server\backend
mkdir .\server\logs

REM install parser as console needs parser and move it into server lib
call mvn -f parser/pom.xml clean install
move .\parser\target\*.jar server\lib

REM install console in server folder
call mvn -f console/pom.xml clean install
move .\console\target\console.war server\webapps


REM install UI into webapp root
cd ui
call npm install
call npm run build
cd ..
mkdir server\webapps\ROOT\assets
XCOPY /q /E ui\src\assets server\webapps\ROOT\assets
move ui\build\ server\webapps\ROOT\assets

REM Now run the built server
REM cd server
REM .\bin\startup.bat
