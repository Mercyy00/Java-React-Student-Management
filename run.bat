@echo off
REM ============================================================
REM  One-click launcher for the classic Java + JDBC + Servlet + JSP app.
REM  It sets Java 17 and Maven for you, then starts the server.
REM  Just double-click this file, OR run  run.bat  in a terminal.
REM  When you see "Started Jetty Server", open:  http://localhost:8080
REM  Press Ctrl+C in this window to stop the server.
REM ============================================================

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot"
set "MVN=C:\Users\Perfect\Downloads\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin\mvn.cmd"

echo Starting the Student Management System...
echo When you see "Started Jetty Server" below, open http://localhost:8080 in your browser.
echo.
call "%MVN%" jetty:run
