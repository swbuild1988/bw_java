--客户表数据
insert into T_OAM_CUSTOMER (ID, COMPANY_ID, CONTACT, TEL, CRT_TIME)
values (2, 1, '联系人0', '13722228880', to_date('26-06-2018 10:17:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CUSTOMER (ID, COMPANY_ID, CONTACT, TEL, CRT_TIME)
values (3, 2, '联系人1', '13722228881', to_date('25-06-2018 10:17:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CUSTOMER (ID, COMPANY_ID, CONTACT, TEL, CRT_TIME)
values (4, 3, '联系人2', '13722228882', to_date('24-06-2018 10:17:47', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_CUSTOMER (ID, COMPANY_ID, CONTACT, TEL, CRT_TIME)
values (5, 4, '联系人3', '13722228883', to_date('23-06-2018 10:17:47', 'dd-mm-yyyy hh24:mi:ss'));
commit;


--合同表
insert into t_oam_cable_contract (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('c1001', '光纤部署合同', 3, 1, 1, to_date('29-07-2018 15:28:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-07-2018 15:28:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-07-2018 09:54:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into t_oam_cable_contract (ID, NAME, CUSTOMER_ID, PAY_TYPE, CONTRACT_STATUS, CONTRACT_START_TIME, CONTRACT_END_TIME, CRT_TIME)
values ('c1002', '高压线部署合同', 2, 2, 1, to_date('29-07-2018 15:28:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('29-07-2018 15:28:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-07-2018 09:55:52', 'dd-mm-yyyy hh24:mi:ss'));
commit;
--管线表
insert into t_oam_cable (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('4e5896a791b14c82a5f6cfe507710a51', 'c1001', '光纤', 100, 1, '此字段可以忽略', to_date('30-07-2018 09:54:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into t_oam_cable (ID, CONTRACT_ID, CABLE_NAME, CABLE_LENGTH, CABLE_STATUS, CABLE_LOCATION, CRT_TIME)
values ('d6d3790e5d664929b266f5a83941db4b', 'c1002', '高压线缆', 8888, 1, '此字段可以忽略', to_date('30-07-2018 09:55:52', 'dd-mm-yyyy hh24:mi:ss'));
commit;

--cable，section中间表
insert into t_oam_cable_section (ID, CABLE_ID, SECTION_ID)
values (10, '4e5896a791b14c82a5f6cfe507710a51', 1);

insert into t_oam_cable_section (ID, CABLE_ID, SECTION_ID)
values (11, '4e5896a791b14c82a5f6cfe507710a51', 5);

insert into t_oam_cable_section (ID, CABLE_ID, SECTION_ID)
values (12, '4e5896a791b14c82a5f6cfe507710a51', 6);

insert into t_oam_cable_section (ID, CABLE_ID, SECTION_ID)
values (13, 'd6d3790e5d664929b266f5a83941db4b', 1);

insert into t_oam_cable_section (ID, CABLE_ID, SECTION_ID)
values (14, 'd6d3790e5d664929b266f5a83941db4b', 2);

insert into t_oam_cable_section (ID, CABLE_ID, SECTION_ID)
values (15, 'd6d3790e5d664929b266f5a83941db4b', 4);
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
