
declare 
      num   number; 
begin       
   num := 0;
      select count(1) into num from user_constraints where constraint_name = 'FK_TN_PER_REFERENCE_T'; 
      if num > 0 then   
         execute immediate 'alter table T_SECURITY_TUNNEL_PERMISSION drop constraint  FK_TN_PER_REFERENCE_T';   
      end if;
-- prompt dropping sequence
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_TUNNEL_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_TUNNEL_SEQUENCE';   
      end if;     
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_TUNNEL_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_TUNNEL_TG';   
      end if; 


-- prompt dropping T_COMMON_TUNNEL
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_TUNNEL';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_TUNNEL'; 
      end   if; 
      
      -- prompt dropping sequence
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_AREA_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_AREA_SEQUENCE';   
      end if;     
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_AREA_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_AREA_TG';   
      end if; 


-- prompt dropping T_COMMON_AREA
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_AREA';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_AREA'; 
      end   if; 

-- prompt Dropping T_SECURITY_DOMAIN...
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_SECURITY_DOMAIN';
      if   num=1   then 
          execute immediate 'drop table T_SECURITY_DOMAIN'; 
      end   if; 


-- prompt Dropping T_SECURITY_LOG...
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_SECURITY_LOG';
      if   num=1   then 
          execute immediate 'drop table T_SECURITY_LOG'; 
      end   if; 
-- prompt Dropping T_OPERATION_LOG...
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OPERATION_LOG';
      if   num=1   then 
          execute immediate 'drop table T_OPERATION_LOG'; 
      end   if; 
  
/*==============================================================*/
/* Table: "T_SECURITY_USER"                                     */
/*==============================================================*/
-- ɾ�����
      num := 0;
      select count(1) into num from user_constraints where constraint_name = 'FK_T_U_REFERENCE_T_U'; 
      if num > 0 then   
         execute immediate 'alter table T_SECURITY_USER_ROLE drop constraint  FK_T_U_REFERENCE_T_U';   
      end if;
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'SECURITY_USER_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  SECURITY_USER_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'SECURITY_USER_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  SECURITY_USER_TG';   
      end if;
-- prompt Dropping T_SECURITY_USER...
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_SECURITY_USER';
      if   num=1   then 
          execute immediate 'drop table T_SECURITY_USER'; 
      end   if; 
  
/*==============================================================*/
/* Table: "T_SECURITY_ROLE"                                     */
/*==============================================================*/

-- ɾ�����
      num := 0;
      select count(1) into num from user_constraints where constraint_name = 'FK_T_R_REFERENCE_T_R'; 
      if num > 0 then   
         execute immediate 'alter table T_SECURITY_ROLE_PERMISSION DROP constraint  FK_T_R_REFERENCE_T_R';   
      end if;
-- ɾ�����
      num := 0;
      select count(1) into num from user_constraints where constraint_name = 'FK_T_U_REFERENCE_T_R'; 
      if num > 0 then   
         execute immediate 'alter table T_SECURITY_USER_ROLE DROP constraint  FK_T_U_REFERENCE_T_R';   
      end if;
-- prompt dropping sequence      
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'SECURITY_ROLE_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  SECURITY_ROLE_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'SECURITY_ROLE_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  SECURITY_ROLE_TG';   
      end if;
-- prompt Dropping T_SECURITY_ROLE...
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_SECURITY_ROLE';
      if   num=1   then 
          execute immediate 'drop table T_SECURITY_ROLE'; 
      end   if; 
      
/*==============================================================*/
/* Table: "T_SECURITY_PERMISSION"                               */
/*==============================================================*/

-- ɾ�����
      num := 0;
      select count(1) into num from user_constraints where constraint_name = 'FK_T_R_REFERENCE_T_P'; 
      if num > 0 then   
         execute immediate 'alter table T_SECURITY_ROLE_PERMISSION DROP constraint  FK_T_R_REFERENCE_T_P';   
      end if;
  
           num := 0;
      select count(1) into num from user_constraints where constraint_name = 'FK_TN_PER_REFERENCE_P'; 
      if num > 0 then   
         execute immediate 'alter table T_SECURITY_TUNNEL_PERMISSION DROP constraint  FK_TN_PER_REFERENCE_P';   
      end if;
-- prompt dropping sequence      
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'SECURITY_PERMISSION_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  SECURITY_PERMISSION_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'SECURITY_PERMISSION_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  SECURITY_PERMISSION_TG';   
      end if;

-- prompt Dropping T_SECURITY_PERMISSION...
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_SECURITY_PERMISSION';
      if   num=1   then 
          execute immediate 'drop table T_SECURITY_PERMISSION'; 
      end   if;     


/*==============================================================*/
/* Table: "T_SECURITY_ROLE_PERMISSION"                          */
/*==============================================================*/

-- prompt dropping sequence      
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'SECURITY_ROLE_PERMISSION_SQ'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  SECURITY_ROLE_PERMISSION_SQ';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'SECURITY_ROLE_PERMISSION_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  SECURITY_ROLE_PERMISSION_TG';   
      end if;
-- prompt Dropping T_SECURITY_ROLE_PERMISSION...
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_SECURITY_ROLE_PERMISSION';
      if   num=1   then 
          execute immediate 'drop table T_SECURITY_ROLE_PERMISSION'; 
      end   if; 
      
/*==============================================================*/
/* Table: "T_SECURITY_USER_ROLE"                                */
/*==============================================================*/

      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'SECURITY_USER_ROLE_SQ'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  SECURITY_USER_ROLE_SQ';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'SECURITY_USER_ROLE_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  SECURITY_USER_ROLE_TG';   
      end if;

-- prompt Dropping T_SECURITY_USER_ROLE...
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_SECURITY_USER_ROLE';
      if   num=1   then 
          execute immediate 'drop table T_SECURITY_USER_ROLE'; 
      end   if;  
      
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_STORE_TYPE_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_STORE_TYPE_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_STORE_TYPE_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_STORE_TYPE_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_STORE_TYPE';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_STORE_TYPE'; 
      end   if; 
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_STORE_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_STORE_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_STORE_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_STORE_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_STORE';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_STORE'; 
      end   if; 
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_SECTION_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_SECTION_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_SECTION_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_SECTION_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_SECTION';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_SECTION'; 
      end   if;
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_STORE_CUSTOMER_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_STORE_CUSTOMER_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_STORE_CUSTOMER_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_STORE_CUSTOMER_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_STORE_CUSTOMER';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_STORE_CUSTOMER'; 
      end   if; 
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_COMPANY_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_COMPANY_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_COMPANY_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_COMPANY_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_COMPANY';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_COMPANY'; 
      end   if; 
      
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_POSITION_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_POSITION_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_POSITION_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_POSITION_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_POSITION';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_POSITION'; 
      end   if; 
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_ENTRY_HISTORY_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_ENTRY_HISTORY_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_ENTRY_HISTORY_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_ENTRY_HISTORY_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_ENTRY_HISTORY';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_ENTRY_HISTORY'; 
      end   if; 
      
      
--部门表
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_DEPT_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_DEPT_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_DEPT_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_DEPT_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_DEPT';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_DEPT'; 
      end   if;
 

--公司部门中间表 
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_COMPANY_DEPT_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_COMPANY_DEPT_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_COMPANY_DEPT_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_COMPANY_DEPT_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_COMPANY_DEPT';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_COMPANY_DEPT'; 
      end   if; 
      
    
--员工表
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_STAFF_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_STAFF_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_STAFF_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_STAFF_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_STAFF';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_STAFF'; 
      end   if; 
      
      
--我的任务表
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_MYTASK_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_MYTASK_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_MYTASK_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_MYTASK_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_MYTASK';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_MYTASK'; 
      end   if; 
--文件信息表
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_FILE_INFO_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_FILE_INFO_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_FILE_INFO_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_FILE_INFO_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_FILE_INFO';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_FILE_INFO'; 
      end   if; 
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_SCHEDULE_JOB_SQ'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_SCHEDULE_JOB_SQ';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_SCHEDULE_JOB_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_SCHEDULE_JOB_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_SCHEDULE_JOB';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_SCHEDULE_JOB'; 
      end   if;  
end;
/


---------------------------------------------------------------
---------------------------------------------------------------
-------------------------T-A-B-L-E-----------------------------
---------------------------------------------------------------
---------------------------------------------------------------

-- prompt Creating T_COMMON_TUNNEL...
create table T_COMMON_TUNNEL
(
  id    NUMBER not null,
  name  VARCHAR2(30) not null,
  length NUMBER,
  sn     VARCHAR2(20) not null,
  responsibility_id number  not null,
  construct_id      number  not null,
  operation_id      number  not null,
  camera            varchar2(200),
  maxview_config_id number  not null,
  crt_time          date
);
alter table T_COMMON_TUNNEL
  add constraint PK_T_COMMON_TUNNEL_ID primary key (ID);

-- create
create sequence COMMON_TUNNEL_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER COMMON_TUNNEL_TG
  BEFORE INSERT ON T_COMMON_TUNNEL
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_TUNNEL_SEQUENCE.nextval into :new.id from dual;
end COMMON_TUNNEL_TG;
/
alter trigger COMMON_TUNNEL_TG enable;



-- prompt Creating T_COMMON_area...
create table T_COMMON_area
(
  id    NUMBER not null,
  name  VARCHAR2(30) not null,
  sn    varchar2(20) not null,
  location  VARCHAR2(500),
  tunnel_id NUMBER not null,
  camera    varchar2(200),
  crt_Time        DATE not null
);
alter table T_COMMON_area
  add constraint common_area_ID primary key (ID);

-- create sequence
create sequence COMMON_area_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER COMMON_area_TG
  BEFORE INSERT ON T_COMMON_area
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_area_SEQUENCE.nextval into :new.id from dual;
end COMMON_area_TG;
/



-- prompt Creating T_SECURITY_DOMAIN...
create table T_SECURITY_DOMAIN
(
  id   NUMBER not null,
  name VARCHAR2(30) not null
);
alter table T_SECURITY_DOMAIN
  add constraint SECURITY_DOMAINID primary key (ID);


-- prompt Creating T_SECURITY_LOG...
create table T_SECURITY_LOG
(
  id                VARCHAR2(50) not null,
  method            VARCHAR2(200) not null,
  req_User          VARCHAR2(30),
  req_Role          VARCHAR2(30),
  req_Permission    VARCHAR2(30),
  crt_Time          DATE not null
);
alter table T_SECURITY_LOG
  add constraint SECURITY_LOG_ID primary key (ID);
  
-- prompt Creating T_OPERATION_LOG...
/*==============================================================*/
/* Table: T_OPERATION_LOG                                       */
/*==============================================================*/
-- Create table 
create table T_OPERATION_LOG
(
  id          VARCHAR2(50 CHAR) not null,
  module_type VARCHAR2(50 CHAR),
  req_id      VARCHAR2(10 CHAR),
  req_ip      VARCHAR2(20 CHAR),
  req_user    VARCHAR2(20 CHAR),
  method      VARCHAR2(200 CHAR),
  params      VARCHAR2(200 CHAR),
  result      VARCHAR2(10 CHAR) ,
  crt_time    DATE,
  description VARCHAR2(200),
  CONSTRAINT PK_T_OPERATION_LOG PRIMARY KEY ("ID")
);


--shiro table below...


CREATE TABLE "T_SECURITY_USER"  (
   "ID"                 NUMBER                          NOT NULL,
   "NAME"               VARCHAR2(50 CHAR)               NOT NULL,
   "PASSWORD"           VARCHAR2(50 CHAR)               NOT NULL,
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_SECURITY_USER PRIMARY KEY ("ID")
);

create sequence SECURITY_USER_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;

CREATE OR REPLACE TRIGGER SECURITY_USER_TG
  BEFORE INSERT ON T_SECURITY_USER
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select SECURITY_USER_SEQUENCE.nextval into :new.id from dual;
end SECURITY_USER_TG;
/
 
      
CREATE TABLE "T_SECURITY_ROLE"  (
   "ID"                 NUMBER                          NOT NULL,
   "ROLE_CODE"               VARCHAR2(50 CHAR)               NOT NULL,
   "ROLE_DESC"               VARCHAR2(50 CHAR),
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_SECURITY_ROLE PRIMARY KEY ("ID")
);

create sequence SECURITY_ROLE_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;

CREATE OR REPLACE TRIGGER SECURITY_ROLE_TG
  BEFORE INSERT ON T_SECURITY_ROLE
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select SECURITY_ROLE_SEQUENCE.nextval into :new.id from dual;
end SECURITY_ROLE_TG;
/



CREATE TABLE "T_SECURITY_PERMISSION"  (
   "ID"                 NUMBER                          NOT NULL,
   "PER_CODE"               VARCHAR2(50 CHAR)               NOT NULL,
   "PER_DESC"               VARCHAR2(50 CHAR),
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_SECURITY_PERMISSION PRIMARY KEY ("ID")
);

create sequence SECURITY_PERMISSION_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;

CREATE OR REPLACE TRIGGER SECURITY_PERMISSION_TG
  BEFORE INSERT ON T_SECURITY_PERMISSION
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select SECURITY_PERMISSION_SEQUENCE.nextval into :new.id from dual;
end SECURITY_PERMISSION_TG;
/




--角色权限表
CREATE TABLE "T_SECURITY_ROLE_PERMISSION"  (
   ID                   NUMBER                          NOT NULL,
   "ROLE_ID"            NUMBER,
   "PER_ID"             NUMBER,
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_SECURITY_ROLE_PERMISSION PRIMARY KEY (ID)
);


create sequence SECURITY_ROLE_PERMISSION_SQ
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;

CREATE OR REPLACE TRIGGER SECURITY_ROLE_PERMISSION_TG
  BEFORE INSERT ON T_SECURITY_ROLE_PERMISSION
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select SECURITY_ROLE_PERMISSION_SQ.nextval into :new.id from dual;
end SECURITY_ROLE_PERMISSION_TG;
/



--用户角色表

CREATE TABLE "T_SECURITY_USER_ROLE"  (
   ID                   NUMBER                          NOT NULL,
   "USER_ID"            NUMBER,
   "ROLE_ID"            NUMBER,
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_SECURITY_USER_ROLE PRIMARY KEY (ID)
);

create sequence SECURITY_USER_ROLE_SQ
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;

CREATE OR REPLACE TRIGGER SECURITY_USER_ROLE_TG
  BEFORE INSERT ON T_SECURITY_USER_ROLE
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select SECURITY_USER_ROLE_SQ.nextval into :new.id from dual;
end SECURITY_USER_ROLE_TG;
/



--外键
ALTER TABLE "T_SECURITY_ROLE_PERMISSION"
   ADD CONSTRAINT FK_T_R_REFERENCE_T_R FOREIGN KEY ("ROLE_ID")
      REFERENCES "T_SECURITY_ROLE" ("ID");

ALTER TABLE "T_SECURITY_ROLE_PERMISSION"
   ADD CONSTRAINT FK_T_R_REFERENCE_T_P FOREIGN KEY ("PER_ID")
      REFERENCES "T_SECURITY_PERMISSION" ("ID");

ALTER TABLE "T_SECURITY_USER_ROLE"
   ADD CONSTRAINT FK_T_U_REFERENCE_T_U FOREIGN KEY ("USER_ID")
      REFERENCES "T_SECURITY_USER" ("ID");

ALTER TABLE "T_SECURITY_USER_ROLE"
   ADD CONSTRAINT FK_T_U_REFERENCE_T_R FOREIGN KEY ("ROLE_ID")
      REFERENCES "T_SECURITY_ROLE" ("ID");
   
   
      
--管廊仓类型表
CREATE TABLE T_COMMON_STORE_TYPE  (
   "ID"                 NUMBER               NOT NULL,
   NAME			VARCHAR2(50) not null,
   SN       varchar2(20) not null,
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_COMMON_STORE_TYPE PRIMARY KEY ("ID")
);


--create sequence
create sequence COMMON_STORE_TYPE_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_STORE_TYPE_TG
  BEFORE INSERT ON T_COMMON_STORE_TYPE
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_STORE_TYPE_SEQUENCE.nextval into :new.id from dual;
end COMMON_STORE_TYPE_TG;
/
--管廊仓表
CREATE TABLE T_COMMON_STORE  (
   "ID"                 NUMBER               NOT NULL,
   name                 varchar2(100),
   sn                   varchar2(20) not null,
   "TUNNEL_ID"          NUMBER               NOT NULL,
   store_type_id	    NUMBER		           NOT NULL,
   camera             varchar2(200),
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_COMMON_STORE PRIMARY KEY ("ID")
);
--create sequence
create sequence COMMON_STORE_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_STORE_TG
  BEFORE INSERT ON T_COMMON_STORE
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_STORE_SEQUENCE.nextval into :new.id from dual;
end COMMON_STORE_TG;
/

--仓节
CREATE TABLE T_COMMON_SECTION  (
   id                NUMBER               NOT NULL,
   name                 varchar2(50)		not null,
   tunnel_id            number,
   store_id          NUMBER               NOT NULL,
   area_id          NUMBER               NOT NULL,
   total_cable_number number,
   camera             varchar2(200),
   start_point        varchar2(100),
   end_point          varchar2(100),
   crt_time          DATE,
   CONSTRAINT PK_T_COMMON_SECTION PRIMARY KEY ("ID")
);


--create sequence
create sequence COMMON_SECTION_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_SECTION_TG
  BEFORE INSERT ON T_COMMON_SECTION
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_SECTION_SEQUENCE.nextval into :new.id from dual;
end COMMON_SECTION_TG;
/


--公司表
CREATE TABLE T_COMMON_COMPANY(
	id                NUMBER               NOT NULL,
	name			varchar2(100),
	crt_time          DATE,
   CONSTRAINT PK_T_COMMON_COMPANY PRIMARY KEY ("ID")
);
--create sequence
create sequence COMMON_COMPANY_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_COMPANY_TG
  BEFORE INSERT ON T_COMMON_COMPANY
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_COMPANY_SEQUENCE.nextval into :new.id from dual;
end COMMON_COMPANY_TG;
/


--职位表
CREATE TABLE T_COMMON_POSITION(
	id                NUMBER               NOT NULL,
	name			varchar2(100),
	crt_time          DATE,
   CONSTRAINT PK_T_COMMON_POSITION PRIMARY KEY ("ID")
);
--create sequence
create sequence COMMON_POSITION_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_POSITION_TG
  BEFORE INSERT ON T_COMMON_POSITION
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_POSITION_SEQUENCE.nextval into :new.id from dual;
end COMMON_POSITION_TG;
/


--部门表
CREATE TABLE T_COMMON_DEPT(
	id                NUMBER               NOT NULL,
	NAME		VARCHAR2(20),
	crt_time	date,
   CONSTRAINT PK_T_COMMON_DEPT PRIMARY KEY ("ID")
);


--create sequence
create sequence COMMON_DEPT_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_DEPT_TG
  BEFORE INSERT ON T_COMMON_DEPT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_DEPT_SEQUENCE.nextval into :new.id from dual;
end COMMON_DEPT_TG;
/


--公司部门中间表
CREATE TABLE T_COMMON_COMPANY_DEPT(
	id                NUMBER               NOT NULL,
	company_id	 NUMBER               NOT NULL,
	dept_id		 NUMBER               NOT NULL,
   CONSTRAINT PK_T_COMMON_COMPANY_DEPT PRIMARY KEY ("ID")
);


--create sequence
create sequence COMMON_COMPANY_DEPT_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_COMPANY_DEPT_TG
  BEFORE INSERT ON T_COMMON_COMPANY_DEPT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_COMPANY_DEPT_SEQUENCE.nextval into :new.id from dual;
end COMMON_COMPANY_DEPT_TG;
/


--员工表
CREATE TABLE T_COMMON_STAFF(
  id                NUMBER               NOT NULL,
  NAME    VARCHAR2(20),
  dept_id    NUMBER               NOT NULL,
  position_id  NUMBER               NOT NULL,
  account_id  NUMBER               NOT NULL,
  crt_time  date,
   CONSTRAINT PK_T_COMMON_STAFF PRIMARY KEY ("ID")
);


--create sequence
create sequence COMMON_STAFF_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_STAFF_TG
  BEFORE INSERT ON T_COMMON_STAFF
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_STAFF_SEQUENCE.nextval into :new.id from dual;
end COMMON_STAFF_TG;
/

--我的任务表
CREATE TABLE T_COMMON_MYTASK(
  id                NUMBER               NOT NULL,
  process_definition_id varchar2(100) not null,--流程定义id
  request_staff_id  number    not null,--请求人id
  approver_id   number    not null,--审批人id
  process_type    number(1)    not null,--流程类型：入廊；巡检；....
  status          number(1)    not null,--流程状态：审核中，通过，拒绝...
  crt_time  date,--申请时间
   CONSTRAINT PK_T_COMMON_MYTASK PRIMARY KEY ("ID")
);


--create sequence
create sequence COMMON_MYTASK_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_MYTASK_TG
  BEFORE INSERT ON T_COMMON_MYTASK
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_MYTASK_SEQUENCE.nextval into :new.id from dual;
end COMMON_MYTASK_TG;
/

--文件信息表
CREATE TABLE T_COMMON_FILE_INFO(
  id              NUMBER          NOT NULL,
  NAME    VARCHAR2(100)            , --文件名称
  file_type number(1) not null, --文件类型
  doc_type    number(1) not null, --资料类型
  path    varchar2(100)   , --路径
  crt_time  date      , --上传时间
   CONSTRAINT PK_T_COMMON_FILE_INFO PRIMARY KEY ("ID")
);


--create sequence
create sequence COMMON_FILE_INFO_SEQUENCE
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_FILE_INFO_TG
  BEFORE INSERT ON T_COMMON_FILE_INFO
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_FILE_INFO_SEQUENCE.nextval into :new.id from dual;
end COMMON_FILE_INFO_TG;
/

CREATE TABLE T_COMMON_SCHEDULE_JOB  (
   job_id               NUMBER      NOT NULL,
   job_name   varchar2(50)    not null,
   job_group            varchar2(50)    not null,
   job_status   NUMBER(1)      NOT NULL,
   job_class    varchar2(200)  not null,
   job_method   varchar2(200)  not null,
   cron_expression      varchar2(50)    not null,  
   description    varchar2(100),  
   CONSTRAINT PK_T_COMMON_SCHEDULE_JOB PRIMARY KEY ("JOB_ID")
);


--create sequence
create sequence COMMON_SCHEDULE_JOB_SQ
start with 1000
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER COMMON_SCHEDULE_JOB_TG
  BEFORE INSERT ON T_COMMON_SCHEDULE_JOB
  FOR EACH ROW
  WHEN (new.JOB_ID is null)
begin
  select COMMON_SCHEDULE_JOB_SQ.nextval into :new.JOB_ID from dual;
end COMMON_SCHEDULE_JOB_TG;
/



