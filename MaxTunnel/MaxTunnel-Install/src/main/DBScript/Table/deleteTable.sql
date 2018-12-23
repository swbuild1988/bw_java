declare 
      num   number; 
begin
--²âÊÔ±í:T_TESTS
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
      select count(1) into num from user_tables where TABLE_NAME = 'T_TESTS';
      if   num=1   then 
          execute immediate 'drop table T_TESTS'; 
      end   if; 
end;
/