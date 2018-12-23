--管廊数据
insert into T_COMMON_TUNNEL (ID, NAME, sn, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, maxview_config_id,CRT_TIME)
values (1, '古城大街', '02', 5000, 1, 5, 1, null, 1,to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_TUNNEL (ID, NAME, sn, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, maxview_config_id,CRT_TIME)
values (2, '实验路',  '03',1000, 1, 5, 1, null, 1,to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_TUNNEL (ID, NAME, sn, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, maxview_config_id,CRT_TIME)
values (3, '经三路',  '04',1000, 1, 5, 1, null, 1,to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_TUNNEL (ID, NAME, sn, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, maxview_config_id,CRT_TIME)
values (4, '经二路',  '05',1000, 1, 5, 1, null, 1,to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_TUNNEL (ID, NAME, sn, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, maxview_config_id,CRT_TIME)
values (5, '纬三路',  '06',5000, 1, 5, 1, null, 1,to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_TUNNEL (ID, NAME, sn, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, maxview_config_id,CRT_TIME)
values (6, '监控中心',  '01', 5000, 1, 5, 1, null, 1,to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

commit;


insert into T_COMMON_STORE_TYPE ( NAME, SN, PARENT, CRT_TIME)
values ('污水舱', '01','01', to_date('28-11-2018 09:45:40', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '电力舱', '02', '02', to_date('28-11-2018 09:45:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '综合舱', '03', '03', to_date('28-11-2018 09:46:09', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '燃气舱', '04', '04', to_date('28-11-2018 09:46:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '设备间', '05', '05', to_date('28-11-2018 09:46:29', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '污水舱进风口', '11', '01', to_date('28-11-2018 09:46:48', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '电力舱进风口', '12', '02', to_date('28-11-2018 09:47:14', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '综合舱进风口', '13', '03', to_date('28-11-2018 09:47:32', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '燃气舱进风口', '14', '04', to_date('28-11-2018 09:47:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '污水舱排风口', '21', '01', to_date('28-11-2018 09:48:04', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '电力舱排风口', '22', '02', to_date('28-11-2018 09:48:14', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '综合舱排风口', '23', '03', to_date('28-11-2018 09:48:33', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '燃气舱排风口', '24', '04', to_date('28-11-2018 09:48:46', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '燃气舱投料井', '31', '04', to_date('28-11-2018 09:49:18', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '非燃气舱投料井', '32', '32', to_date('28-11-2018 09:49:38', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (NAME, SN, PARENT, CRT_TIME)
values ( '其他', '41', '41', to_date('28-11-2018 09:49:48', 'dd-mm-yyyy hh24:mi:ss'));

commit;

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '10区', '10', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '11区', '11', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '12区', '12', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '13区', '13', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '14区', '14', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '15区', '15', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '16区', '16', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '17区', '17', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '18区', '18', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '19区', '19', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '20区', '20', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '3区', '03', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '4区', '04', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '5区', '05', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '6区', '06', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '7区', '07', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '8区', '08', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '9区', '09', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '21区', '21', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '22区', '22', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values ( '1区', '01', null, 6, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

commit;

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1001, '电力舱', '02', 1, 1001, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1001, '电力舱进风口', '12', 1, 1006, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1001, '电力舱排风口', '22', 1, 1010, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1004, '非燃气舱投料井', '32', 1, 1014, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1005, '其他', '41', 1, 1015, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1006, '燃气舱', '04', 1, 1003, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1006, '燃气舱进风口', '14', 1, 1008, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1006, '燃气舱排风口', '24', 1, 1012, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1006, '燃气舱投料井', '31', 1, 1013, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1010, '设备间', '05', 1, 1004, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1011, '污水舱', '01', 1, 1000, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1011, '污水舱进风口', '11', 1, 1005, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1011, '污水舱排风口', '21', 1, 1009, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1014, '综合舱', '03', 1, 1002, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1014, '综合舱进风口', '13', 1, 1007, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1014, '综合舱排风口', '23', 1, 1011, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (PARENT_ID, NAME, SN, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values ( 1017, '其他', '41', 6, 1015, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));
commit;


insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1050, '6区-电力舱排风口', 1, 1003, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1051, '7区-电力舱排风口', 1, 1003, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1052, '8区-电力舱排风口', 1, 1003, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1053, '9区-电力舱排风口', 1, 1003, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1054, '10区-非燃气舱投料井', 1, 1004, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1055, '11区-非燃气舱投料井', 1, 1004, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1056, '12区-非燃气舱投料井', 1, 1004, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1057, '13区-非燃气舱投料井', 1, 1004, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1058, '14区-非燃气舱投料井', 1, 1004, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1059, '15区-非燃气舱投料井', 1, 1004, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1060, '16区-非燃气舱投料井', 1, 1004, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1061, '17区-非燃气舱投料井', 1, 1004, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1062, '18区-非燃气舱投料井', 1, 1004, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1063, '19区-非燃气舱投料井', 1, 1004, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1064, '20区-非燃气舱投料井', 1, 1004, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1065, '3区-非燃气舱投料井', 1, 1004, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1066, '4区-非燃气舱投料井', 1, 1004, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1067, '5区-非燃气舱投料井', 1, 1004, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1068, '6区-非燃气舱投料井', 1, 1004, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1069, '7区-非燃气舱投料井', 1, 1004, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1070, '8区-非燃气舱投料井', 1, 1004, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1071, '9区-非燃气舱投料井', 1, 1004, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1072, '10区-其他', 1, 1005, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1073, '11区-其他', 1, 1005, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1074, '12区-其他', 1, 1005, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1075, '13区-其他', 1, 1005, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1076, '14区-其他', 1, 1005, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1077, '15区-其他', 1, 1005, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1078, '16区-其他', 1, 1005, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1079, '17区-其他', 1, 1005, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1080, '18区-其他', 1, 1005, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1081, '19区-其他', 1, 1005, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1082, '20区-其他', 1, 1005, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1083, '3区-其他', 1, 1005, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1084, '4区-其他', 1, 1005, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1085, '5区-其他', 1, 1005, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1086, '6区-其他', 1, 1005, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1087, '7区-其他', 1, 1005, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1088, '8区-其他', 1, 1005, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1089, '9区-其他', 1, 1005, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1090, '10区-燃气舱', 1, 1006, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1091, '11区-燃气舱', 1, 1006, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1092, '12区-燃气舱', 1, 1006, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1093, '13区-燃气舱', 1, 1006, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1094, '14区-燃气舱', 1, 1006, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1095, '15区-燃气舱', 1, 1006, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1096, '16区-燃气舱', 1, 1006, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1097, '17区-燃气舱', 1, 1006, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1098, '18区-燃气舱', 1, 1006, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1099, '19区-燃气舱', 1, 1006, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1100, '20区-燃气舱', 1, 1006, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1101, '3区-燃气舱', 1, 1006, 1012, 10, null,'112.494079,37.707195,6.130', '112.494079,37.706120,6.130', to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1102, '4区-燃气舱', 1, 1006, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1103, '5区-燃气舱', 1, 1006, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1104, '6区-燃气舱', 1, 1006, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1105, '7区-燃气舱', 1, 1006, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1106, '8区-燃气舱', 1, 1006, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1107, '9区-燃气舱', 1, 1006, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1108, '10区-燃气舱进风口', 1, 1007, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1109, '11区-燃气舱进风口', 1, 1007, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1110, '12区-燃气舱进风口', 1, 1007, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1111, '13区-燃气舱进风口', 1, 1007, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1112, '14区-燃气舱进风口', 1, 1007, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1113, '15区-燃气舱进风口', 1, 1007, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1114, '16区-燃气舱进风口', 1, 1007, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1115, '17区-燃气舱进风口', 1, 1007, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1116, '18区-燃气舱进风口', 1, 1007, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1117, '19区-燃气舱进风口', 1, 1007, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1118, '20区-燃气舱进风口', 1, 1007, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1119, '3区-燃气舱进风口', 1, 1007, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1120, '4区-燃气舱进风口', 1, 1007, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1121, '5区-燃气舱进风口', 1, 1007, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1122, '6区-燃气舱进风口', 1, 1007, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1123, '7区-燃气舱进风口', 1, 1007, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1124, '8区-燃气舱进风口', 1, 1007, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1125, '9区-燃气舱进风口', 1, 1007, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1126, '10区-燃气舱排风口', 1, 1008, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1127, '11区-燃气舱排风口', 1, 1008, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1128, '12区-燃气舱排风口', 1, 1008, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1129, '13区-燃气舱排风口', 1, 1008, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1130, '14区-燃气舱排风口', 1, 1008, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1131, '15区-燃气舱排风口', 1, 1008, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1132, '16区-燃气舱排风口', 1, 1008, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1133, '17区-燃气舱排风口', 1, 1008, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1134, '18区-燃气舱排风口', 1, 1008, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1135, '19区-燃气舱排风口', 1, 1008, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1136, '20区-燃气舱排风口', 1, 1008, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1137, '3区-燃气舱排风口', 1, 1008, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1138, '4区-燃气舱排风口', 1, 1008, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1139, '5区-燃气舱排风口', 1, 1008, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1140, '6区-燃气舱排风口', 1, 1008, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1141, '7区-燃气舱排风口', 1, 1008, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1142, '8区-燃气舱排风口', 1, 1008, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1179, '9区-设备间', 1, 1010, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1204, '16区-污水仓进风口', 1, 1012, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1205, '17区-污水仓进风口', 1, 1012, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1206, '18区-污水仓进风口', 1, 1012, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1207, '19区-污水仓进风口', 1, 1012, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1208, '20区-污水仓进风口', 1, 1012, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1209, '3区-污水仓进风口', 1, 1012, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1210, '4区-污水仓进风口', 1, 1012, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1211, '5区-污水仓进风口', 1, 1012, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1212, '6区-污水仓进风口', 1, 1012, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1213, '7区-污水仓进风口', 1, 1012, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1214, '8区-污水仓进风口', 1, 1012, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1215, '9区-污水仓进风口', 1, 1012, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1216, '10区-污水仓排风口', 1, 1013, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1217, '11区-污水仓排风口', 1, 1013, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1218, '12区-污水仓排风口', 1, 1013, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1219, '13区-污水仓排风口', 1, 1013, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1220, '14区-污水仓排风口', 1, 1013, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1221, '15区-污水仓排风口', 1, 1013, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1222, '16区-污水仓排风口', 1, 1013, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1223, '17区-污水仓排风口', 1, 1013, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1224, '18区-污水仓排风口', 1, 1013, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1225, '19区-污水仓排风口', 1, 1013, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1226, '20区-污水仓排风口', 1, 1013, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1248, '6区-综合仓', 1, 1014, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1249, '7区-综合仓', 1, 1014, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1250, '8区-综合仓', 1, 1014, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1251, '9区-综合仓', 1, 1014, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1252, '10区-综合仓进风口', 1, 1015, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1253, '11区-综合仓进风口', 1, 1015, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1254, '12区-综合仓进风口', 1, 1015, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1255, '13区-综合仓进风口', 1, 1015, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1256, '14区-综合仓进风口', 1, 1015, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1257, '15区-综合仓进风口', 1, 1015, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1258, '16区-综合仓进风口', 1, 1015, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1259, '17区-综合仓进风口', 1, 1015, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1260, '18区-综合仓进风口', 1, 1015, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1261, '19区-综合仓进风口', 1, 1015, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1262, '20区-综合仓进风口', 1, 1015, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1263, '3区-综合仓进风口', 1, 1015, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1264, '4区-综合仓进风口', 1, 1015, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1265, '5区-综合仓进风口', 1, 1015, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1266, '6区-综合仓进风口', 1, 1015, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1267, '7区-综合仓进风口', 1, 1015, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1268, '8区-综合仓进风口', 1, 1015, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1269, '9区-综合仓进风口', 1, 1015, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1270, '10区-综合仓排风口', 1, 1016, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1271, '11区-综合仓排风口', 1, 1016, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1273, '13区-综合仓排风口', 1, 1016, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1274, '14区-综合仓排风口', 1, 1016, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1275, '15区-综合仓排风口', 1, 1016, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1276, '16区-综合仓排风口', 1, 1016, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1277, '17区-综合仓排风口', 1, 1016, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1000, '10区-电力舱', 1, 1001, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1001, '11区-电力舱', 1, 1001, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1002, '12区-电力舱', 1, 1001, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1003, '13区-电力舱', 1, 1001, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1004, '14区-电力舱', 1, 1001, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1005, '15区-电力舱', 1, 1001, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1006, '16区-电力舱', 1, 1001, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1007, '17区-电力舱', 1, 1001, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1008, '18区-电力舱', 1, 1001, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1009, '19区-电力舱', 1, 1001, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1010, '20区-电力舱', 1, 1001, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1011, '3区-电力舱', 1, 1001, 1012, 10, null, '112.494028,37.707195,6.130', '112.494028,37.706120,6.130', to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1012, '4区-电力舱', 1, 1001, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1013, '5区-电力舱', 1, 1001, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1014, '6区-电力舱', 1, 1001, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1015, '7区-电力舱', 1, 1001, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1016, '8区-电力舱', 1, 1001, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1017, '9区-电力舱', 1, 1001, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1018, '10区-电力舱进风口', 1, 1002, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1019, '11区-电力舱进风口', 1, 1002, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1020, '12区-电力舱进风口', 1, 1002, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1021, '13区-电力舱进风口', 1, 1002, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1022, '14区-电力舱进风口', 1, 1002, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1023, '15区-电力舱进风口', 1, 1002, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1024, '16区-电力舱进风口', 1, 1002, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1025, '17区-电力舱进风口', 1, 1002, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1026, '18区-电力舱进风口', 1, 1002, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1027, '19区-电力舱进风口', 1, 1002, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1028, '20区-电力舱进风口', 1, 1002, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1029, '3区-电力舱进风口', 1, 1002, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1030, '4区-电力舱进风口', 1, 1002, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1031, '5区-电力舱进风口', 1, 1002, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1032, '6区-电力舱进风口', 1, 1002, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1033, '7区-电力舱进风口', 1, 1002, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1034, '8区-电力舱进风口', 1, 1002, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1035, '9区-电力舱进风口', 1, 1002, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1036, '10区-电力舱排风口', 1, 1003, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1037, '11区-电力舱排风口', 1, 1003, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1038, '12区-电力舱排风口', 1, 1003, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1039, '13区-电力舱排风口', 1, 1003, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1040, '14区-电力舱排风口', 1, 1003, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1041, '15区-电力舱排风口', 1, 1003, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1042, '16区-电力舱排风口', 1, 1003, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1043, '17区-电力舱排风口', 1, 1003, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1044, '18区-电力舱排风口', 1, 1003, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1045, '19区-电力舱排风口', 1, 1003, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1046, '20区-电力舱排风口', 1, 1003, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1047, '3区-电力舱排风口', 1, 1003, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1048, '4区-电力舱排风口', 1, 1003, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1049, '5区-电力舱排风口', 1, 1003, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1143, '9区-燃气舱排风口', 1, 1008, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1144, '10区-燃气舱投料井', 1, 1009, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1145, '11区-燃气舱投料井', 1, 1009, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1146, '12区-燃气舱投料井', 1, 1009, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1147, '13区-燃气舱投料井', 1, 1009, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1148, '14区-燃气舱投料井', 1, 1009, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1149, '15区-燃气舱投料井', 1, 1009, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1150, '16区-燃气舱投料井', 1, 1009, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1151, '17区-燃气舱投料井', 1, 1009, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1152, '18区-燃气舱投料井', 1, 1009, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1153, '19区-燃气舱投料井', 1, 1009, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1154, '20区-燃气舱投料井', 1, 1009, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1155, '3区-燃气舱投料井', 1, 1009, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1156, '4区-燃气舱投料井', 1, 1009, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1157, '5区-燃气舱投料井', 1, 1009, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1158, '6区-燃气舱投料井', 1, 1009, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1159, '7区-燃气舱投料井', 1, 1009, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1160, '8区-燃气舱投料井', 1, 1009, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1161, '9区-燃气舱投料井', 1, 1009, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1162, '10区-设备间', 1, 1010, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1163, '11区-设备间', 1, 1010, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1164, '12区-设备间', 1, 1010, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1165, '13区-设备间', 1, 1010, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1166, '14区-设备间', 1, 1010, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1167, '15区-设备间', 1, 1010, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1168, '16区-设备间', 1, 1010, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1169, '17区-设备间', 1, 1010, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1170, '18区-设备间', 1, 1010, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1171, '19区-设备间', 1, 1010, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1172, '20区-设备间', 1, 1010, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1173, '3区-设备间', 1, 1010, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1174, '4区-设备间', 1, 1010, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1175, '5区-设备间', 1, 1010, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1176, '6区-设备间', 1, 1010, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1177, '7区-设备间', 1, 1010, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1178, '8区-设备间', 1, 1010, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1180, '10区-污水仓', 1, 1011, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1181, '11区-污水仓', 1, 1011, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1182, '12区-污水仓', 1, 1011, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1183, '13区-污水仓', 1, 1011, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1184, '14区-污水仓', 1, 1011, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1185, '15区-污水仓', 1, 1011, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1186, '16区-污水仓', 1, 1011, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1187, '17区-污水仓', 1, 1011, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1188, '18区-污水仓', 1, 1011, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1189, '19区-污水仓', 1, 1011, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1190, '20区-污水仓', 1, 1011, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1191, '3区-污水仓', 1, 1011, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1192, '4区-污水仓', 1, 1011, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1193, '5区-污水仓', 1, 1011, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1194, '6区-污水仓', 1, 1011, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1195, '7区-污水仓', 1, 1011, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1196, '8区-污水仓', 1, 1011, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1197, '9区-污水仓', 1, 1011, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1198, '10区-污水仓进风口', 1, 1012, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1199, '11区-污水仓进风口', 1, 1012, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1200, '12区-污水仓进风口', 1, 1012, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1201, '13区-污水仓进风口', 1, 1012, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1202, '14区-污水仓进风口', 1, 1012, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1203, '15区-污水仓进风口', 1, 1012, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1227, '3区-污水仓排风口', 1, 1013, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1228, '4区-污水仓排风口', 1, 1013, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1229, '5区-污水仓排风口', 1, 1013, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1230, '6区-污水仓排风口', 1, 1013, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1231, '7区-污水仓排风口', 1, 1013, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1232, '8区-污水仓排风口', 1, 1013, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1233, '9区-污水仓排风口', 1, 1013, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1234, '10区-综合仓', 1, 1014, 1001, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1235, '11区-综合仓', 1, 1014, 1002, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1236, '12区-综合仓', 1, 1014, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1237, '13区-综合仓', 1, 1014, 1004, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1238, '14区-综合仓', 1, 1014, 1005, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1239, '15区-综合仓', 1, 1014, 1006, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1240, '16区-综合仓', 1, 1014, 1007, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1241, '17区-综合仓', 1, 1014, 1008, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1242, '18区-综合仓', 1, 1014, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1243, '19区-综合仓', 1, 1014, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1244, '20区-综合仓', 1, 1014, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1245, '3区-综合仓', 1, 1014, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1246, '4区-综合仓', 1, 1014, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1247, '5区-综合仓', 1, 1014, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1272, '12区-综合仓排风口', 1, 1016, 1003, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1278, '18区-综合仓排风口', 1, 1016, 1009, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1279, '19区-综合仓排风口', 1, 1016, 1010, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1280, '20区-综合仓排风口', 1, 1016, 1011, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1281, '3区-综合仓排风口', 1, 1016, 1012, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1283, '5区-综合仓排风口', 1, 1016, 1014, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1284, '6区-综合仓排风口', 1, 1016, 1015, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1285, '7区-综合仓排风口', 1, 1016, 1016, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1286, '8区-综合仓排风口', 1, 1016, 1017, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1287, '9区-综合仓排风口', 1, 1016, 1018, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1282, '4区-综合仓排风口', 1, 1016, 1013, 10, null, null, null, to_date('28-11-2018 09:56:30', 'dd-mm-yyyy hh24:mi:ss'));

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
values (1, 'job1', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'saveDataScheduleBatch', '0 0/1 * * * ?', '监测对象数据定时保存');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (7, 'job7', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'deleteDataSchedule', '0 0 9 * * ?', '监测对象数据定时删除');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1000, 'job8', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'saveToConsumeData', '0 0 1 * * ?', '定时任务统计每天的能耗并保存到ConsumeData表中');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1001, 'job9', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'heartBeat', '0/5 * * * * ?', '登录maxview终端之后，持续发送心跳 ');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1002, 'job10', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'sendTestData', '0/15 * * * * ?', '模拟maxview发送数据');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1003, 'job11', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'sendTestAlarm', '0/30 * * * * ?', '模拟maxview发送告警');
commit;
