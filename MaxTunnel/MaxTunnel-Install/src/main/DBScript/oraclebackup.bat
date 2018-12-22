@echo off

echo create new folder
md "D:\Oracle\Backup"

echo 删除30天前的备份文件和日志

forfiles /p "D:\Oracle\Backup" /m *.dmp /d -30 /c "cmd /c del @path" 
forfiles /p "D:\Oracle\Backup" /m *.log /d -30 /c "cmd /c del @path"

echo 正在备份 Oracle 数据库，请稍等…… 
exp bandweaver/123456@MAXTUNNELDB file=D:\Oracle\Backup\%date:~0,4%%date:~5,2%%date:~8,2%.dmp owner(bandweaver) log=D:\Oracle\Backup\%date:~0,4%%date:~5,2%%date:~8,2%.log
echo 任务完成!
:end

pause
