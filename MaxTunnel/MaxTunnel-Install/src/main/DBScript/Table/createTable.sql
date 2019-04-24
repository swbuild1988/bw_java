-- ���ȼ�¼��
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


--�ļ�������
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

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
 values (1005, 'job1006', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'weekExport', '0 0 7 ? * MON', '�ܱ�����');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
 values (1006, 'job1007', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'monthExport', '0 0 7 1 * ?', '�±�����');
commit;
