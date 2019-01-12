--管廊数据
insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (1, '古城大街', 5000, '02', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'), 1);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (2, '实验路', 1000, '03', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (3, '经三路', 1000, '04', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (4, '经二路', 1000, '05', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (5, '纬三路', 5000, '06', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (6, '监控中心', 500, '01', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 1);

commit;

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1001, '电力舱', '02', 1001, 1, 1001, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1002, '电力舱进风口', '12', 1001, 1, 1006, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1003, '电力舱排风口', '22', 1001, 1, 1010, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1004, '非燃气舱投料井', '32', 1004, 1, 1014, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1005, '其他', '41', 1005, 1, 1015, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1006, '燃气舱', '04', 1006, 1, 1003, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1007, '燃气舱进风口', '14', 1006, 1, 1008, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1008, '燃气舱排风口', '24', 1006, 1, 1012, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1009, '燃气舱投料井', '31', 1006, 1, 1013, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1010, '设备间', '05', 1010, 1, 1004, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1011, '污水舱', '01', 1011, 1, 1000, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1012, '污水舱进风口', '11', 1011, 1, 1005, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1013, '污水舱排风口', '21', 1011, 1, 1009, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1014, '综合舱', '03', 1014, 1, 1002, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1015, '综合舱进风口', '13', 1014, 1, 1007, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1016, '综合舱排风口', '23', 1014, 1, 1011, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1017, '其他', '41', 1017, 6, 1015, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

commit;

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1001, '10区', '10', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1002, '11区', '11', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1003, '12区', '12', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1004, '13区', '13', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1005, '14区', '14', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1006, '15区', '15', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1007, '16区', '16', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1008, '17区', '17', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1009, '18区', '18', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1010, '19区', '19', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1011, '20区', '20', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1012, '3区', '03', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1013, '4区', '04', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1014, '5区', '05', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1015, '6区', '06', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1016, '7区', '07', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1017, '8区', '08', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1018, '9区', '09', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1019, '21区', '21', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1020, '22区', '22', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1021, '1区', '01', null, 6, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

commit;

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1200, '3区-电力舱排风口', 1, 1003, 1012, 10, 1020, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1201, '4区-电力舱排风口', 1, 1003, 1013, 10, 1021, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1202, '5区-电力舱排风口', 1, 1003, 1014, 10, 1022, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1203, '6区-电力舱排风口', 1, 1003, 1015, 10, 1023, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1204, '7区-电力舱排风口', 1, 1003, 1016, 10, 1024, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1205, '8区-电力舱排风口', 1, 1003, 1017, 10, 1025, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1206, '9区-电力舱排风口', 1, 1003, 1018, 10, 1026, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1207, '10区-电力舱排风口', 1, 1003, 1001, 10, 1027, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1208, '11区-电力舱排风口', 1, 1003, 1002, 10, 1028, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1209, '12区-电力舱排风口', 1, 1003, 1003, 10, 1029, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1210, '13区-电力舱排风口', 1, 1003, 1004, 10, 1030, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1211, '14区-电力舱排风口', 1, 1003, 1005, 10, 1031, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1212, '15区-电力舱排风口', 1, 1003, 1006, 10, 1032, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1213, '16区-电力舱排风口', 1, 1003, 1007, 10, 1033, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1214, '17区-电力舱排风口', 1, 1003, 1008, 10, 1034, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1215, '18区-电力舱排风口', 1, 1003, 1009, 10, 1035, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1216, '19区-电力舱排风口', 1, 1003, 1010, 10, 1036, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1217, '20区-电力舱排风口', 1, 1003, 1011, 10, 1037, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1218, '21区-电力舱排风口', 1, 1003, 1019, 10, 1038, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1220, '3区-综合舱排风口', 1, 1016, 1012, 10, 1040, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1221, '4区-综合舱排风口', 1, 1016, 1013, 10, 1041, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1011, '14区-污水舱', 1, 1011, 1005, 10, 1011, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1013, '16区-污水舱', 1, 1011, 1007, 10, 1013, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1014, '17区-污水舱', 1, 1011, 1008, 10, 1014, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1017, '20区-污水舱', 1, 1011, 1011, 10, 1017, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1019, '22区-污水舱', 1, 1011, 1020, 10, 1019, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1020, '3区-电力舱', 1, 1001, 1012, 10, 1020, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1025, '8区-电力舱', 1, 1001, 1017, 10, 1025, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1026, '9区-电力舱', 1, 1001, 1018, 10, 1026, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1041, '4区-综合舱', 1, 1014, 1013, 10, 1041, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1060, '3区-燃气舱', 1, 1006, 1012, 10, 1060, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1061, '4区-燃气舱', 1, 1006, 1013, 10, 1061, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1062, '5区-燃气舱', 1, 1006, 1014, 10, 1062, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1063, '6区-燃气舱', 1, 1006, 1015, 10, 1063, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1091, '14区-设备间', 1, 1010, 1005, 10, 1091, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1093, '16区-设备间', 1, 1010, 1007, 10, 1093, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1094, '17区-设备间', 1, 1010, 1008, 10, 1094, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1095, '18区-设备间', 1, 1010, 1009, 10, 1095, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1109, '12区-污水舱进风口', 1, 1012, 1003, 10, 1009, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1110, '13区-污水舱进风口', 1, 1012, 1004, 10, 1010, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1111, '14区-污水舱进风口', 1, 1012, 1005, 10, 1011, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1112, '15区-污水舱进风口', 1, 1012, 1006, 10, 1012, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1113, '16区-污水舱进风口', 1, 1012, 1007, 10, 1013, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1114, '17区-污水舱进风口', 1, 1012, 1008, 10, 1014, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1115, '18区-污水舱进风口', 1, 1012, 1009, 10, 1015, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1116, '19区-污水舱进风口', 1, 1012, 1010, 10, 1016, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1117, '20区-污水舱进风口', 1, 1012, 1011, 10, 1017, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1118, '21区-污水舱进风口', 1, 1012, 1019, 10, 1018, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1119, '22区-污水舱进风口', 1, 1012, 1020, 10, 1019, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1120, '3区-电力舱进风口', 1, 1002, 1012, 10, 1020, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1121, '4区-电力舱进风口', 1, 1002, 1013, 10, 1021, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1122, '5区-电力舱进风口', 1, 1002, 1014, 10, 1022, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1123, '6区-电力舱进风口', 1, 1002, 1015, 10, 1023, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1124, '7区-电力舱进风口', 1, 1002, 1016, 10, 1024, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1140, '3区-综合舱进风口', 1, 1015, 1012, 10, 1040, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1141, '4区-综合舱进风口', 1, 1015, 1013, 10, 1041, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1142, '5区-综合舱进风口', 1, 1015, 1014, 10, 1042, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1143, '6区-综合舱进风口', 1, 1015, 1015, 10, 1043, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1144, '7区-综合舱进风口', 1, 1015, 1016, 10, 1044, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1145, '8区-综合舱进风口', 1, 1015, 1017, 10, 1045, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1146, '9区-综合舱进风口', 1, 1015, 1018, 10, 1046, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1147, '10区-综合舱进风口', 1, 1015, 1001, 10, 1047, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1148, '11区-综合舱进风口', 1, 1015, 1002, 10, 1048, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1149, '12区-综合舱进风口', 1, 1015, 1003, 10, 1049, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1150, '13区-综合舱进风口', 1, 1015, 1004, 10, 1050, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1151, '14区-综合舱进风口', 1, 1015, 1005, 10, 1051, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1152, '15区-综合舱进风口', 1, 1015, 1006, 10, 1052, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1153, '16区-综合舱进风口', 1, 1015, 1007, 10, 1053, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1154, '17区-综合舱进风口', 1, 1015, 1008, 10, 1054, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1155, '18区-综合舱进风口', 1, 1015, 1009, 10, 1055, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1156, '19区-综合舱进风口', 1, 1015, 1010, 10, 1056, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1157, '20区-综合舱进风口', 1, 1015, 1011, 10, 1057, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1158, '21区-综合舱进风口', 1, 1015, 1019, 10, 1058, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1159, '22区-综合舱进风口', 1, 1015, 1020, 10, 1059, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1160, '3区-燃气舱进风口', 1, 1007, 1012, 10, 1060, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1161, '4区-燃气舱进风口', 1, 1007, 1013, 10, 1061, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1162, '5区-燃气舱进风口', 1, 1007, 1014, 10, 1062, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1163, '6区-燃气舱进风口', 1, 1007, 1015, 10, 1063, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1164, '7区-燃气舱进风口', 1, 1007, 1016, 10, 1064, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1165, '8区-燃气舱进风口', 1, 1007, 1017, 10, 1065, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1166, '9区-燃气舱进风口', 1, 1007, 1018, 10, 1066, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1167, '10区-燃气舱进风口', 1, 1007, 1001, 10, 1067, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1219, '22区-电力舱排风口', 1, 1003, 1020, 10, 1039, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1222, '5区-综合舱排风口', 1, 1016, 1014, 10, 1042, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1223, '6区-综合舱排风口', 1, 1016, 1015, 10, 1043, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1224, '7区-综合舱排风口', 1, 1016, 1016, 10, 1044, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1225, '8区-综合舱排风口', 1, 1016, 1017, 10, 1045, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1226, '9区-综合舱排风口', 1, 1016, 1018, 10, 1046, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1227, '10区-综合舱排风口', 1, 1016, 1001, 10, 1047, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1228, '11区-综合舱排风口', 1, 1016, 1002, 10, 1048, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1229, '12区-综合舱排风口', 1, 1016, 1003, 10, 1049, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1230, '13区-综合舱排风口', 1, 1016, 1004, 10, 1050, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1231, '14区-综合舱排风口', 1, 1016, 1005, 10, 1051, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1232, '15区-综合舱排风口', 1, 1016, 1006, 10, 1052, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1233, '16区-综合舱排风口', 1, 1016, 1007, 10, 1053, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1234, '17区-综合舱排风口', 1, 1016, 1008, 10, 1054, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1235, '18区-综合舱排风口', 1, 1016, 1009, 10, 1055, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1236, '19区-综合舱排风口', 1, 1016, 1010, 10, 1056, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1237, '20区-综合舱排风口', 1, 1016, 1011, 10, 1057, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1238, '21区-综合舱排风口', 1, 1016, 1019, 10, 1058, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1239, '22区-综合舱排风口', 1, 1016, 1020, 10, 1059, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1240, '3区-燃气舱排风口', 1, 1008, 1012, 10, 1060, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1241, '4区-燃气舱排风口', 1, 1008, 1013, 10, 1061, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1242, '5区-燃气舱排风口', 1, 1008, 1014, 10, 1062, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1243, '6区-燃气舱排风口', 1, 1008, 1015, 10, 1063, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1244, '7区-燃气舱排风口', 1, 1008, 1016, 10, 1064, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1245, '8区-燃气舱排风口', 1, 1008, 1017, 10, 1065, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1246, '9区-燃气舱排风口', 1, 1008, 1018, 10, 1066, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1247, '10区-燃气舱排风口', 1, 1008, 1001, 10, 1067, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1248, '11区-燃气舱排风口', 1, 1008, 1002, 10, 1068, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1249, '12区-燃气舱排风口', 1, 1008, 1003, 10, 1069, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1250, '13区-燃气舱排风口', 1, 1008, 1004, 10, 1070, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1251, '14区-燃气舱排风口', 1, 1008, 1005, 10, 1071, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1252, '15区-燃气舱排风口', 1, 1008, 1006, 10, 1072, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1253, '16区-燃气舱排风口', 1, 1008, 1007, 10, 1073, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1254, '17区-燃气舱排风口', 1, 1008, 1008, 10, 1074, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1255, '18区-燃气舱排风口', 1, 1008, 1009, 10, 1075, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1256, '19区-燃气舱排风口', 1, 1008, 1010, 10, 1076, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1257, '20区-燃气舱排风口', 1, 1008, 1011, 10, 1077, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1258, '21区-燃气舱排风口', 1, 1008, 1019, 10, 1078, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1259, '22区-燃气舱排风口', 1, 1008, 1020, 10, 1079, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1260, '3区-燃气舱投料井', 1, 1009, 1012, 10, 1060, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1261, '4区-燃气舱投料井', 1, 1009, 1013, 10, 1061, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1262, '6区-燃气舱投料井', 1, 1009, 1015, 10, 1063, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1263, '7区-燃气舱投料井', 1, 1009, 1016, 10, 1064, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1264, '8区-燃气舱投料井', 1, 1009, 1017, 10, 1065, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1265, '9区-燃气舱投料井', 1, 1009, 1018, 10, 1066, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1266, '10区-燃气舱投料井', 1, 1009, 1001, 10, 1067, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1267, '11区-燃气舱投料井', 1, 1009, 1002, 10, 1068, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1268, '12区-燃气舱投料井', 1, 1009, 1003, 10, 1069, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1269, '13区-燃气舱投料井', 1, 1009, 1004, 10, 1070, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1270, '14区-燃气舱投料井', 1, 1009, 1005, 10, 1071, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1271, '16区-燃气舱投料井', 1, 1009, 1007, 10, 1073, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1272, '17区-燃气舱投料井', 1, 1009, 1008, 10, 1074, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1273, '18区-燃气舱投料井', 1, 1009, 1009, 10, 1075, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1274, '19区-燃气舱投料井', 1, 1009, 1010, 10, 1076, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1275, '20区-燃气舱投料井', 1, 1009, 1011, 10, 1077, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1276, '21区-燃气舱投料井', 1, 1009, 1019, 10, 1078, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1277, '22区-燃气舱投料井', 1, 1009, 1020, 10, 1079, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1278, '3区-非燃气舱投料井', 1, 1004, 1012, 10, 1278, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1279, '6区-非燃气舱投料井', 1, 1004, 1015, 10, 1279, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1280, '7区-非燃气舱投料井', 1, 1004, 1016, 10, 1280, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1281, '9区-非燃气舱投料井', 1, 1004, 1018, 10, 1281, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1282, '11区-非燃气舱投料井', 1, 1004, 1002, 10, 1282, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1283, '13区-非燃气舱投料井', 1, 1004, 1004, 10, 1283, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1284, '16区-非燃气舱投料井', 1, 1004, 1007, 10, 1284, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1285, '17区-非燃气舱投料井', 1, 1004, 1008, 10, 1285, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1286, '19区-非燃气舱投料井', 1, 1004, 1010, 10, 1286, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1287, '21区-非燃气舱投料井', 1, 1004, 1019, 10, 1287, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1288, '1区-其他', 6, 1017, 1021, 10, 1288, null, null, null, to_date('23-12-2018 09:53:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1016, '19区-污水舱', 1, 1011, 1010, 10, 1016, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1000, '3区-污水舱', 1, 1011, 1012, 10, 1000, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1001, '4区-污水舱', 1, 1011, 1013, 10, 1001, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1002, '5区-污水舱', 1, 1011, 1014, 10, 1002, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1003, '6区-污水舱', 1, 1011, 1015, 10, 1003, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1004, '7区-污水舱', 1, 1011, 1016, 10, 1004, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1005, '8区-污水舱', 1, 1011, 1017, 10, 1005, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1006, '9区-污水舱', 1, 1011, 1018, 10, 1006, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1007, '10区-污水舱', 1, 1011, 1001, 10, 1007, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1008, '11区-污水舱', 1, 1011, 1002, 10, 1008, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1009, '12区-污水舱', 1, 1011, 1003, 10, 1009, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1010, '13区-污水舱', 1, 1011, 1004, 10, 1010, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1012, '15区-污水舱', 1, 1011, 1006, 10, 1012, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1015, '18区-污水舱', 1, 1011, 1009, 10, 1015, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1018, '21区-污水舱', 1, 1011, 1019, 10, 1018, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1021, '4区-电力舱', 1, 1001, 1013, 10, 1021, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1022, '5区-电力舱', 1, 1001, 1014, 10, 1022, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1023, '6区-电力舱', 1, 1001, 1015, 10, 1023, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1024, '7区-电力舱', 1, 1001, 1016, 10, 1024, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1027, '10区-电力舱', 1, 1001, 1001, 10, 1027, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1028, '11区-电力舱', 1, 1001, 1002, 10, 1028, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1029, '12区-电力舱', 1, 1001, 1003, 10, 1029, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1030, '13区-电力舱', 1, 1001, 1004, 10, 1030, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1031, '14区-电力舱', 1, 1001, 1005, 10, 1031, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1032, '15区-电力舱', 1, 1001, 1006, 10, 1032, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1033, '16区-电力舱', 1, 1001, 1007, 10, 1033, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1034, '17区-电力舱', 1, 1001, 1008, 10, 1034, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1035, '18区-电力舱', 1, 1001, 1009, 10, 1035, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1036, '19区-电力舱', 1, 1001, 1010, 10, 1036, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1037, '20区-电力舱', 1, 1001, 1011, 10, 1037, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1038, '21区-电力舱', 1, 1001, 1019, 10, 1038, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1039, '22区-电力舱', 1, 1001, 1020, 10, 1039, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1040, '3区-综合舱', 1, 1014, 1012, 10, 1040, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1042, '5区-综合舱', 1, 1014, 1014, 10, 1042, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1043, '6区-综合舱', 1, 1014, 1015, 10, 1043, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1044, '7区-综合舱', 1, 1014, 1016, 10, 1044, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1045, '8区-综合舱', 1, 1014, 1017, 10, 1045, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1046, '9区-综合舱', 1, 1014, 1018, 10, 1046, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1047, '10区-综合舱', 1, 1014, 1001, 10, 1047, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1048, '11区-综合舱', 1, 1014, 1002, 10, 1048, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1049, '12区-综合舱', 1, 1014, 1003, 10, 1049, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1050, '13区-综合舱', 1, 1014, 1004, 10, 1050, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1051, '14区-综合舱', 1, 1014, 1005, 10, 1051, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1052, '15区-综合舱', 1, 1014, 1006, 10, 1052, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1053, '16区-综合舱', 1, 1014, 1007, 10, 1053, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1054, '17区-综合舱', 1, 1014, 1008, 10, 1054, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1055, '18区-综合舱', 1, 1014, 1009, 10, 1055, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1056, '19区-综合舱', 1, 1014, 1010, 10, 1056, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1057, '20区-综合舱', 1, 1014, 1011, 10, 1057, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1058, '21区-综合舱', 1, 1014, 1019, 10, 1058, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1059, '22区-综合舱', 1, 1014, 1020, 10, 1059, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1064, '7区-燃气舱', 1, 1006, 1016, 10, 1064, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1065, '8区-燃气舱', 1, 1006, 1017, 10, 1065, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1066, '9区-燃气舱', 1, 1006, 1018, 10, 1066, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1067, '10区-燃气舱', 1, 1006, 1001, 10, 1067, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1068, '11区-燃气舱', 1, 1006, 1002, 10, 1068, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1069, '12区-燃气舱', 1, 1006, 1003, 10, 1069, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1070, '13区-燃气舱', 1, 1006, 1004, 10, 1070, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1071, '14区-燃气舱', 1, 1006, 1005, 10, 1071, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1072, '15区-燃气舱', 1, 1006, 1006, 10, 1072, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1073, '16区-燃气舱', 1, 1006, 1007, 10, 1073, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1074, '17区-燃气舱', 1, 1006, 1008, 10, 1074, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1075, '18区-燃气舱', 1, 1006, 1009, 10, 1075, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1076, '19区-燃气舱', 1, 1006, 1010, 10, 1076, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1077, '20区-燃气舱', 1, 1006, 1011, 10, 1077, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1078, '21区-燃气舱', 1, 1006, 1019, 10, 1078, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1079, '22区-燃气舱', 1, 1006, 1020, 10, 1079, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1080, '3区-设备间', 1, 1010, 1012, 10, 1080, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1081, '4区-设备间', 1, 1010, 1013, 10, 1081, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1082, '5区-设备间', 1, 1010, 1014, 10, 1082, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1083, '6区-设备间', 1, 1010, 1015, 10, 1083, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1084, '7区-设备间', 1, 1010, 1016, 10, 1084, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1085, '8区-设备间', 1, 1010, 1017, 10, 1085, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1086, '9区-设备间', 1, 1010, 1018, 10, 1086, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1087, '10区-设备间', 1, 1010, 1001, 10, 1087, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1088, '11区-设备间', 1, 1010, 1002, 10, 1088, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1089, '12区-设备间', 1, 1010, 1003, 10, 1089, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1090, '13区-设备间', 1, 1010, 1004, 10, 1090, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1092, '15区-设备间', 1, 1010, 1006, 10, 1092, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1096, '19区-设备间', 1, 1010, 1010, 10, 1096, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1097, '20区-设备间', 1, 1010, 1011, 10, 1097, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1098, '21区-设备间', 1, 1010, 1019, 10, 1098, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1099, '22区-设备间', 1, 1010, 1020, 10, 1099, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1100, '3区-污水舱进风口', 1, 1012, 1012, 10, 1000, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1101, '4区-污水舱进风口', 1, 1012, 1013, 10, 1001, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1102, '5区-污水舱进风口', 1, 1012, 1014, 10, 1002, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1103, '6区-污水舱进风口', 1, 1012, 1015, 10, 1003, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1104, '7区-污水舱进风口', 1, 1012, 1016, 10, 1004, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1105, '8区-污水舱进风口', 1, 1012, 1017, 10, 1005, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1106, '9区-污水舱进风口', 1, 1012, 1018, 10, 1006, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1107, '10区-污水舱进风口', 1, 1012, 1001, 10, 1007, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1108, '11区-污水舱进风口', 1, 1012, 1002, 10, 1008, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1125, '8区-电力舱进风口', 1, 1002, 1017, 10, 1025, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1126, '9区-电力舱进风口', 1, 1002, 1018, 10, 1026, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1127, '10区-电力舱进风口', 1, 1002, 1001, 10, 1027, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1128, '11区-电力舱进风口', 1, 1002, 1002, 10, 1028, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1129, '12区-电力舱进风口', 1, 1002, 1003, 10, 1029, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1130, '13区-电力舱进风口', 1, 1002, 1004, 10, 1030, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1131, '14区-电力舱进风口', 1, 1002, 1005, 10, 1031, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1132, '15区-电力舱进风口', 1, 1002, 1006, 10, 1032, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1133, '16区-电力舱进风口', 1, 1002, 1007, 10, 1033, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1134, '17区-电力舱进风口', 1, 1002, 1008, 10, 1034, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1135, '18区-电力舱进风口', 1, 1002, 1009, 10, 1035, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1136, '19区-电力舱进风口', 1, 1002, 1010, 10, 1036, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1137, '20区-电力舱进风口', 1, 1002, 1011, 10, 1037, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1138, '21区-电力舱进风口', 1, 1002, 1019, 10, 1038, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1139, '22区-电力舱进风口', 1, 1002, 1020, 10, 1039, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1168, '11区-燃气舱进风口', 1, 1007, 1002, 10, 1068, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1169, '12区-燃气舱进风口', 1, 1007, 1003, 10, 1069, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1170, '13区-燃气舱进风口', 1, 1007, 1004, 10, 1070, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1171, '14区-燃气舱进风口', 1, 1007, 1005, 10, 1071, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1172, '15区-燃气舱进风口', 1, 1007, 1006, 10, 1072, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1173, '16区-燃气舱进风口', 1, 1007, 1007, 10, 1073, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1174, '17区-燃气舱进风口', 1, 1007, 1008, 10, 1074, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1175, '18区-燃气舱进风口', 1, 1007, 1009, 10, 1075, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1176, '19区-燃气舱进风口', 1, 1007, 1010, 10, 1076, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1177, '20区-燃气舱进风口', 1, 1007, 1011, 10, 1077, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1178, '21区-燃气舱进风口', 1, 1007, 1019, 10, 1078, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1179, '22区-燃气舱进风口', 1, 1007, 1020, 10, 1079, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1180, '3区-污水舱排风口', 1, 1013, 1012, 10, 1000, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1181, '4区-污水舱排风口', 1, 1013, 1013, 10, 1001, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1182, '5区-污水舱排风口', 1, 1013, 1014, 10, 1002, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1183, '6区-污水舱排风口', 1, 1013, 1015, 10, 1003, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1184, '7区-污水舱排风口', 1, 1013, 1016, 10, 1004, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1185, '8区-污水舱排风口', 1, 1013, 1017, 10, 1005, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1186, '9区-污水舱排风口', 1, 1013, 1018, 10, 1006, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1187, '10区-污水舱排风口', 1, 1013, 1001, 10, 1007, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1188, '11区-污水舱排风口', 1, 1013, 1002, 10, 1008, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1189, '12区-污水舱排风口', 1, 1013, 1003, 10, 1009, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1190, '13区-污水舱排风口', 1, 1013, 1004, 10, 1010, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1191, '14区-污水舱排风口', 1, 1013, 1005, 10, 1011, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1192, '15区-污水舱排风口', 1, 1013, 1006, 10, 1012, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1193, '16区-污水舱排风口', 1, 1013, 1007, 10, 1013, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1194, '17区-污水舱排风口', 1, 1013, 1008, 10, 1014, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1195, '18区-污水舱排风口', 1, 1013, 1009, 10, 1015, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1196, '19区-污水舱排风口', 1, 1013, 1010, 10, 1016, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1197, '20区-污水舱排风口', 1, 1013, 1011, 10, 1017, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1198, '21区-污水舱排风口', 1, 1013, 1019, 10, 1018, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1199, '22区-污水舱排风口', 1, 1013, 1020, 10, 1019, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

commit;


--user
insert into T_SECURITY_USER (ID, NAME, PASSWORD, CRT_TIME)
values (1000, 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', to_date('20-06-2018 10:47:02', 'dd-mm-yyyy hh24:mi:ss'));



--员工表
insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID, CRT_TIME)
values (1, '刘冬絮', 1, 1, to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID, CRT_TIME)
values (2, '李萌', 2, 2,to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID, CRT_TIME)
values (3, '王帅', 3, 3,to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID,CRT_TIME)
values (4, '赵晓静', 4, 4,to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID, CRT_TIME)
values (5, '王超群', 1, 3,to_date('26-06-2018', 'dd-mm-yyyy'));
commit;


--部门表
insert into T_COMMON_DEPT (ID, NAME, CRT_TIME)
values (1, '产品部', to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_DEPT (ID, NAME, CRT_TIME)
values (2, '研发部', to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_DEPT (ID, NAME, CRT_TIME)
values (3, '运营部', to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_DEPT (ID, NAME, CRT_TIME)
values (4, '销售部', to_date('26-06-2018', 'dd-mm-yyyy'));
commit;



--公司表
insert into t_common_company (ID, NAME, CRT_TIME)
values (1, '波汇科技', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_company (ID, NAME, CRT_TIME)
values (2, '中国移动', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_company (ID, NAME, CRT_TIME)
values (3, '中国石化', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_company (ID, NAME, CRT_TIME)
values (4, '中国电信', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_company (ID, NAME, CRT_TIME)
values (5, '中铁十二局', to_date('22-06-2018', 'dd-mm-yyyy'));
commit;

--公司部门中间表
insert into T_COMMON_company_dept (ID, COMPANY_ID, DEPT_ID)
values (1, 1, 1);

insert into T_COMMON_company_dept (ID, COMPANY_ID, DEPT_ID)
values (2, 1, 2);

insert into T_COMMON_company_dept (ID, COMPANY_ID, DEPT_ID)
values (3, 1, 3);

insert into T_COMMON_company_dept (ID, COMPANY_ID, DEPT_ID)
values (4, 1, 4);
commit;

--职位表
insert into t_common_position (ID, NAME, CRT_TIME)
values (1, '总经理', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_position (ID, NAME, CRT_TIME)
values (2, '产品总监', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_position (ID, NAME, CRT_TIME)
values (3, '主管', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_position (ID, NAME, CRT_TIME)
values (4, '普通员工', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_position (ID, NAME, CRT_TIME)
values (5, '技术经理', to_date('18-06-2018', 'dd-mm-yyyy'));
commit;

--schedule_job表
insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (2, 'job2', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjHour', '0 1 0/1 * * ?', '统计上个小时最大最小平均值');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (3, 'job3', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjDay', '0 0 9 * * ?', '统计昨天最大最小平均值');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (4, 'job4', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjWeek', '0 0 9 ? * MON', '统计上周最大最小平均值');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (5, 'job5', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjMonth', '0 0 9 1 * ?', '统计上月最大最小平均值');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (6, 'job6', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjYear', '0 0 9 1 1 ? *', '统计上年最大最小平均值');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1, 'job1', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'saveDataScheduleBatch', '0 0/30 * * * ?', '监测对象数据定时保存');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (7, 'job7', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'deleteDataSchedule', '0 0 9 * * ?', '监测对象数据定时删除');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1000, 'job8', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'saveToConsumeData', '0 0 1 * * ?', '定时任务统计每天的能耗并保存到ConsumeData表中');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1001, 'job9', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'heartBeat', '0/5 * * * * ?', '登录maxview终端之后，持续发送心跳 ');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1002, 'job10', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'sendTestData', '0/15 * * * * ?', '模拟maxview发送数据');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1003, 'job11', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'sendTestAlarm', '0/30 * * * * ?', '模拟maxview发送告警');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1004, 'job12', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'runDaysAutoAdded', '1 0 0 * * ?', '管廊运行时间定时自增');

commit;
