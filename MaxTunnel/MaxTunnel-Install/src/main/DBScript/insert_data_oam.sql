--插入能耗表数据
insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (25, 2, 31, 57.1, 1.5, to_date('06-11-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (27, 2, 33, 69.1, 1.5, to_date('16-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (29, 3, 31, 55.1, 1.5, to_date('22-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (34, 4, 32, 74.1, 1.5, to_date('15-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (35, 4, 33, 44.1, 1.5, to_date('26-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (36, 4, 34, 16.1, 1.5, to_date('03-11-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (37, 5, 31, 71.1, 1.5, to_date('31-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (38, 5, 32, 64.1, 1.5, to_date('23-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (39, 5, 33, 98.1, 1.5, to_date('09-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (40, 5, 34, 19.1, 1.5, to_date('28-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (21, 1, 31, 61.1, 1.5, to_date('09-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (22, 1, 32, 16.1, 1.5, to_date('21-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (23, 1, 33, 61.1, 1.5, to_date('11-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (24, 1, 34, 62.1, 1.5, to_date('21-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (26, 2, 32, 21.1, 1.5, to_date('23-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (28, 2, 34, 44.1, 1.5, to_date('07-11-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (30, 3, 32, 19.1, 1.5, to_date('19-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (31, 3, 33, 3.1, 1.5, to_date('01-11-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (32, 3, 34, 66.1, 1.5, to_date('19-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_OAM_ENERGY (ID, TUNNEL_ID, OBJECT_TYPE, VALUE, UNIT_PRICE, CRT_TIME)
values (33, 4, 31, 37.1, 1.5, to_date('21-10-2018 13:17:28', 'dd-mm-yyyy hh24:mi:ss'));
commit;


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

insert into T_OAM_EVENT (id, name, staff_id, range, event_level, longitude, latitude, start_time, end_time, crt_time)
values (1, '进博会', 3, 7, 2, '112.49405197240482', '37.70708540511645', to_date('10-11-2018', 'dd-mm-yyyy'), to_date('16-11-2018', 'dd-mm-yyyy'), to_date('08-11-2018', 'dd-mm-yyyy'));

insert into T_OAM_EVENT (id, name, staff_id, range, event_level, longitude, latitude, start_time, end_time, crt_time)
values (2, '奥运会', 2, 7, 3, '112.49405959102272', '37.70674696590566', to_date('11-11-2018', 'dd-mm-yyyy'), to_date('15-11-2018', 'dd-mm-yyyy'), to_date('08-11-2018', 'dd-mm-yyyy'));
commit;




