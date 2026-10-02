@echo off
REM R2 Dev server helper (Windows).
REM   dev build   build parser + console from source and install them into server\
REM   dev start   start the Dev server on http://localhost:7000 (console at /console)
REM   dev stop    stop it
REM   dev status  show whether it is listening
REM   dev mason   build Mason (MASON_HOME, default ..\mason) with Maven and put its jar into the app template
REM Needs a JDK (8-17) via JAVA_HOME or on PATH. Maven is bundled in server\maven.
setlocal
set ROOT=%~dp0
set ROOT=%ROOT:~0,-1%
set SERVER=%ROOT%\server

if defined JAVA_HOME goto javaok
for /f "delims=" %%i in ('where java 2^>nul') do if not defined JAVA_EXE set "JAVA_EXE=%%i"
if not defined JAVA_EXE (
  echo JAVA_HOME is not set and java is not on PATH
  exit /b 1
)
for %%i in ("%JAVA_EXE%") do set "JAVA_HOME=%%~dpi.."
:javaok

if "%1"=="build" goto build
if "%1"=="start" goto start
if "%1"=="stop" goto stop
if "%1"=="status" goto status
if "%1"=="mason" goto mason
echo usage: dev [build^|start^|stop^|status^|mason]
exit /b 1

:build
REM the bundled Maven 3.6 needs these to run on JDK 16+
set MAVEN_OPTS=--add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.text=ALL-UNNAMED --add-opens=java.desktop/java.awt.font=ALL-UNNAMED
set MVN=%SERVER%\maven\bin\mvn.cmd
call "%MVN%" -B -f "%ROOT%\parser\pom.xml" clean install -DskipTests || exit /b 1
call "%MVN%" -B -f "%ROOT%\console\pom.xml" clean package -DskipTests || exit /b 1
REM Kotlin script engine for KotlinRunner (git-ignored jars)
call "%MVN%" -B -q -f "%SERVER%\kotlin\pom.xml" dependency:copy-dependencies -DoutputDirectory="%SERVER%\lib" || exit /b 1
del /q "%SERVER%\lib\openapi-rest-model-*.jar" 2>nul
copy /y "%ROOT%\parser\target\openapi-rest-model-*.jar" "%SERVER%\lib\" >nul
rmdir /s /q "%SERVER%\webapps\console" 2>nul
copy /y "%ROOT%\console\target\console.war" "%SERVER%\webapps\console.war" >nul
for %%d in (temp tempapps backend logs) do if not exist "%SERVER%\%%d" mkdir "%SERVER%\%%d"
echo Built. Run: dev start
exit /b 0

:mason
REM Apps are generated from console\src\main\resources\app-template-v0.1.zip, which embeds the Mason jar.
if "%MASON_HOME%"=="" set MASON_HOME=%ROOT%\..\mason
if not exist "%MASON_HOME%\taglib\pom.xml" ( echo Mason checkout not found at %MASON_HOME%. Set MASON_HOME. & exit /b 1 )
set MAVEN_OPTS=--add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.text=ALL-UNNAMED --add-opens=java.desktop/java.awt.font=ALL-UNNAMED
call "%SERVER%\maven\bin\mvn.cmd" -B -f "%MASON_HOME%\taglib\pom.xml" clean package -DskipTests || exit /b 1
if exist "%TEMP%\r2tpl" rmdir /s /q "%TEMP%\r2tpl"
mkdir "%TEMP%\r2tpl\WEB-INF\lib"
copy /y "%MASON_HOME%\taglib\target\mason-*.jar" "%TEMP%\r2tpl\WEB-INF\lib\" >nul
pushd "%TEMP%\r2tpl"
"%JAVA_HOME%\bin\jar.exe" uf "%ROOT%\console\src\main\resources\app-template-v0.1.zip" WEB-INF\lib
popd
echo App template updated. Run: dev build (rebuilds the console with it)
exit /b 0

:start
set CATALINA_HOME=%SERVER%
set CATALINA_BASE=%SERVER%
for %%d in (temp tempapps backend logs) do if not exist "%SERVER%\%%d" mkdir "%SERVER%\%%d"
if not exist "%SERVER%\webapps\console.war" if not exist "%SERVER%\webapps\console" ( echo No console build found. Run: dev build & exit /b 1 )
call "%SERVER%\bin\catalina.bat" start
echo Starting... console: http://localhost:7000/console  (login admin/admin)
exit /b 0

:stop
set CATALINA_HOME=%SERVER%
set CATALINA_BASE=%SERVER%
call "%SERVER%\bin\catalina.bat" stop
exit /b 0

:status
netstat -ano | findstr /r /c:":7000 .*LISTENING" >nul && (echo running on :7000) || (echo not running)
exit /b 0
