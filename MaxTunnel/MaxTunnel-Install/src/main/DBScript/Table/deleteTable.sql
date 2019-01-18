
-- 邵森 太原修改的脚本

--tunnel
alter table T_COMMON_TUNNEL add status number(1);
update T_COMMON_TUNNEL set status = 1 where id in (1,6);
update T_COMMON_TUNNEL set status = 2 where id in (2,3,4,5);

--job
insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1004, 'job12', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'runDaysAutoAdded', '1 0 0 * * ?', '管廊运行时间定时自增');
commit;

--合同表
insert into T_OAM_CABLE_CONTRACT (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('16a91e49fd4b46b8bd1eff9280bd5925', '合同1', 2, 1, 1, to_date('29-12-2018 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-12-2019 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2019 15:33:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE_CONTRACT (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('02e2ac6c340b4b259ef00a376fa00421', '合同2', 2, 1, 1, to_date('29-12-2018 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-12-2019 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2019 15:42:05', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE_CONTRACT (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('71b9675fc93a40848da963175146fbfd', '合同3', 2, 1, 1, to_date('29-12-2018 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-12-2019 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2019 15:42:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE_CONTRACT (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('30329ed061d14f9cb895c056a6b8cc10', '合同3', 2, 1, 1, to_date('29-12-2018 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-12-2019 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2019 15:45:16', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE_CONTRACT (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('e9e673613ded447782d9f951e246fbe0', '综合仓部署管线合同', 2, 1, 1, to_date('29-12-2018 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-12-2019 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2019 15:47:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE_CONTRACT (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('aa46c3cf8d594f789a4d6a492c57aa9f', '综合仓部署管线合同2', 2, 1, 1, to_date('29-12-2018 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-12-2019 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2019 15:48:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE_CONTRACT (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('d0939f07009947ccbd6938d24e492c57', '设备合同', 2, 1, 1, to_date('29-12-2018 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-12-2019 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2019 15:46:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE_CONTRACT (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('11b3a7122d1b4dffa7cd6a85d8debf13', '设备合同2', 2, 1, 1, to_date('29-12-2018 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-12-2019 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2019 15:46:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE_CONTRACT (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('2f92f7e0c2f54a03b7d3ae0c79bb7c75', '合同3', 2, 1, 1, to_date('29-12-2018 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-12-2019 08:00:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-01-2019 15:44:33', 'dd-mm-yyyy hh24:mi:ss'));

commit;
--管线表
insert into T_OAM_CABLE (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('d4b8a02ab7c94619a5ede9c3b259e898', '16a91e49fd4b46b8bd1eff9280bd5925', '电力光纤', 1000.56, 1, null, to_date('10-01-2019 15:33:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('651816e6b6a043ad9a46cbf38e9a8f6a', '02e2ac6c340b4b259ef00a376fa00421', '污水光纤', 10500, 1, null, to_date('10-01-2019 15:42:06', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('49f7e9aa22b64079990ef4a7009077af', '71b9675fc93a40848da963175146fbfd', '污水光纤', 10500, 1, null, to_date('10-01-2019 15:42:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('e4323904cdba4ce3b7124f4c3744b4f1', '30329ed061d14f9cb895c056a6b8cc10', '水利管线', 20505, 1, null, to_date('10-01-2019 15:45:16', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('ce7e8aac3b6f47f58c21cb21777f0e89', 'e9e673613ded447782d9f951e246fbe0', '综合管线', 30000, 1, null, to_date('10-01-2019 15:47:58', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('b9da7e2e42f54ac09c603ba907bbc61b', 'aa46c3cf8d594f789a4d6a492c57aa9f', '综合管线', 2000, 1, null, to_date('10-01-2019 15:48:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('95699756553742dab20a758899388889', 'd0939f07009947ccbd6938d24e492c57', '设备管线', 30000, 1, null, to_date('10-01-2019 15:46:42', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('d112e847eab042bc977d8c6c9aa66f49', '11b3a7122d1b4dffa7cd6a85d8debf13', '设备管线', 2300, 1, null, to_date('10-01-2019 15:46:53', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CABLE (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('4c37affc02ee461caf71e783323bf016', '2f92f7e0c2f54a03b7d3ae0c79bb7c75', '燃气管线', 10600, 1, null, to_date('10-01-2019 15:44:33', 'dd-mm-yyyy hh24:mi:ss'));
commit;

--cable，section中间表
insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (17, 'd4b8a02ab7c94619a5ede9c3b259e898', 1027);

insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (18, '651816e6b6a043ad9a46cbf38e9a8f6a', 1007);

insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (3, '49f7e9aa22b64079990ef4a7009077af', 1007);

insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (5, 'e4323904cdba4ce3b7124f4c3744b4f1', 1007);

insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (8, 'ce7e8aac3b6f47f58c21cb21777f0e89', 1048);

insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (9, 'b9da7e2e42f54ac09c603ba907bbc61b', 1048);

insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (6, '95699756553742dab20a758899388889', 1087);

insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (7, 'd112e847eab042bc977d8c6c9aa66f49', 1087);

insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (4, '4c37affc02ee461caf71e783323bf016', 1067);
commit;


--tunnel_run
declare 
      num   number; 
begin
      num := 0;
      select count(1) into num from user_sequences where sequence_name = 'COMMON_TUNNEL_RUN_SQ'; 
      if num > 0 then   
         execute immediate 'DROP SEQUENCE  COMMON_TUNNEL_RUN_SQ';   
      end if;     
      num := 0;
      select count(1) into num from user_triggers where trigger_name = 'COMMON_TUNNEL_RUN_TG'; 
      if num > 0 then   
         execute immediate 'DROP TRIGGER  COMMON_TUNNEL_RUN_TG';   
      end if;
      num := 0;
      select count(1) into num from user_tables where TABLE_NAME = 'T_COMMON_TUNNEL_RUN';
      if   num=1   then 
          execute immediate 'drop table T_COMMON_TUNNEL_RUN'; 
      end   if; 
end;
/

CREATE TABLE T_COMMON_TUNNEL_RUN(
  id              NUMBER    NOT NULL,
  run_days  number    not null,
  safe_dyas number    not null,
   CONSTRAINT PK_T_COMMON_TUNNEL_RUN PRIMARY KEY ("ID")
);


create sequence COMMON_TUNNEL_RUN_SQ
start with 1
increment by 1
nomaxvalue
nocycle
cache 20;

CREATE OR REPLACE TRIGGER COMMON_TUNNEL_RUN_TG
  BEFORE INSERT ON T_COMMON_TUNNEL_RUN
  FOR EACH ROW
  WHEN (new.id is null)
begin
  select COMMON_TUNNEL_RUN_SQ.nextval into :new.id from dual;
end COMMON_TUNNEL_RUN_TG;
/
