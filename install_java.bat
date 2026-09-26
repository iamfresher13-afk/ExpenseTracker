@echo off
REM ============================================================
REM  install_java.bat - installs Eclipse Temurin JDK 17 via winget
REM  Double-click this file, then approve the Windows admin prompt.
REM ============================================================
setlocal

echo.
echo === Checking for winget ===
where winget >nul 2>&1
if errorlevel 1 (
    echo winget was not found on this machine.
    echo winget ships with Windows 10/11 "App Installer" from the Microsoft Store.
    echo Please install "App Installer" from the Microsoft Store, then re-run this script.
    echo Alternatively, install JDK 17 manually from:
    echo    https://adoptium.net/temurin/releases/?version=17
    pause
    exit /b 1
)

echo winget found.
echo.
echo === Checking existing Java ===
java -version 2>nul
echo.

echo === Installing Eclipse Temurin JDK 17 (LTS) ===
echo A Windows admin prompt will appear - click YES to allow the install.
echo.
winget install --id EclipseAdoptium.Temurin.17.JDK -e --accept-source-agreements --accept-package-agreements

if errorlevel 1 (
    echo.
    echo winget reported an error. Possible causes:
    echo   - You did not approve the admin prompt
    echo   - JDK 17 is already installed
    echo   - Network / Microsoft Store source issue
    echo If it is already installed, that is fine.
    pause
    exit /b 1
)

echo.
echo === Done ===
echo JDK 17 installed. The Temurin MSI sets JAVA_HOME and updates PATH automatically.
echo.
echo IMPORTANT: close this window and open a NEW Command Prompt, then verify:
echo    java -version
echo You should see: openjdk version "17...".
echo.
pause
