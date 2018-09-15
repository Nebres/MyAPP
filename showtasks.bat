call runcrud.bat
if "%ERRORLEVEL%" == "0" goto runbrowser
.echo
echo Fail Cannot open runcrud.bat

:runbrowser
start http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
.echo
echo Fail Cannot open Firefox

:end
echo END
