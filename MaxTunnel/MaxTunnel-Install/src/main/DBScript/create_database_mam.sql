declare 
      num   number; 
begin 
-- -- -- prompt dropping sequence
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'MAM_ALARM_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  MAM_ALARM_SEQUENCE';   
      end if;     
-- -- -- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'MAM_ALARM_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  MAM_ALARM_TG';   
      end if; 
      
-- -- -- prompt dropping sequence
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'MAM_VIDEO_SERVER_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  MAM_VIDEO_SERVER_SEQUENCE';   
      end if;     
-- -- -- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'MAM_VIDEO_SERVER_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  MAM_VIDEO_SERVER_TG';   
      end if; 
      
-- -- -- prompt dropping sequence
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'MAM_VIDEO_SCENE_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  MAM_VIDEO_SCENE_SEQUENCE';   
      end if;     
-- -- -- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'MAM_VIDEO_SCENE_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER MAM_VIDEO_SCENE_TG';   
      end if; 
-- -- -- prompt dropping T_MAM_ALARM
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_ALARM';
      if   num=1   then 
          execute immediate 'drop table T_MAM_ALARM'; 
      end   if; 
-- -- -- prompt Dropping T_MAM_ALARM_TYPE
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_ALARM_TYPE';
      if   num=1   then 
          execute immediate 'drop table T_MAM_ALARM_TYPE'; 
      end   if; 
-- prompt dropping sequence
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'MAM_MEASVALUE_TABDICT_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  MAM_MEASVALUE_TABDICT_SEQUENCE';   
      end if;     
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'MAM_MEASVALUE_TABDICT_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  MAM_MEASVALUE_TABDICT_TG';   
      end if; 
-- prompt dropping T_MAM_MEASVALUE_TABDICT
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASVALUE_TABDICT';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASVALUE_TABDICT'; 
      end   if; 
-- prompt Dropping T_MAM_MEASVALUE_AI
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASVALUE_AI';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASVALUE_AI'; 
      end   if; 
-- prompt Dropping T_MAM_MEASVALUE_DI
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASVALUE_DI';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASVALUE_DI'; 
      end   if; 
-- prompt Dropping T_MAM_MEASVALUE_SI
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASVALUE_SI';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASVALUE_SI'; 
      end   if; 
-- prompt Dropping T_MAM_MEASVALUE_SO
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASVALUE_SO';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASVALUE_SO'; 
      end   if; 
-- prompt Dropping T_MAM_PERSON_LOCATOR
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_PERSON_LOCATOR';
      if   num=1   then 
          execute immediate 'drop table T_MAM_PERSON_LOCATOR'; 
      end   if; 
-- prompt Dropping T_MAM_MEASVALUE_DAS_SPECTRUM
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASVALUE_DASSPECTRUM';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASVALUE_DASSPECTRUM'; 
      end   if; 
-- prompt Dropping T_MAM_MEASVALUE_DISTRIBUTE
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASVALUE_DISTRIBUTE';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASVALUE_DISTRIBUTE'; 
      end   if; 
-- prompt Dropping T_MAM_MEASOBJ
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASOBJ';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASOBJ'; 
      end   if; 
-- prompt Dropping T_MAM_MEASOBJ_AI
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASOBJ_AI';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASOBJ_AI'; 
      end   if; 
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASOBJ_DI';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASOBJ_DI'; 
      end   if; 
      
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASOBJ_SI';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASOBJ_SI'; 
      end   if; 
-- prompt Dropping T_MAM_MEASOBJ_DISTRIBUTE
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASOBJ_DISTRIBUTE';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASOBJ_DISTRIBUTE'; 
      end   if; 
-- prompt Dropping T_MAM_MEASOBJ_SO
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASOBJ_SO';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASOBJ_SO'; 
      end   if; 
-- prompt Dropping T_MAM_VIDEO
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_VIDEO';
      if   num=1   then 
          execute immediate 'drop table T_MAM_VIDEO'; 
      end   if; 
-- prompt Dropping T_MAM_VIDEO_SCENE
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_VIDEO_SCENE';
      if   num=1   then 
          execute immediate 'drop table T_MAM_VIDEO_SCENE'; 
      end   if; 
-- prompt Dropping T_MAM_VIDEO_SERVER
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_VIDEO_SERVER';
      if   num=1   then 
          execute immediate 'drop table T_MAM_VIDEO_SERVER'; 
      end   if; 
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'MAM_MEASOBJ_REPORT_SQ'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  MAM_MEASOBJ_REPORT_SQ';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'MAM_MEASOBJ_REPORT_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  MAM_MEASOBJ_REPORT_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASOBJ_REPORT';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASOBJ_REPORT'; 
      end   if; 
      
      
-- -- -- prompt dropping sequence
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'MAM_VIDEO_PRESET_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  MAM_VIDEO_PRESET_SEQUENCE';   
      end if;     
-- -- -- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'MAM_VIDEO_PRESET_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  MAM_VIDEO_PRESET_TG';   
      end if; 
-- prompt Dropping T_MAM_VIDEO_SERVER
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_VIDEO_PRESET';
      if   num=1   then 
          execute immediate 'drop table T_MAM_VIDEO_PRESET'; 
      end   if; 
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'MAM_MEASOBJ_MAP_SQ'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  MAM_MEASOBJ_MAP_SQ';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'MAM_MEASOBJ_MAP_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  MAM_MEASOBJ_MAP_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MEASOBJ_MAP';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MEASOBJ_MAP'; 
      end   if; 
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'MAM_MAXVIEW_CONFIG_SEQUENCE'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  MAM_MAXVIEW_CONFIG_SEQUENCE';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'MAM_MAXVIEW_CONFIG_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  MAM_MAXVIEW_CONFIG_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_MAM_MAXVIEW_CONFIG';
      if   num=1   then 
          execute immediate 'drop table T_MAM_MAXVIEW_CONFIG'; 
      end   if; 
end;
/

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
  video_scene_id               number not null,
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
 
