rem # create new folder
rem # md "C:\Oracle\MaxTunnel"
echo Completed Create Folder!

rem # create tablespace
rem # sqlplus system/123456 < create tablespace MAXTUNNELDB1 datafile 'C:\Oracle\MaxTunnel\maxtunnel.dbf'
rem # 实例控件创建先不写了，有点复杂，一般用oracle自带工具安装

rem # create user
rem #@create_oracle_user.sql
echo Completed Create User!

sqlplus bandweaver/123456@MAXTUNNELDB <create_database_common.sql >sqlplus.log
echo Completed MaxTunnel DataBase Common Table!

sqlplus bandweaver/123456@MAXTUNNELDB <create_database_em.sql >sqlplus.log
echo Completed MaxTunnel DataBase Common Table!

sqlplus bandweaver/123456@MAXTUNNELDB <create_database_mam.sql >sqlplus.log
echo Completed MaxTunnel DataBase Common Table!

sqlplus bandweaver/123456@MAXTUNNELDB <create_database_oam.sql >sqlplus.log
echo Completed MaxTunnel DataBase Common Table!

sqlplus bandweaver/123456@MAXTUNNELDB <create_database_omm.sql >sqlplus.log
echo Completed MaxTunnel DataBase Common Table!

echo Completed All MaxTunnel DataBase!!!
:end

pause