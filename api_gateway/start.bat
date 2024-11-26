@echo off
setlocal enabledelayedexpansion

REM Path to the .env file
set ENV_FILE=.env

REM Check if the .env file exists
if not exist %ENV_FILE% (
    echo .env file not found!
    exit /b 1
)

REM Read the .env file and set environment variables
for /f "delims=" %%i in (%ENV_FILE%) do (
    set "line=%%i"
    REM Remove any surrounding quotes
    set "line=!line:"=!"
    REM Split the line into key and value
    for /f "tokens=1* delims==" %%a in ("!line!") do (
        set "key=%%a"
        set "value=%%b"
        REM Set the environment variable
        set "!key!=!value!"
    )
)

REM Construct the arguments string
set "args="
for /f "tokens=1* delims==" %%a in ('set') do (
    if not "%%a"=="" (
        if not "%%a"=="args" (
            if not "%%a"=="ENV_FILE" (
                REM Check if the variable is defined in the .env file
                findstr /c:"%%a=" %ENV_FILE% >nul
                if !errorlevel! equ 0 (
                    set "args=!args! --%%a=%%b"
                )
            )
        )
    )
)

REM Remove the leading space from the args string
set "args=!args:~1!"

REM Echo the command to be run
echo gradlew bootRun --args="!args!"

REM Run the gradlew command with the constructed arguments
./gradlew bootRun --args="!args!"

endlocal
