--客户表数据
insert into T_OAM_CUSTOMER (ID, COMPANY_ID, CONTACT, TEL, CRT_TIME)
values (2, 1, '刘冬絮', '13722228880', to_date('26-06-2018 10:17:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CUSTOMER (ID, COMPANY_ID, CONTACT, TEL, CRT_TIME)
values (3, 2, '李理想', '13722228881', to_date('25-06-2018 10:17:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CUSTOMER (ID, COMPANY_ID, CONTACT, TEL, CRT_TIME)
values (4, 3, '高鑫', '13722228882', to_date('24-06-2018 10:17:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CUSTOMER (ID, COMPANY_ID, CONTACT, TEL, CRT_TIME)
values (5, 4, '顾亚茹', '13722228883', to_date('23-06-2018 10:17:47', 'dd-mm-yyyy hh24:mi:ss'));
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
values (1, 'd4b8a02ab7c94619a5ede9c3b259e898', 1027);

insert into T_OAM_CABLE_SECTION (ID, CABLE_ID, SECTION_ID)
values (2, '651816e6b6a043ad9a46cbf38e9a8f6a', 1007);

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

--重大事件数据
insert into T_OAM_EVENT (id, name, staff_id, range, event_level, longitude, latitude, start_time, end_time, crt_time)
values (1, '进博会', 3, 7, 2, '112.49405197240482', '37.70708540511645', to_date('10-11-2018', 'dd-mm-yyyy'), to_date('16-11-2018', 'dd-mm-yyyy'), to_date('08-11-2018', 'dd-mm-yyyy'));

insert into T_OAM_EVENT (id, name, staff_id, range, event_level, longitude, latitude, start_time, end_time, crt_time)
values (2, '奥运会', 2, 7, 3, '112.49405959102272', '37.70674696590566', to_date('11-11-2018', 'dd-mm-yyyy'), to_date('15-11-2018', 'dd-mm-yyyy'), to_date('08-11-2018', 'dd-mm-yyyy'));
commit;

--能耗Consume数据
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (1, 2, 5, 23, 36, 2, 1001, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (2, 2, 5, 23, 37, 2, 1002, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (3, 2, 5, 24, 36, 2, 1003, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (4, 2, 5, 24, 37, 2, 1004, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (5, 2, 5, 25, 36, 2, 1011, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (6, 2, 5, 25, 37, 2, 1012, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (7, 2, 5, 26, 36, 2, 1007, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (8, 2, 5, 26, 37, 2, 1008, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (9, 2, 5, 27, 36, 2, 1009, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (10, 2, 5, 27, 37, 2, 1010, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (11, 2, 5, 28, 36, 2, 1005, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (12, 2, 5, 28, 37, 2, 1006, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (13, 2, 6, 23, 36, 2, 1037, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (14, 2, 6, 23, 37, 2, 1038, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (15, 2, 6, 24, 36, 2, 1039, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (16, 2, 6, 24, 37, 2, 1040, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (17, 2, 6, 25, 36, 2, 1047, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (18, 2, 6, 25, 37, 2, 1048, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (19, 2, 6, 26, 36, 2, 1043, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (20, 2, 6, 26, 37, 2, 1044, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (21, 2, 6, 27, 36, 2, 1045, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (22, 2, 6, 27, 37, 2, 1046, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (23, 2, 6, 28, 36, 2, 1041, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (24, 2, 6, 28, 37, 2, 1042, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (25, 2, 7, 23, 36, 2, 1025, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (26, 2, 7, 23, 37, 2, 1026, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (27, 2, 7, 24, 36, 2, 1027, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (28, 2, 7, 24, 37, 2, 1028, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (29, 2, 7, 25, 36, 2, 1035, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (30, 2, 7, 25, 37, 2, 1036, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (31, 2, 7, 26, 36, 2, 1031, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (32, 2, 7, 26, 37, 2, 1032, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (33, 2, 7, 27, 36, 2, 1033, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (34, 2, 7, 27, 37, 2, 1034, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (35, 2, 7, 28, 36, 2, 1029, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (36, 2, 7, 28, 37, 2, 1030, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (37, 2, 8, 23, 36, 2, 1013, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (38, 2, 8, 23, 37, 2, 1014, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (39, 2, 8, 24, 36, 2, 1015, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (40, 2, 8, 24, 37, 2, 1016, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (41, 2, 8, 25, 36, 2, 1023, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (42, 2, 8, 25, 37, 2, 1024, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (43, 2, 8, 26, 36, 2, 1019, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (44, 2, 8, 26, 37, 2, 1020, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (45, 2, 8, 27, 36, 2, 1021, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (46, 2, 8, 27, 37, 2, 1022, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (47, 2, 8, 28, 36, 2, 1017, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (48, 2, 8, 28, 37, 2, 1018, null);
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (49, 2, 8, 28, 0, 2, null, '1017,1018');
insert into T_OAM_CONSUME (id, tunnel_id, store_id, area_id, object_type, energy_type, object_id, compute)
values (50, 2, 8, 0, 36, 2, null, '1013,1015,1017,1019,1021,1023');
commit;
