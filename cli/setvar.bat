@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@if "%DEBUG%" == "" @echo off

@rem 
@rem $Revision$ $Date$
@rem 

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

:begin
set pathfull=%path%
set pathfull=%pathfull:"=%
echo %pathfull%
Pause
if not (%pathfull:MTG_HOME=%==%pathfull%)
echo It contains MTG_HOME

@rem Determine what directory it is in.
set DIRNAME=%~dp0
set DIRNAME=%DIRNAME:~0,-1%
echo %DIRNAME%
if "%DIRNAME%" == "" set DIRNAME=.\
@rem set newpath=%path:"=%
@rem set newpath=%newpath%;%DIRNAME%
@rem if not %og:MTG_HOME_PATH=%==%og% echo It contains MTG_HOME_PATH
echo %DIRNAME%
Pause
:: Set Path variable
setx -m MTG_HOME_PATH "%DIRNAME%"
Pause