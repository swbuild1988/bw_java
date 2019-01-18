declare 
      num   number; 
begin
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
      select count(1) into num from user_tables where TABLE_NAME = 'T_OMM_EQUIPMENT';
      if   num=1   then 
          execute immediate 'drop table T_OMM_EQUIPMENT'; 
      end   if; 
end;
/

----------------------------------------------------------------
---------------------------TABLE--------------------------------
----------------------------------------------------------------
----------------------------------------------------------------

-- prompt Creating T_OMM_EQUIPMENT. …Ë±∏
create table T_OMM_EQUIPMENT
(
  id           NUMBER not null,
  name         VARCHAR2(30) not null,
  type         NUMBER not null,
  crt_time     DATE,
  run_time     date,
  alarm_no     NUMBER not null,
  status       NUMBER,
  asset_no     VARCHAR2(30),
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
