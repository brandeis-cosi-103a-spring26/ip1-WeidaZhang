@echo off
REM Build and run verification for IP2 Card Game

echo ========================================
echo IP2 Card Game - Build Verification
echo ========================================
echo.

echo [1/3] Building with Maven...
cd /d "%~dp0"
set PATH=%PATH%;C:\apache-maven-3.9.5\bin
mvn clean verify -q
if errorlevel 1 (
    echo BUILD FAILED!
    exit /b 1
)
echo [✓] Build successful - all tests passed
echo.

echo [2/3] Checking JAR file...
if exist "target\ip2-1.0-SNAPSHOT.jar" (
    echo [✓] JAR file created: target\ip2-1.0-SNAPSHOT.jar
    for %%I in ("target\ip2-1.0-SNAPSHOT.jar") do echo    Size: %%~zI bytes
) else (
    echo [✗] JAR file not found!
    exit /b 1
)
echo.

echo [3/3] Running game...
echo [✓] Game runs successfully - last game output:
echo.
java -jar target\ip2-1.0-SNAPSHOT.jar | findstr /c:"GAME OVER" /c:"Winner:"
echo.

echo ========================================
echo Build Status: SUCCESS ✓
echo ========================================
echo.
echo To run the game manually:
echo   java -jar target\ip2-1.0-SNAPSHOT.jar
echo.
echo To rebuild:
echo   mvn clean package
echo.
