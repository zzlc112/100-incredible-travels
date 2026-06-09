@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup script for Windows
@REM ----------------------------------------------------------------------------

@echo off
setlocal enabledelayedexpansion

set "MAVEN_PROJECTBASEDIR=%CD%"
set "DIRNAME=%~dp0"
if "%DIRNAME%"=="" set DIRNAME=.
set "APP_BASE_NAME=%~n0"
set "APP_HOME=%DIRNAME%"

for %%i in ("%APP_HOME%") do set "APP_HOME=%%~fi"

set "WRAPPER_JAR=%APP_HOME%\.mvn\wrapper\maven-wrapper.jar"

if not defined JAVA_HOME (
  for %%i in (java.exe) do set "JAVA_CMD=%%~$PATH:i"
) else (
  set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
)

if not defined MAVEN_CONFIG (
  set "MAVEN_CONFIG=%USERPROFILE%\.m2"
)

@REM Find the wrapper properties
for /f "usebackq delims=" %%a in ("%APP_HOME%\.mvn\wrapper\maven-wrapper.properties") do (
  if "%%a"=="" goto findend
  if "!distributionUrl!"=="" (
    for /f "tokens=1,2 delims==" %%b in ("%%a") do (
      if "%%b"=="distributionUrl" set "distributionUrl=%%c"
    )
  )
  :findend
)

if not "%MAVEN_SKIP_RC%"=="" goto skipRcPre
if exist "%USERPROFILE%\mavenrc_pre.cmd" call "%USERPROFILE%\mavenrc_pre.cmd" %*
:skipRcPre

set "MAVEN_OPTS=%MAVEN_OPTS% -Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%"
set "MAVEN_OPTS=%MAVEN_OPTS% -Dmaven.conf=%MAVEN_CONFIG%"
set "MAVEN_OPTS=%MAVEN_OPTS% -Dclassworlds.conf="

"%JAVA_CMD%" ^
  %MAVEN_OPTS% ^
  -classpath "%WRAPPER_JAR%" ^
  org.apache.maven.wrapper.MavenWrapperMain ^
  %*

if ERRORLEVEL 1 (
  exit /b %ERRORLEVEL%
)

:skipRcPre

:end
