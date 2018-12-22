
rem create new folder
md "D:\Oracle\MaxTunnel"
echo Completed Create Folder!

rem create tablespace
sqlplus system/123456 as sysdba @create_tablespace.sql
echo Completed Create Tablespace!

rem create user
sqlplus system/123456 as sysdba @create_oracle_user.sql
echo Completed Create User!

sqlplus bandweaver/123456@MAXTUNNELDB <create_database.sql >sqlplus.log
echo Completed MaxTunnel All DataBase!!!

sqlplus bandweaver/123456@MAXTUNNELDB <insert_data.sql >sqlplus_data.log
echo Completed MaxTunnel Insert Data!!!
:end

pause