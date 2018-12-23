---------------------------------------------------------------
---------------------------------------------------------------
-------------------------C-O-M-M-O-N---------------------------
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
--user
CREATE TABLE "T_SECURITY_USER"  (
   id                 NUMBER                          NOT NULL,
   name               VARCHAR2(50 CHAR)               NOT NULL,
   password           VARCHAR2(100 CHAR)              NOT NULL,
   crt_time           DATE,
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
 
--role   
CREATE TABLE "T_SECURITY_ROLE"  (
   id                 NUMBER                          NOT NULL,
   role_name          VARCHAR2(50 CHAR)               NOT NULL,
   crt_time           DATE,
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


--监测对象关联其他对象表
CREATE TABLE T_EM_OBJ_BIND(
  id              NUMBER          NOT NULL,
  object_id       number          not null,   
  bind_id         number          not null,
  type            number(1)       not null,
  crt_time        DATE,       
   CONSTRAINT PK_T_EM_OBJ_BIND PRIMARY KEY ("ID")
);


--create sequence
create sequence EM_OBJ_BIND_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER EM_OBJ_BIND_TG
  BEFORE INSERT ON T_EM_OBJ_BIND
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select EM_OBJ_BIND_SQ.nextval into :new.id from dual;
end EM_OBJ_BIND_TG;
/

--permission
CREATE TABLE "T_SECURITY_PERMISSION"  (
   id                 NUMBER                          NOT NULL,
   menu_code          VARCHAR2(50 CHAR)               not null,
   menu_name          VARCHAR2(50 CHAR)               not null,
   permission_code    VARCHAR2(50 CHAR)               not null,
   permission_name    VARCHAR2(50 CHAR)               not null,
   crt_time           DATE,
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




--role-permission
CREATE TABLE "T_SECURITY_ROLE_PERMISSION"  (
   ID                   NUMBER                          NOT NULL,
   r_id                  NUMBER                          not null,
   p_id                  NUMBER                          not null,
   crt_time             DATE,
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



--user-role
CREATE TABLE "T_SECURITY_USER_ROLE"  (
   ID                   NUMBER                          NOT NULL,
   u_id                 NUMBER                          not null,
   r_id                 NUMBER                          not null,
   crt_time             DATE,
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

--管廊仓类型表
CREATE TABLE T_COMMON_STORE_TYPE  (
   "ID"                 NUMBER               NOT NULL,
   NAME     VARCHAR2(50) not null,
   SN       varchar2(20) not null,
   PARENT   varchar2(20),
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
   PARENT               varchar2(20),
   "TUNNEL_ID"          NUMBER               NOT NULL,
   store_type_id      NUMBER               NOT NULL,
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
   name                 varchar2(50)    not null,
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
  name      varchar2(100),
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
  name      varchar2(100),
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
  NAME    VARCHAR2(20),
  crt_time  date,
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
  company_id   NUMBER               NOT NULL,
  dept_id    NUMBER               NOT NULL,
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

----------------------------------------------------------------
---------------------------E-M----------------------------------
----------------------------------------------------------------
----------------------------------------------------------------
--相关单位
CREATE TABLE T_EM_RELATED_UNIT(
  id                NUMBER               NOT NULL,
  name      varchar2(100)               NOT NULL,
  contact     varchar2(50),
  tel     varchar2(20),
  unit_type NUMBER               NOT NULL,--单位类别：学校，医院，机关单位
  address   varchar2(100),--地址
  section_ids varchar2(100) not null,--关联哪个section
  longitude   varchar2(20), --经度
  latitude    varchar2(20), --维度
  crt_time          DATE,
   CONSTRAINT PK_T_EM_RELATED_UNIT PRIMARY KEY ("ID")
);
--create sequence
create sequence EM_RELATED_UNIT_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER EM_RELATED_UNIT_TG
  BEFORE INSERT ON T_EM_RELATED_UNIT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select EM_RELATED_UNIT_SQ.nextval into :new.id from dual;
end EM_RELATED_UNIT_TG;
/
--应急预案表
CREATE TABLE T_EM_PLAN(
  id              NUMBER          NOT NULL,
  process_key     varchar2(100)   not null, 
  task_key        varchar2(100)   not null,
  task_name       varchar2(100)   not null,      
  target_key      number(1)       not null,
  target_value    varchar2(100)   not null,
  action_key      number(1)       not null,
  action_value    varchar2(100)   not null,
  finish_key      number(1)       not null,
  finish_value    number          not null,
  is_finished     number(1)       not null,
  crt_time        DATE,
  CONSTRAINT PK_T_EM_PLAN PRIMARY KEY ("ID")
);
--create sequence
create sequence EM_PLAN_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER EM_PLAN_TG
  BEFORE INSERT ON T_EM_PLAN
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select EM_PLAN_SQ.nextval into :new.id from dual;
end EM_PLAN_TG;
/

---------------------------------------------------------------
---------------------------------------------------------------
-------------------------M-A-M---------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
--告警
create table T_MAM_ALARM
(
  id           NUMBER not null,
  alarm_date   DATE not null,
  alarm_name   varchar2(50),
  alarm_level  NUMBER(1) not null,
  tunnel_id    number not null,
  object_id    NUMBER not null,
  object_name  varchar2(100) not null,
  is_distribute number(1) not null,
  alarm_source varchar2(100),
  longitude    varchar2(50),
  latitude     varchar2(50),
  cleaned      NUMBER(1) not null,
  cleaned_date DATE,
  description  VARCHAR2(200)
);
alter table T_MAM_ALARM
  add constraint MAM_ALARM_ID primary key (ID);

create sequence MAM_ALARM_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

CREATE OR REPLACE TRIGGER MAM_ALARM_TG
  BEFORE INSERT ON T_MAM_ALARM
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select MAM_ALARM_SEQUENCE.nextval into :new.id from dual;
end MAM_ALARM_TG;
/
alter trigger MAM_ALARM_TG enable;
-- prompt createing T_MAM_MEASVALUE_TABDICT
CREATE TABLE T_MAM_MEASVALUE_TABDICT(
	ID                NUMBER NOT NULL,
	DataType          NUMBER,
	SeqNo             NUMBER,
	HisTabName        VARCHAR2(200),
	Days              NUMBER,
	StartDate         DATE,
	EndDate           DATE
);
alter table T_MAM_MEASVALUE_TABDICT add constraint MAM_MEASVALUE_TABDICT_ID primary key (ID);
  
-- create MAM_MEASVALUE_TABDICT_SEQUENCE
create sequence MAM_MEASVALUE_TABDICT_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger MAM_MEASVALUE_TABDICT_TG
CREATE OR REPLACE TRIGGER MAM_MEASVALUE_TABDICT_TG
  BEFORE INSERT ON T_MAM_MEASVALUE_TABDICT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select MAM_MEASVALUE_TABDICT_SEQUENCE.nextval into :new.id from dual;
end MAM_MEASVALUE_TABDICT_TG;
/
alter trigger MAM_MEASVALUE_TABDICT_TG enable;


-- prompt Creating T_MAM_MEASVALUE_AI
create table T_MAM_MEASVALUE_AI
(
  time            DATE not null,
  Object_ID        NUMBER not null,
  CV              NUMBER not null
);
Create Index ai_time on T_MAM_MEASVALUE_AI(time,Object_ID);

-- prompt Creating T_MAM_MEASVALUE_DI
create table T_MAM_MEASVALUE_DI
(
  time             DATE not null,
  Object_ID         NUMBER not null,
  CV               NUMBER not null
);
Create Index di_time on T_MAM_MEASVALUE_DI(time,Object_ID);

-- prompt Creating T_MAM_MEASVALUE_SI
create table T_MAM_MEASVALUE_SI
(
  time             DATE not null,
  Object_ID         NUMBER not null,
  CV               NUMBER not null
);
Create Index si_time on T_MAM_MEASVALUE_SI(time,Object_ID);

-- prompt Creating T_MAM_MEASVALUE_SO
create table T_MAM_MEASVALUE_SO
(
  time             DATE not null,
  Object_ID         NUMBER not null,
  CV               VARCHAR2(4000)
);
Create Index so_time on T_MAM_MEASVALUE_SO(time,Object_ID);

-- prompt Creating T_MAM_MEASVALUE_DASSPECTRUM
create table T_MAM_MEASVALUE_DASSPECTRUM
(
  time                   DATE not null,
  Object_ID               NUMBER not null,
  Zone_ID                 NUMBER,
  Channel_Count           NUMBER not null,
  Channel_ID              NUMBER,
  Samplerate             NUMBER,
  Fft_Size                NUMBER,
  Data                   BLOB
);
Create Index da_time on T_MAM_MEASVALUE_DASSPECTRUM(time,Object_ID);

-- prompt Creating T_MAM_MEASVALUE_DISTRIBUTE
create table T_MAM_MEASVALUE_DISTRIBUTE(
  time              DATE NOT NULL,
  Object_ID         NUMBER NOT NULL,
  space_precision   number not null,
  dcv               varchar2(4000)
);
Create Index dis_time on T_MAM_MEASVALUE_DISTRIBUTE(time,Object_ID);

-- creating T_MAM_MEASOBJ
CREATE TABLE T_MAM_MEASOBJ(
  ID                        NUMBER NOT NULL,
  tunnel_id                 number not null,
  store_id                  number not null,
  area_id                   number,
  section_ID                NUMBER not null,
  Name                      VARCHAR2(50) NOT NULL,
  DataType_ID               NUMBER NOT NULL,
  ObjType_ID                NUMBER NOT NULL,
  Actived                   NUMBER(1) NOT NULL,
  Description               VARCHAR2(100),
  Longitude                 VARCHAR2(50),
  Latitude                  VARCHAR2(50),
  height                    VARCHAR2(50),
  deviation                 NUMBER
);
alter table T_MAM_MEASOBJ add constraint MAM_MEASOBJ_ID primary key (ID);


-- creating T_MAM_MEASOBJ_AI
CREATE TABLE T_MAM_MEASOBJ_AI(
  ID                        NUMBER NOT NULL,
  Refresh_Time              DATE,
  CV                        Number
);
alter table T_MAM_MEASOBJ_AI add constraint MAM_MEASOBJ_AI_ID primary key (ID);

--开关量
CREATE TABLE T_MAM_MEASOBJ_DI(
  ID                        NUMBER NOT NULL,
  Refresh_Time              DATE,
  CV                        Number(1),
  CONSTRAINT PK_T_MAM_MEASOBJ_DI PRIMARY KEY ("ID")
);

--状态量
CREATE TABLE T_MAM_MEASOBJ_SI(
  ID                        NUMBER NOT NULL,
  Refresh_Time              DATE,
  CV                        Number(1),
  CONSTRAINT PK_T_MAM_MEASOBJ_SI PRIMARY KEY ("ID")
);

-- creating T_MAM_MEASOBJ_Distribute
CREATE TABLE T_MAM_MEASOBJ_Distribute(
  ID                        NUMBER NOT NULL,
  Refresh_Time              DATE,
  space_Precision           Number,
  dcv                       VARCHAR2(4000)
);
alter table T_MAM_MEASOBJ_Distribute add constraint MAM_MEASOBJ_distribute_ID primary key (ID);

-- creating T_MAM_MEASOBJ_SO
CREATE TABLE T_MAM_MEASOBJ_SO(
  ID                        NUMBER NOT NULL,
  Refresh_Time              DATE,
  CV                        VARCHAR2(400)
);
alter table T_MAM_MEASOBJ_SO add constraint MAM_MEASOBJ_SO_ID primary key (ID);

-- create relation of man & so 人和定位器的关系
CREATE TABLE T_MAM_PERSON_LOCATOR(
  PERSION_ID                NUMBER NOT NULL,
  LOCATOR_ID                NUMBER NOT NULL,
  -- 起关联时间
  Relate_Time               DATE,
  STATUS                    VARCHAR2(20)
);


-- creating T_MAM_Video_Scene
CREATE TABLE T_MAM_Video_Scene(
  ID                        NUMBER NOT NULL,
  name                      Varchar2(50),
  tunnel_id                 number not null,
  is_Loop                   NUMBER(1) NOT NULL,
  loop_Index                number
);
alter table T_MAM_Video_Scene add constraint MAM_Video_Scene_ID primary key (ID);

-- create
create sequence MAM_Video_Scene_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER MAM_Video_Scene_TG
  BEFORE INSERT ON T_MAM_Video_Scene
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select MAM_Video_Scene_SEQUENCE.nextval into :new.id from dual;
end MAM_Video_Scene_TG;
/
alter trigger MAM_Video_Scene_TG enable;


-- creating T_MAM_Video_Extend_Scene
CREATE TABLE T_MAM_Video_Extend_Scene(
  ID                        NUMBER NOT NULL,
  name                      Varchar2(50),
  tunnel_id                 number not null,
  is_Loop                   NUMBER(1) NOT NULL,
  loop_Index                number
);
alter table T_MAM_Video_Extend_Scene add constraint MAM_Video_Extend_Scene_ID primary key (ID);

-- create
create sequence MAM_Extend_Scene_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER MAM_Video_Extend_Scene_TG
  BEFORE INSERT ON T_MAM_Video_Extend_Scene
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select MAM_Extend_Scene_SEQUENCE.nextval into :new.id from dual;
end MAM_Video_Extend_Scene_TG;
/
alter trigger MAM_Video_Extend_Scene_TG enable;


-- creating T_MAM_Video_Server
CREATE TABLE T_MAM_Video_Server(
  ID                        NUMBER NOT NULL,
  name                      Varchar2(50),
  vendor                    number not null,
  vendor_version            number,
  ip                        varchar2(20),
  port                      number,
  username                  varchar2(50),
  password                  varchar2(50),
  channel_num               number  
);
alter table T_MAM_Video_Server add constraint MAM_Video_Server_ID primary key (ID);

-- create
create sequence MAM_Video_Server_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER MAM_Video_Server_TG
  BEFORE INSERT ON T_MAM_Video_Server
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select MAM_Video_Server_SEQUENCE.nextval into :new.id from dual;
end MAM_Video_Server_TG;
/
alter trigger MAM_Video_Server_TG enable;

--creating t_mam_video_preset
create Table t_mam_video_preset(
  ID                           Number not null,
  video_id                     Number not null,
  preset_name                  varchar2(50),
  longitude                    varchar2(50),
  latitude                     varchar2(50),
  height                       varchar2(50),
  pitch                        varchar2(50),
  roll                         varchar2(50),
  heading                      varchar2(50)  
);
alter table t_mam_video_preset add constraint MAM_Video_Preset_ID primary key (ID); 

-- create
create sequence mam_video_preset_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER mam_video_preset_TG
  BEFORE INSERT ON T_mam_video_preset
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select mam_video_preset_SEQUENCE.nextval into :new.id from dual;
end mam_video_preset_TG;
/
alter trigger mam_video_preset_TG enable;

-- creating T_MAM_Video
create Table t_mam_video(
  ID                           Number not null,
  server_id                    number not null,
  video_scene_id               number,
  video_extend_scene_id        number,
  channel_no                   number not null
);
alter table T_MAM_Video add constraint MAM_Video_ID primary key (ID);

--measObj周期统计表
CREATE TABLE T_MAM_MEASOBJ_REPORT(
   id   number  not null,
   name         varchar2(50),
   object_id  number  not null,
   tunnel_id  number  not null,
   object_type  number  not null,
   time_type  number  not null,
   s_time date  not null,
   e_time date  not null,
   max_value  number    ,
   min_value  number    ,
   avg_value  number    ,
   CONSTRAINT PK_T_MAM_MEASOBJ_REPORT PRIMARY KEY ("ID")
);


--create sequence
create sequence MAM_MEASOBJ_REPORT_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER MAM_MEASOBJ_REPORT_TG
  BEFORE INSERT ON T_MAM_MEASOBJ_REPORT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select MAM_MEASOBJ_REPORT_SQ.nextval into :new.id from dual;
end MAM_MEASOBJ_REPORT_TG;
/


--measObj映射表
CREATE TABLE T_MAM_MEASOBJ_MAP(
   id   number  not null,
   object_id  number  not null,
   input_value  number  not null,
   object_id2 number  not null,
   output_value number  not null,
   crt_time     date    not null,
   CONSTRAINT PK_T_MAM_MEASOBJ_MAP PRIMARY KEY ("ID")
);


--create sequence
create sequence MAM_MEASOBJ_MAP_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER MAM_MEASOBJ_MAP_TG
  BEFORE INSERT ON T_MAM_MEASOBJ_MAP
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select MAM_MEASOBJ_MAP_SQ.nextval into :new.id from dual;
end MAM_MEASOBJ_MAP_TG;
/

--maxview配置表
CREATE TABLE T_MAM_MAXVIEW_CONFIG(
  id                NUMBER               NOT NULL,
  name    varchar2(50)  not null,
  ip    varchar2(50)  not null,
  port    varchar2(10)  not null,
  username  varchar2(50)  not null,
  password  varchar2(100) not null,
  crt_time  date,
  CONSTRAINT PK_T_MAM_MAXVIEW_CONFIG PRIMARY KEY ("ID")
);


--create sequence
create sequence MAM_MAXVIEW_CONFIG_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER MAM_MAXVIEW_CONFIG_TG
  BEFORE INSERT ON T_MAM_MAXVIEW_CONFIG
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select MAM_MAXVIEW_CONFIG_SEQUENCE.nextval into :new.id from dual;
end MAM_MAXVIEW_CONFIG_TG;
/

---------------------------------------------------------------
---------------------------------------------------------------
-------------------------O-A-M---------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
-- 能耗表
create table T_OAM_CONSUME
(
  ID         NUMBER NOT NULL,
  TUNNEL_ID  NUMBER NOT NULL,
  STORE_ID   NUMBER NOT NULL,
  AREA_ID    NUMBER NOT NULL,
  OBJECT_TYPE NUMBER NOT NULL,
  ENERGY_TYPE NUMBER NOT NULL,
  OBJECT_ID  NUMBER,
  COMPUTE    VARCHAR2(50)
);
alter table T_OAM_CONSUME add constraint OAM_CONSUME_ID primary key(ID);

-- create OAM_CONSUME_SQ
create sequence OAM_CONSUME_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger OAM_CONSUME_TG
CREATE OR REPLACE TRIGGER OAM_CONSUME_TG
  BEFORE INSERT ON T_OAM_CONSUME
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OAM_CONSUME_SQ.nextval into :new.id from dual;
end OAM_CONSUME_TG;
/
alter trigger OAM_CONSUME_TG enable;

-- prompt create T_OAM_CONSUME_DATA
CREATE TABLE T_OAM_CONSUME_DATA
(
  ID         NUMBER NOT NULL,
  DIRECT    NUMBER,
  INDIRECT   NUMBER,
  TIME       DATE
);
Create Index i_consume on T_OAM_CONSUME_DATA(TIME,ID);

--重大事件表
CREATE TABLE T_OAM_EVENT  (
   ID                NUMBER                      NOT NULL,
   NAME              varchar2(100)               NOT NULL,
   STAFF_ID          NUMBER                      NOT NULL,
   RANGE             NUMBER                      NOT NULL,
   EVENT_LEVEL       NUMBER(1)                   NOT NULL,
   Longitude         VARCHAR2(50),
   Latitude          VARCHAR2(50),
   START_TIME        DATE,
   END_TIME          DATE,
   CRT_TIME          DATE,
   CONSTRAINT PK_T_OAM_EVENT PRIMARY KEY ("ID")
);


--create sequence OAM_EVENT_SEQUENCE
create sequence OAM_EVENT_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger OAM_EVENT_TG
CREATE OR REPLACE TRIGGER OAM_EVENT_TG
  BEFORE INSERT ON T_OAM_EVENT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OAM_EVENT_SEQUENCE.nextval into :new.id from dual;
end OAM_EVENT_TG;
/


--能耗表
CREATE TABLE T_OAM_ENERGY  (
   "ID"                 NUMBER                          NOT NULL,
   "TUNNEL_ID"              NUMBER               NOT NULL,
   object_type              number               not null,
   "VALUE"                  NUMBER,
   "UNIT_PRICE"             NUMBER              NOT NULL,
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_OAM_ENERGY PRIMARY KEY ("ID")
);


--create sequence OAM_ENERGY_SEQUENCE
create sequence OAM_ENERGY_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger OAM_ENERGY_TG
CREATE OR REPLACE TRIGGER OAM_ENERGY_TG
  BEFORE INSERT ON T_OAM_ENERGY
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OAM_ENERGY_SEQUENCE.nextval into :new.id from dual;
end OAM_ENERGY_TG;
/


--客户表
CREATE TABLE T_OAM_CUSTOMER(
  id                 NUMBER               NOT NULL,
  COMPANY_ID         NUMBER               NOT NULL,
  contact            varchar2(50)         not null,
  tel                varchar2(20)         not null,
  contact2           varchar2(50),
  tel2               varchar2(20),
  contact3           varchar2(50),
  tel3               varchar2(20),
  crt_time           DATE,
  CONSTRAINT PK_T_OAM_CUSTOMER PRIMARY KEY ("ID")
);


--create sequence
create sequence OAM_CUSTOMER_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER OAM_CUSTOMER_TG
  BEFORE INSERT ON T_OAM_CUSTOMER
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OAM_CUSTOMER_SEQUENCE.nextval into :new.id from dual;
end OAM_CUSTOMER_TG;
/


--入廊申请记录
CREATE TABLE T_OAM_REQ_HISTORY(
  id                NUMBER               NOT NULL,
  staff_id          NUMBER               NOT NULL,
  approver_id       NUMBER               NOT NULL,
  tunnel_id         NUMBER               NOT NULL,
  action            NUMBER               NOT NULL,
  pre_time          date,
  enter_time        date,
  exit_time         date,
  visitor_number    number,
  visitor_company   number    not null,
  visitor_info    varchar2(500),
  create_time            date,
  process_type           number not null,
  process_status         varchar2(20) not null,
  process_instance_id    varchar2(100),
  is_finished            number ,
  CONSTRAINT PK_T_OAM_REQ_HISTORY PRIMARY KEY ("ID")
);


--create sequence
create sequence OAM_REQ_HISTORY_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER OAM_REQ_HISTORY_TG
  BEFORE INSERT ON T_OAM_REQ_HISTORY
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OAM_REQ_HISTORY_SEQUENCE.nextval into :new.id from dual;
end OAM_REQ_HISTORY_TG;
/

--管线表
CREATE TABLE T_OAM_CABLE(
  id              varchar2(100)    NOT NULL,
  contract_id     varchar2(100)    not null,--合同编号
  cable_name      varchar2(100),--管线名称
  cable_length  number,--管线长度
  cable_status  number(1)   not null,--管线状态
  cable_location  varchar2(100),--管线位置
  crt_time          DATE,
   CONSTRAINT PK_T_OAM_CABLE PRIMARY KEY ("ID")
);

--管线 仓段 关联表
CREATE TABLE T_OAM_CABLE_SECTION(
  id              NUMBER    NOT NULL,
  cable_id    varchar2(100)    not null,
  section_id  number    not null,
   CONSTRAINT PK_T_OAM_CABLE_SECTION PRIMARY KEY ("ID")
);


--create sequence
create sequence OAM_CABLE_SECTION_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER OAM_CABLE_SECTION_TG
  BEFORE INSERT ON T_OAM_CABLE_SECTION
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OAM_CABLE_SECTION_SQ.nextval into :new.id from dual;
end OAM_CABLE_SECTION_TG;
/

--管线布置合同表
CREATE TABLE T_OAM_CABLE_CONTRACT(
  id                        varchar2(100)    NOT NULL,
  name                      varchar2(100),--合同名称
  customer_id               number    not null,--客户表外键
  pay_type                  number(1) not null,--付款方式 枚举
  contract_status           number(1) not null,--合同状态
  contract_start_time       date,
  contract_end_time         date,
  crt_time                  date,
   CONSTRAINT PK_T_OAM_CABLE_CONTRACT PRIMARY KEY ("ID")
);

---------------------------------------------------------------
---------------------------------------------------------------
-------------------------O-M-M---------------------------------
---------------------------------------------------------------
---------------------------------------------------------------
-- Create table T_OMM_INSTRUMENT 仪表工具表
create table T_OMM_INSTRUMENT
(
  id                         NUMBER not null,
  spare_id                   NUMBER,
  name                       varchar2(30) not null,
  model_id                  NUMBER not null,
  type_id                 NUMBER not null,
  VENDER_ID              NUMBER not null,
  USE_STATUS              NUMBER NOT NULL,
  status               NUMBER not null,
  in_time                  DATE
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OMM_INSTRUMENT add constraint OMM_INSTRUMENT_ID primary key (ID);
  
-- create
create sequence OMM_INSTRUMENT_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_INSTRUMENT_TG
  BEFORE INSERT ON T_OMM_INSTRUMENT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_INSTRUMENT_SQ.nextval into :new.id from dual;
end OMM_INSTRUMENT_TG;
/
alter trigger OMM_INSTRUMENT_TG enable;

-- Create table T_OMM_INSTRUMENT_RECORD 仪表工具记录表
create table T_OMM_INSTRUMENT_RECORD
(
  id                         NUMBER not null,
  instrument_id              NUMBER not null,
  staff_id                  NUMBER not null,
  return_id                  NUMBER,
  describe                VARCHAR2(200),
  remark                VARCHAR2(200),
  borrow_time                  DATE,
  return_time                  DATE
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OMM_INSTRUMENT_RECORD add constraint OMM_INSTRUMENT_RECORD_ID primary key (ID);
  
-- create
create sequence OMM_INSTRUMENT_RECORD_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_INSTRUMENT_RECORD_TG
  BEFORE INSERT ON T_OMM_INSTRUMENT_RECORD
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_INSTRUMENT_RECORD_SQ.nextval into :new.id from dual;
end OMM_INSTRUMENT_RECORD_TG;
/
alter trigger OMM_INSTRUMENT_RECORD_TG enable;

-- Create table T_OMM_SPARE 备品表
create table T_OMM_SPARE
(
  id                         NUMBER not null,
  name                       varchar2(30) not null,
  model_id                  NUMBER not null,
  type_id                 NUMBER not null,
  vender_id                 NUMBER not null,
  status                    NUMBER not null,
  in_time                  DATE
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OMM_SPARE add constraint OMM_SPARE_ID primary key (ID);
  
-- create
create sequence OMM_SPARE_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_SPARE_TG
  BEFORE INSERT ON T_OMM_SPARE
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_SPARE_SQ.nextval into :new.id from dual;
end OMM_SPARE_TG;
/
alter trigger OMM_SPARE_TG enable;

-- Create table T_OMM_SPARE_OUT 备品出库表
create table T_OMM_SPARE_OUT
(
  id                         NUMBER not null,
  staff_id                  NUMBER not null,
  user_id                 NUMBER not null,
  whither                 NUMBER,
  describe                VARCHAR2(100),
  out_time                  DATE
);

-- Create table T_OMM_DEFECT 
create table T_OMM_DEFECT
(
  id                         NUMBER not null,
  name                       varchar2(30) not null,
  tunnel_id                  NUMBER not null,
  defect_type                NUMBER not null,
  section_id                 NUMBER not null,
  object_id                  NUMBER,
  equipment_id               NUMBER,
  defect_level               NUMBER not null,
  description                VARCHAR2(100),
  defect_status              NUMBER not null,
  order_id                   VARCHAR2(20),
  create_time                DATE not null,
  edit_time                  DATE
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OMM_DEFECT add constraint OMM_DEFECT_ID primary key (ID);
  
-- create
create sequence OMM_DEFECT_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_DEFECT_TG
  BEFORE INSERT ON T_OMM_DEFECT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_DEFECT_SEQUENCE.nextval into :new.id from dual;
end OMM_DEFECT_TG;
/
alter trigger OMM_DEFECT_TG enable;

-- Create table T_OMM_MAINTENANCE_ORDER 
create table T_OMM_MAINTENANCE_ORDER
(
  id                 number not null,
  order_id           VARCHAR2(20),
  tunnel_id          NUMBER not null,
  defect_id          number,
  worker             number,
  maintenance_time   date,
  create_time        date,
  process_type       number,
  process_status     varchar2(40),
  start_time         date,
  end_time           date,
  maintenance_result varchar2(40),
  describe           varchar2(200),
  process_instance_id       number,
  is_finished               number
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OMM_MAINTENANCE_ORDER add constraint OMM_MAINTENANCE_ORDER_ID primary key (ID);

-- create
create sequence OMM_MAINTENANCE_ORDER_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_MAINTENANCE_ORDER_TG
  BEFORE INSERT ON T_OMM_MAINTENANCE_ORDER
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_MAINTENANCE_ORDER_SEQUENCE.nextval into :new.id from dual;
end OMM_MAINTENANCE_ORDER_TG;
/
alter trigger OMM_MAINTENANCE_ORDER_TG enable;

  
-- prompt Creating T_OMM_EQUIPMENT. 设备
create table T_OMM_EQUIPMENT
(
  id           NUMBER not null,
  name         VARCHAR2(30) not null,
  type         NUMBER not null,
  crt_time     DATE,
  service_life NUMBER,
  status       NUMBER,
  asset_no     VARCHAR2(30) not null,
  tunnel_id    NUMBER,
  vender_id    NUMBER,
  model_id     NUMBER,
  obj_id       NUMBER,
  img_url      VARCHAR2(200)
);
alter table T_OMM_EQUIPMENT add constraint OMM_EQUIPMENT_ID primary key (ID);
  
-- create
create sequence OMM_EQUIPMENT_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_EQUIPMENT_TG
  BEFORE INSERT ON T_OMM_EQUIPMENT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_EQUIPMENT_SEQUENCE.nextval into :new.id from dual;
end OMM_EQUIPMENT_TG;
/
alter trigger OMM_EQUIPMENT_TG enable;

-- prompt Creating T_OMM_EQUIPMENT_STATUS... 
create table T_OMM_EQUIPMENT_STATUS
(
  id   NUMBER not null,
  name VARCHAR2(20)
);
alter table T_OMM_EQUIPMENT_STATUS add constraint OMM_EQUIPMENT_STATUS_ID primary key (ID);

-- prompt Creating T_OMM_EQUIPMENT_TYPE...       
create table T_OMM_EQUIPMENT_TYPE
(
  id   NUMBER not null,
  name VARCHAR2(30) not null
);
alter table T_OMM_EQUIPMENT_TYPE add constraint OMM_EQUIPMENT_TYPE_ID primary key (ID);

-- create
create sequence OMM_EQUIPMENT_TYPE_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_EQUIPMENT_TYPE_TG
  BEFORE INSERT ON T_OMM_EQUIPMENT_TYPE
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_EQUIPMENT_TYPE_SEQUENCE.nextval into :new.id from dual;
end OMM_EQUIPMENT_TYPE_TG;
/
alter trigger OMM_EQUIPMENT_TYPE_TG enable;
  
-- prompt Creeate T_OMM_INSPECTION_PLAN        
create table T_OMM_INSPECTION_PLAN
(
  id              number not null,
  plan_id         varchar2(30) not null,
  name            VARCHAR2(30) not null,
  request_staff_id        number not null,
  approver_id            number not null,
  tunnel_id              number not null,
  group_id               number not null,
  inspect_time           date not null,
  remark                 varchar2(200),
  create_time            date not null,
  process_type           number not null,
  process_status         varchar2(20) not null,
  process_instance_id    varchar2(100),
  is_finished            number  
);
alter table T_OMM_INSPECTION_PLAN add constraint OMM_INSPECTION_PLAN_ID primary key (ID);

-- create
create sequence OMM_INSPECTION_PLAN_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_INSPECTION_PLAN_TG
  BEFORE INSERT ON T_OMM_INSPECTION_PLAN
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_INSPECTION_PLAN_SEQUENCE.nextval into :new.id from dual;
end OMM_INSPECTION_PLAN_TG;
/
alter trigger OMM_INSPECTION_PLAN_TG enable;

-- prompt Creeate T_OMM_INSPECTION_TASK        
create table T_OMM_INSPECTION_TASK
(
  id              number not null,
  plan_id         varchar2(30) not null,
  task_time       date not null,
  inspect_time    date,
  inspect_man_id  number not null,
  create_time     date not null,
  start_time      date,
  end_time        date,
  describe        varchar2(100),
  process_type           number not null,
  process_status         varchar2(20) not null,
  process_instance_id    varchar2(100),
  is_finished            number  
);
alter table T_OMM_INSPECTION_TASK add constraint OMM_INSPECTION_TASK_ID primary key (ID);
 
-- create
create sequence OMM_INSPECTION_TASK_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_INSPECTION_TASK_TG
  BEFORE INSERT ON T_OMM_INSPECTION_TASK
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_INSPECTION_TASK_SEQUENCE.nextval into :new.id from dual;
end OMM_INSPECTION_TASK_TG;
/
alter trigger OMM_INSPECTION_TASK_TG enable;


-- prompt Creeate T_OMM_INSPECTION_RECORD       
create table T_OMM_INSPECTION_RECORD
(
  id              number not null,
  task_id         number not null,
  section_id      number not null,
  create_time     date not null,
  record_time     date not null,
  content         varchar2(20) not null,
  defect_id       number
);
alter table T_OMM_INSPECTION_RECORD add constraint OMM_INSPECTION_RECORD_ID primary key (ID);
 
-- create
create sequence OMM_INSPECTION_RECORD_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_INSPECTION_RECORD_TG
  BEFORE INSERT ON T_OMM_INSPECTION_RECORD
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_INSPECTION_RECORD_SEQUENCE.nextval into :new.id from dual;
end OMM_INSPECTION_RECORD_TG;
/
alter trigger OMM_INSPECTION_RECORD_TG enable;


-- prompt Creeate T_OMM_INSPECTION_Group        
create table T_OMM_INSPECTION_GROUP
(
  id              number not null,
  name            varchar2(30) not null,
  leader_id       number,
  leader_name     varchar2(30)
);
alter table T_OMM_INSPECTION_GROUP add constraint OMM_INSPECTION_GROUP_ID primary key (ID);
 
-- create sequence
create sequence OMM_INSPECTION_GROUP_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger
CREATE OR REPLACE TRIGGER OMM_INSPECTION_GROUP_TG
  BEFORE INSERT ON T_OMM_INSPECTION_GROUP
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_INSPECTION_GROUP_SEQUENCE.nextval into :new.id from dual;
end OMM_INSPECTION_GROUP_TG;
/
alter trigger OMM_INSPECTION_GROUP_TG enable;


-- prompt Create T_OMM_INSPECTION_Group_User        
create table T_OMM_INSPECTION_GROUP_USER
(
  group_id              number not null,
  user_id               number not null
);

--create table T_OMM_EQUIPMENT_VENDER 表：设备供应商
CREATE TABLE T_OMM_EQUIPMENT_VENDER  (
   "ID"                 NUMBER                          NOT NULL,
   "NAME"               VARCHAR2(50 CHAR)               NOT NULL,
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_OMM_EQUIPMENT_VENDER PRIMARY KEY ("ID")
);


--create sequence 
create sequence OMM_EQUIPMENT_VENDER_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger 
CREATE OR REPLACE TRIGGER OMM_EQUIPMENT_VENDER_TG
  BEFORE INSERT ON T_OMM_EQUIPMENT_VENDER
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_EQUIPMENT_VENDER_SEQUENCE.nextval into :new.id from dual;
end OMM_EQUIPMENT_VENDER_TG;
/

--create table T_OMM_EQUIPMENT_MODEL 表：设备型号
CREATE TABLE T_OMM_EQUIPMENT_MODEL  (
   "ID"                 NUMBER                          NOT NULL,
   type_id              NUMBER                          NOT NULL,
   "NAME"               VARCHAR2(50 CHAR)               NOT NULL,
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_OMM_EQUIPMENT_MODEL PRIMARY KEY ("ID")
);


--create sequence 
create sequence OMM_EQUIPMENT_MODEL_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger 
CREATE OR REPLACE TRIGGER OMM_EQUIPMENT_MODEL_TG
  BEFORE INSERT ON T_OMM_EQUIPMENT_MODEL
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_EQUIPMENT_MODEL_SEQUENCE.nextval into :new.id from dual;
end OMM_EQUIPMENT_MODEL_TG;
/

--create table T_OMM_CHECKINGPOINT  表：巡检点
CREATE TABLE T_OMM_CHECKINGPOINT  (
   "ID"                 NUMBER                          NOT NULL,
   "NAME"               VARCHAR2(50 CHAR)               NOT NULL,
   "POSITION"           VARCHAR2(100 CHAR)              NOT NULL,
   "TUNNEL_ID"          NUMBER                          NOT NULL,
   "CRT_TIME"           DATE,
   CONSTRAINT PK_T_OMM_CHECKINGPOINT PRIMARY KEY ("ID")
);


--create sequence 
create sequence OMM_CHECKINGPOINT_SEQUENCE
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger 
CREATE OR REPLACE TRIGGER OMM_CHECKINGPOINT_TG
  BEFORE INSERT ON T_OMM_CHECKINGPOINT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_CHECKINGPOINT_SEQUENCE.nextval into :new.id from dual;
end OMM_CHECKINGPOINT_TG;
/
