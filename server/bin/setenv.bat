@echo off

set JAVA_HOME=%CATALINA_BASE%\jdk

set "JAVA_OPTS=%JAVA_OPTS% -Dfile.encoding=UTF-8 -Xms512m -Xmx2048m"

REM run remove.bat scripts if exists
if exist "%CATALINA_BASE%\bin\remove.bat" (
    call "%CATALINA_BASE%\bin\remove.bat"
    del /F /Q "%CATALINA_BASE%\bin\remove.bat"
    REM also remove remove.sh script
    del /F /Q "%CATALINA_BASE%\bin\remove.sh"
)

IF EXIST "%CATALINA_BASE%\update" (
	REM copy all files from %CATALINA_BASE%\update to %CATALINA_BASE%
	xcopy /s/Y "%CATALINA_BASE%\update" "%CATALINA_BASE%"
	REM empty %CATALINA_BASE%\update folder

	cd /d "%CATALINA_BASE%\update"
	for /F "delims=" %%i in ('dir /b') do (rmdir "%%i" /s/q || del "%%i" /s/q)
)

if exist "%CATALINA_BASE%\bin\mtg-install.bat" (
	call "%CATALINA_BASE%\bin\mtg-install.bat"
	del /F /Q "%CATALINA_BASE%\bin\mtg-install.bat"
	REM also remove mtg-install.bat script
    del /F /Q "%CATALINA_BASE%\bin\mtg-install.sh"
)

rmdir /S /Q "%CATALINA_BASE%\work"

REM for /d %%i in (%CATALINA_BASE%\webapps\*) do (
		REM if NOT "%%i" == "%CATALINA_BASE%\webapps\console" (
				REM if NOT "%%i" == "%CATALINA_BASE%\webapps\ROOT" (
            REM rmdir /S /Q "%%i"
				REM )
		REM )
REM )
