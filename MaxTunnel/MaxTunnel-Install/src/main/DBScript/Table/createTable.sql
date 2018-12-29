declare 
      num   number; 
begin
--≤‚ ‘±Ì:T_TEST
-- prompt dropping sequence 
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'TEST_SQ'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  TEST_SQ';   
      end if;
-- prompt dropping trigger      
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'TEST_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  TEST_TG';   
      end if;
-- prompt Dropping 
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_TEST';
      if   num=1   then 
          execute immediate 'drop table T_TEST'; 
      end   if; 
end;
/

----------------------------------------------------------------
---------------------------TABLE--------------------------------
----------------------------------------------------------------
----------------------------------------------------------------

--≤‚ ‘±Ì
CREATE TABLE T_TEST(
  id              NUMBER          NOT NULL,
  name            varchar2(100)   not null, 
  card            number(18),
  CONSTRAINT PK_T_TEST PRIMARY KEY ("ID")
);


--create sequence
create sequence TEST_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;
-- create trigger
CREATE OR REPLACE TRIGGER TEST_TG
  BEFORE INSERT ON T_TEST
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select TEST_SQ.nextval into :new.id from dual;
end TEST_TG;
/
