
rem create user
sqlplus system/123456 as sysdba @create_oracle_user.sql
echo Completed Create User!

set return=D:\20181212.dmp

imp bandweaver/123456@MAXTUNNELDB file=%return% full=y log=oracleReturn.log
echo 导入完成!

sqlplus bandweaver/123456@MAXTUNNELDB <create_tables.sql
echo Completed MaxTunnel All DataBase!!!
:end

pause
