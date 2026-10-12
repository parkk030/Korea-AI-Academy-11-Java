@echo off
chcp 65001 > nul
setlocal

echo ===================================================
echo        Java 21 Smart GUI Calculator (JDK 21)
echo ===================================================

:: JDK 21 경로 자동 탐색
set "JAVA_CMD=java"
set "JAVAC_CMD=javac"

if exist "C:\Users\Admin\.jdks\temurin-21.0.12.1\bin\java.exe" (
    set "JAVA_CMD=C:\Users\Admin\.jdks\temurin-21.0.12.1\bin\java.exe"
    set "JAVAC_CMD=C:\Users\Admin\.jdks\temurin-21.0.12.1\bin\javac.exe"
) else if defined JAVA_HOME (
    set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
    set "JAVAC_CMD=%JAVA_HOME%\bin\javac.exe"
)

echo [1/2] JDK 21 소스 코드 컴파일 중...
if not exist "bin" mkdir "bin"

"%JAVAC_CMD%" --release 21 -encoding UTF-8 -d bin src/com/calc/*.java src/com/calc/model/*.java src/com/calc/ui/*.java
if %ERRORLEVEL% neq 0 (
    echo [오류] 컴파일에 실패했습니다.
    pause
    exit /b %ERRORLEVEL%
)

echo [2/2] GUI 계산기 실행 중...
"%JAVA_CMD%" -cp bin com.calc.Main

endlocal
