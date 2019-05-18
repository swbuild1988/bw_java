-- 入廊记录表
create table T_OAM_REQ_RECORD
(
  ID         NUMBER NOT NULL,
  STAFF_ID  NUMBER NOT NULL,
  EQUIPMENT_ID   NUMBER NOT NULL,
  TIME          DATE,
  LONGITUDE         VARCHAR2(50),
  LATITUDE          VARCHAR2(50),
  HEIGHT    VARCHAR2(50)
);
alter table T_OAM_REQ_RECORD add constraint OAM_REQ_RECORD_ID primary key(ID);

-- create OAM_REQ_RECORD_SQ
create sequence OAM_REQ_RECORD_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger OAM_REQ_RECORD_TG
CREATE OR REPLACE TRIGGER OAM_REQ_RECORD_TG
  BEFORE INSERT ON T_OAM_REQ_RECORD
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OAM_REQ_RECORD_SQ.nextval into :new.id from dual;
end OAM_REQ_RECORD_TG;
/
alter trigger OAM_REQ_RECORD_TG enable;


--文件导出表
CREATE TABLE T_COMMON_EXPORT(
  id    number    not null,
  name    varchar2(100) not null,
  of_type   number(1)   not null,
  pdf_path    varchar2(200) not null,
  excel_path    varchar2(200) not null,
  type    number(1) not null,
  value   number    not null,
  crt_time        DATE, 
   CONSTRAINT PK_T_COMMON_EXPORT PRIMARY KEY ("ID")
);

create sequence COMMON_EXPORT_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

CREATE OR REPLACE TRIGGER COMMON_EXPORT_TG
  BEFORE INSERT ON T_COMMON_EXPORT
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_EXPORT_SQ.nextval into :new.id from dual;
end COMMON_EXPORT_TG;
/

update T_OMM_EQUIPMENT_VENDER set name = '上海波汇科技有限公司' where id = 1;
update T_OMM_EQUIPMENT_VENDER set name = '安徽波汇智能科技有限公司' where id = 2;
update T_OMM_EQUIPMENT_VENDER set name = '山西波汇信息技术有限公司' where id = 3;

-- 变电站表
create table T_MAM_SUBSTATION
(
  ID         NUMBER NOT NULL,
  TUNNEL_ID  NUMBER NOT NULL,
  INV_ID  NUMBER NOT NULL,
  INA_ID  NUMBER NOT NULL,
  OUTV_ID  NUMBER NOT NULL,
  OUTA_ID  NUMBER NOT NULL,
  POWER_ID  NUMBER NOT NULL,
  ELE_RADIUS   NUMBER,
  POWER_FACTOR   NUMBER,
  COMPENSATION_FACTOR  NUMBER,
  POSITION         VARCHAR2(50),
  TIME          DATE
);
alter table T_MAM_SUBSTATION add constraint MAM_SUBSTATION_ID primary key(ID);

-- create MAM_SUBSTATION_SQ
create sequence MAM_SUBSTATION_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger MAM_SUBSTATION_TG
CREATE OR REPLACE TRIGGER MAM_SUBSTATION_TG
  BEFORE INSERT ON T_MAM_SUBSTATION
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select MAM_SUBSTATION_SQ.nextval into :new.id from dual;
end MAM_SUBSTATION_TG;
/
alter trigger MAM_SUBSTATION_TG enable;

-- 预案-组表
create table T_EM_PLAN_GROUP
(
  ID         NUMBER NOT NULL,
  PLAN_ID  NUMBER NOT NULL,
  NAME			VARCHAR2(20),
  STAFF_ID	NUMBER,
  TIME          DATE
);
alter table T_EM_PLAN_GROUP add constraint EM_PLAN_GROUP_ID primary key(ID);

-- create EM_PLAN_GROUP_SQ
create sequence EM_PLAN_GROUP_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger EM_PLAN_GROUP_TG
CREATE OR REPLACE TRIGGER EM_PLAN_GROUP_TG
  BEFORE INSERT ON T_EM_PLAN_GROUP
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select EM_PLAN_GROUP_SQ.nextval into :new.id from dual;
end EM_PLAN_GROUP_TG;
/
alter trigger EM_PLAN_GROUP_TG enable;


-- 预案-成员表
create table T_EM_PLAN_MEMBER
(
  ID         NUMBER NOT NULL,
  GROUP_ID  NUMBER NOT NULL,
  STAFF_ID	NUMBER NOT NULL,
  TEL			VARCHAR2(20),
  TEL2			VARCHAR2(20),
  TIME          DATE
);
alter table T_EM_PLAN_MEMBER add constraint EM_PLAN_MEMBER_ID primary key(ID);

-- create EM_PLAN_MEMBER_SQ
create sequence EM_PLAN_MEMBER_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger EM_PLAN_MEMBER_TG
CREATE OR REPLACE TRIGGER EM_PLAN_MEMBER_TG
  BEFORE INSERT ON T_EM_PLAN_MEMBER
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select EM_PLAN_MEMBER_SQ.nextval into :new.id from dual;
end EM_PLAN_MEMBER_TG;
/
alter trigger EM_PLAN_MEMBER_TG enable;


-- 巡检计划步骤表
create table T_OMM_INSPECTION_STEP
(
  ID         NUMBER NOT NULL,
  TASK_ID  VARCHAR2(20) NOT NULL,
  NAME			VARCHAR2(100),
  IS_FINISHED    NUMBER
);
alter table T_OMM_INSPECTION_STEP add constraint OMM_INSPECTION_STEP_ID primary key(ID);

-- create OMM_INSPECTION_STEP_SQ
create sequence OMM_INSPECTION_STEP_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

-- create trigger OMM_INSPECTION_STEP_TG
CREATE OR REPLACE TRIGGER OMM_INSPECTION_STEP_TG
  BEFORE INSERT ON T_OMM_INSPECTION_STEP
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select OMM_INSPECTION_STEP_SQ.nextval into :new.id from dual;
end OMM_INSPECTION_STEP_TG;
/
alter trigger OMM_INSPECTION_STEP_TG enable;