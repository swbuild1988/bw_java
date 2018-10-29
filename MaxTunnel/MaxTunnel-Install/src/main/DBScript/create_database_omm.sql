declare 
      num   number; 
begin 
    -- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_CHECKINGPOINT_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_CHECKINGPOINT_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_CHECKINGPOINT_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_CHECKINGPOINT_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_CHECKINGPOINT';
      if   num=1   then 
          execute immediate 'drop table T_OMM_CHECKINGPOINT'; 
      end   if;
	-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_EQUIPMENT_MODEL_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_EQUIPMENT_MODEL_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_EQUIPMENT_MODEL_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_EQUIPMENT_MODEL_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_EQUIPMENT_MODEL';
      if   num=1   then 
          execute immediate 'drop table T_OMM_EQUIPMENT_MODEL'; 
      end   if; 
	-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_EQUIPMENT_VENDER_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_EQUIPMENT_VENDER_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_EQUIPMENT_VENDER_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_EQUIPMENT_VENDER_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_EQUIPMENT_VENDER';
      if   num=1   then 
          execute immediate 'drop table T_OMM_EQUIPMENT_VENDER'; 
      end   if; 
-- -- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_DEFECT_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_DEFECT_SEQUENCE';   
      end if;     
-- -- prompt dropping trigger    
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_DEFECT_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_DEFECT_TG';   
      end if; 
-- -- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_EQUIPMENT_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_EQUIPMENT_SEQUENCE';   
      end if;     
-- -- prompt dropping trigger    
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_EQUIPMENT_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_EQUIPMENT_TG';   
      end if; 
-- -- prompt dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_DEFECT';
      if   num=1   then 
          execute immediate 'drop table T_OMM_DEFECT'; 
      end   if; 
-- -- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_MAINTENANCE_ORDER_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_MAINTENANCE_ORDER_SEQUENCE';   
      end if;     
-- -- prompt dropping trigger    
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_MAINTENANCE_ORDER_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_MAINTENANCE_ORDER_TG';   
      end if; 
-- -- prompt dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_MAINTENANCE_ORDER';
      if   num=1   then 
          execute immediate 'drop table T_OMM_MAINTENANCE_ORDER'; 
      end   if; 
-- -- prompt dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_EQUIPMENT';
      if   num=1   then 
          execute immediate 'drop table T_OMM_EQUIPMENT'; 
      end   if; 
-- -- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_EQUIPMENT_STATUS';
      if   num=1   then 
          execute immediate 'drop table T_OMM_EQUIPMENT_STATUS'; 
      end   if; 
-- -- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_EQUIPMENT_TYPE';
      if   num=1   then 
          execute immediate 'drop table T_OMM_EQUIPMENT_TYPE'; 
      end   if; 
-- -- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_INSPECTION_PLAN_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_INSPECTION_PLAN_SEQUENCE';   
      end if;     
-- -- prompt dropping trigger 
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_INSPECTION_PLAN_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_INSPECTION_PLAN_TG';   
      end if; 
-- -- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_INSPECTION_PLAN';
      if   num=1   then 
          execute immediate 'drop table T_OMM_INSPECTION_PLAN'; 
      end   if; 
-- -- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_INSPECTION_TASK_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_INSPECTION_TASK_SEQUENCE';   
      end if;     
-- -- prompt dropping trigger 
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_INSPECTION_TASK_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_INSPECTION_TASK_TG';   
      end if; 
-- -- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_INSPECTION_TASK';
      if   num=1   then 
          execute immediate 'drop table T_OMM_INSPECTION_TASK'; 
      end   if; 
-- -- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_INSPECTION_RECORD_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_INSPECTION_RECORD_SEQUENCE';   
      end if;     
-- -- prompt dropping trigger 
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_INSPECTION_RECORD_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_INSPECTION_RECORD_TG';   
      end if; 
-- -- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_INSPECTION_RECORD';
      if   num=1   then 
          execute immediate 'drop table T_OMM_INSPECTION_RECORD'; 
      end   if; 
-- -- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'OMM_INSPECTION_GROUP_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  OMM_INSPECTION_GROUP_SEQUENCE';   
      end if;     
-- -- prompt dropping trigger 
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'OMM_INSPECTION_GROUP_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  OMM_INSPECTION_GROUP_TG';   
      end if; 
-- -- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_INSPECTION_GROUP';
      if   num=1   then 
          execute immediate 'drop table T_OMM_INSPECTION_GROUP'; 
      end   if; 
-- -- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_INSPECTION_GROUP_USER';
      if   num=1   then 
          execute immediate 'drop table T_OMM_INSPECTION_GROUP_USER'; 
      end   if; 
end;
/

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
