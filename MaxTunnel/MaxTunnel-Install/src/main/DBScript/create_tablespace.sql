
--step 1 : create tablespace
create tablespace maxtunnel_data
logging
datafile 'c:\maxtunnel_data.dbf'
size 50m
autoextend on
next 50m maxsize unlimited
extent management local;

--step 2 : create user
create user bandweaver identified by 123456 default tablespace maxtunnel_data;

--step 3 : grant roles 
grant connect,resource,dba to bandweaver;
