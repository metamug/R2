REM remove parser
del  .\server\lib\openapi-rest-model-*.jar

REM remove console webapp
rmdir  .\server\webapps\console
del  .\server\webapps\console.war

REM remove root webapp
del  .\server\webapps\ROOT\dist
del  .\server\webapps\ROOT\assets
del  .\server\webapps\ROOT\static
del  .\server\webapps\ROOT\index.html

REM Add Empty Folders
mkdir .\server\temp
mkdir .\server\tempapps
mkdir .\server\backend
mkdir .\server\logs

REM install console in server folder
cd console
mvn clean install
mv console\target\console.war server\webapps

REM install parser into server lib
cd ..\parser
mvn clean install
mv parser\target\*.jar server\lib

REM install UI into webapp root
mv ui\src\assets server\webapps\ROOT
