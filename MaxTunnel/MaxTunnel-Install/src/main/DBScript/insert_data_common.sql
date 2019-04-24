--��������
insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (1, '�ųǴ��', 5000, '02', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'), 1);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (2, 'ʵ��·', 1000, '03', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (3, '����·', 1000, '04', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (4, '����·', 1000, '05', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (5, 'γ��·', 5000, '06', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 2);

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, SN, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, MAXVIEW_CONFIG_ID, CRT_TIME, STATUS)
values (6, '�������', 500, '01', 1, 5, 1, null, 1, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'), 1);

commit;

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1001, '������', '02', 1001, 1, 1001, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1002, '�����ս����', '12', 1001, 1, 1006, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1003, '�������ŷ��', '22', 1001, 1, 1010, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1004, '��ȼ����Ͷ�Ͼ�', '32', 1004, 1, 1014, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1005, '����', '41', 1005, 1, 1015, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1006, 'ȼ����', '04', 1006, 1, 1003, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1007, 'ȼ���ս����', '14', 1006, 1, 1008, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1008, 'ȼ�����ŷ��', '24', 1006, 1, 1012, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1009, 'ȼ����Ͷ�Ͼ�', '31', 1006, 1, 1013, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1010, '�豸��', '05', 1010, 1, 1004, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1011, '��ˮ��', '01', 1011, 1, 1000, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1012, '��ˮ�ս����', '11', 1011, 1, 1005, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1013, '��ˮ���ŷ��', '21', 1011, 1, 1009, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1014, '�ۺϲ�', '03', 1014, 1, 1002, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1015, '�ۺϲս����', '13', 1014, 1, 1007, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1016, '�ۺϲ��ŷ��', '23', 1014, 1, 1011, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, SN, PARENT_ID, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1017, '����', '41', 1017, 6, 1015, null, to_date('28-11-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'));

commit;

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1001, '10��', '10', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1002, '11��', '11', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1003, '12��', '12', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1004, '13��', '13', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1005, '14��', '14', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1006, '15��', '15', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1007, '16��', '16', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1008, '17��', '17', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1009, '18��', '18', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1010, '19��', '19', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1011, '20��', '20', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1012, '3��', '03', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1013, '4��', '04', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1014, '5��', '05', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1015, '6��', '06', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1016, '7��', '07', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1017, '8��', '08', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1018, '9��', '09', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1019, '21��', '21', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1020, '22��', '22', null, 1, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, SN, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1021, '1��', '01', null, 6, null, to_date('28-11-2018 09:56:26', 'dd-mm-yyyy hh24:mi:ss'));

commit;

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1200, '3��-�������ŷ��', 1, 1003, 1012, 10, 1020, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1201, '4��-�������ŷ��', 1, 1003, 1013, 10, 1021, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1202, '5��-�������ŷ��', 1, 1003, 1014, 10, 1022, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1203, '6��-�������ŷ��', 1, 1003, 1015, 10, 1023, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1204, '7��-�������ŷ��', 1, 1003, 1016, 10, 1024, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1205, '8��-�������ŷ��', 1, 1003, 1017, 10, 1025, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1206, '9��-�������ŷ��', 1, 1003, 1018, 10, 1026, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1207, '10��-�������ŷ��', 1, 1003, 1001, 10, 1027, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1208, '11��-�������ŷ��', 1, 1003, 1002, 10, 1028, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1209, '12��-�������ŷ��', 1, 1003, 1003, 10, 1029, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1210, '13��-�������ŷ��', 1, 1003, 1004, 10, 1030, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1211, '14��-�������ŷ��', 1, 1003, 1005, 10, 1031, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1212, '15��-�������ŷ��', 1, 1003, 1006, 10, 1032, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1213, '16��-�������ŷ��', 1, 1003, 1007, 10, 1033, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1214, '17��-�������ŷ��', 1, 1003, 1008, 10, 1034, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1215, '18��-�������ŷ��', 1, 1003, 1009, 10, 1035, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1216, '19��-�������ŷ��', 1, 1003, 1010, 10, 1036, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1217, '20��-�������ŷ��', 1, 1003, 1011, 10, 1037, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1218, '21��-�������ŷ��', 1, 1003, 1019, 10, 1038, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1220, '3��-�ۺϲ��ŷ��', 1, 1016, 1012, 10, 1040, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1221, '4��-�ۺϲ��ŷ��', 1, 1016, 1013, 10, 1041, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1011, '14��-��ˮ��', 1, 1011, 1005, 10, 1011, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1013, '16��-��ˮ��', 1, 1011, 1007, 10, 1013, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1014, '17��-��ˮ��', 1, 1011, 1008, 10, 1014, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1017, '20��-��ˮ��', 1, 1011, 1011, 10, 1017, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1019, '22��-��ˮ��', 1, 1011, 1020, 10, 1019, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1020, '3��-������', 1, 1001, 1012, 10, 1020, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1025, '8��-������', 1, 1001, 1017, 10, 1025, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1026, '9��-������', 1, 1001, 1018, 10, 1026, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1041, '4��-�ۺϲ�', 1, 1014, 1013, 10, 1041, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1060, '3��-ȼ����', 1, 1006, 1012, 10, 1060, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1061, '4��-ȼ����', 1, 1006, 1013, 10, 1061, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1062, '5��-ȼ����', 1, 1006, 1014, 10, 1062, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1063, '6��-ȼ����', 1, 1006, 1015, 10, 1063, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1091, '14��-�豸��', 1, 1010, 1005, 10, 1091, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1093, '16��-�豸��', 1, 1010, 1007, 10, 1093, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1094, '17��-�豸��', 1, 1010, 1008, 10, 1094, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1095, '18��-�豸��', 1, 1010, 1009, 10, 1095, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1109, '12��-��ˮ�ս����', 1, 1012, 1003, 10, 1009, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1110, '13��-��ˮ�ս����', 1, 1012, 1004, 10, 1010, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1111, '14��-��ˮ�ս����', 1, 1012, 1005, 10, 1011, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1112, '15��-��ˮ�ս����', 1, 1012, 1006, 10, 1012, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1113, '16��-��ˮ�ս����', 1, 1012, 1007, 10, 1013, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1114, '17��-��ˮ�ս����', 1, 1012, 1008, 10, 1014, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1115, '18��-��ˮ�ս����', 1, 1012, 1009, 10, 1015, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1116, '19��-��ˮ�ս����', 1, 1012, 1010, 10, 1016, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1117, '20��-��ˮ�ս����', 1, 1012, 1011, 10, 1017, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1118, '21��-��ˮ�ս����', 1, 1012, 1019, 10, 1018, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1119, '22��-��ˮ�ս����', 1, 1012, 1020, 10, 1019, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1120, '3��-�����ս����', 1, 1002, 1012, 10, 1020, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1121, '4��-�����ս����', 1, 1002, 1013, 10, 1021, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1122, '5��-�����ս����', 1, 1002, 1014, 10, 1022, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1123, '6��-�����ս����', 1, 1002, 1015, 10, 1023, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1124, '7��-�����ս����', 1, 1002, 1016, 10, 1024, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1140, '3��-�ۺϲս����', 1, 1015, 1012, 10, 1040, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1141, '4��-�ۺϲս����', 1, 1015, 1013, 10, 1041, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1142, '5��-�ۺϲս����', 1, 1015, 1014, 10, 1042, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1143, '6��-�ۺϲս����', 1, 1015, 1015, 10, 1043, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1144, '7��-�ۺϲս����', 1, 1015, 1016, 10, 1044, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1145, '8��-�ۺϲս����', 1, 1015, 1017, 10, 1045, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1146, '9��-�ۺϲս����', 1, 1015, 1018, 10, 1046, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1147, '10��-�ۺϲս����', 1, 1015, 1001, 10, 1047, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1148, '11��-�ۺϲս����', 1, 1015, 1002, 10, 1048, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1149, '12��-�ۺϲս����', 1, 1015, 1003, 10, 1049, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1150, '13��-�ۺϲս����', 1, 1015, 1004, 10, 1050, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1151, '14��-�ۺϲս����', 1, 1015, 1005, 10, 1051, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1152, '15��-�ۺϲս����', 1, 1015, 1006, 10, 1052, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1153, '16��-�ۺϲս����', 1, 1015, 1007, 10, 1053, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1154, '17��-�ۺϲս����', 1, 1015, 1008, 10, 1054, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1155, '18��-�ۺϲս����', 1, 1015, 1009, 10, 1055, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1156, '19��-�ۺϲս����', 1, 1015, 1010, 10, 1056, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1157, '20��-�ۺϲս����', 1, 1015, 1011, 10, 1057, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1158, '21��-�ۺϲս����', 1, 1015, 1019, 10, 1058, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1159, '22��-�ۺϲս����', 1, 1015, 1020, 10, 1059, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1160, '3��-ȼ���ս����', 1, 1007, 1012, 10, 1060, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1161, '4��-ȼ���ս����', 1, 1007, 1013, 10, 1061, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1162, '5��-ȼ���ս����', 1, 1007, 1014, 10, 1062, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1163, '6��-ȼ���ս����', 1, 1007, 1015, 10, 1063, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1164, '7��-ȼ���ս����', 1, 1007, 1016, 10, 1064, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1165, '8��-ȼ���ս����', 1, 1007, 1017, 10, 1065, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1166, '9��-ȼ���ս����', 1, 1007, 1018, 10, 1066, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1167, '10��-ȼ���ս����', 1, 1007, 1001, 10, 1067, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1219, '22��-�������ŷ��', 1, 1003, 1020, 10, 1039, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1222, '5��-�ۺϲ��ŷ��', 1, 1016, 1014, 10, 1042, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1223, '6��-�ۺϲ��ŷ��', 1, 1016, 1015, 10, 1043, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1224, '7��-�ۺϲ��ŷ��', 1, 1016, 1016, 10, 1044, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1225, '8��-�ۺϲ��ŷ��', 1, 1016, 1017, 10, 1045, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1226, '9��-�ۺϲ��ŷ��', 1, 1016, 1018, 10, 1046, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1227, '10��-�ۺϲ��ŷ��', 1, 1016, 1001, 10, 1047, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1228, '11��-�ۺϲ��ŷ��', 1, 1016, 1002, 10, 1048, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1229, '12��-�ۺϲ��ŷ��', 1, 1016, 1003, 10, 1049, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1230, '13��-�ۺϲ��ŷ��', 1, 1016, 1004, 10, 1050, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1231, '14��-�ۺϲ��ŷ��', 1, 1016, 1005, 10, 1051, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1232, '15��-�ۺϲ��ŷ��', 1, 1016, 1006, 10, 1052, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1233, '16��-�ۺϲ��ŷ��', 1, 1016, 1007, 10, 1053, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1234, '17��-�ۺϲ��ŷ��', 1, 1016, 1008, 10, 1054, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1235, '18��-�ۺϲ��ŷ��', 1, 1016, 1009, 10, 1055, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1236, '19��-�ۺϲ��ŷ��', 1, 1016, 1010, 10, 1056, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1237, '20��-�ۺϲ��ŷ��', 1, 1016, 1011, 10, 1057, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1238, '21��-�ۺϲ��ŷ��', 1, 1016, 1019, 10, 1058, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1239, '22��-�ۺϲ��ŷ��', 1, 1016, 1020, 10, 1059, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1240, '3��-ȼ�����ŷ��', 1, 1008, 1012, 10, 1060, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1241, '4��-ȼ�����ŷ��', 1, 1008, 1013, 10, 1061, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1242, '5��-ȼ�����ŷ��', 1, 1008, 1014, 10, 1062, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1243, '6��-ȼ�����ŷ��', 1, 1008, 1015, 10, 1063, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1244, '7��-ȼ�����ŷ��', 1, 1008, 1016, 10, 1064, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1245, '8��-ȼ�����ŷ��', 1, 1008, 1017, 10, 1065, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1246, '9��-ȼ�����ŷ��', 1, 1008, 1018, 10, 1066, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1247, '10��-ȼ�����ŷ��', 1, 1008, 1001, 10, 1067, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1248, '11��-ȼ�����ŷ��', 1, 1008, 1002, 10, 1068, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1249, '12��-ȼ�����ŷ��', 1, 1008, 1003, 10, 1069, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1250, '13��-ȼ�����ŷ��', 1, 1008, 1004, 10, 1070, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1251, '14��-ȼ�����ŷ��', 1, 1008, 1005, 10, 1071, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1252, '15��-ȼ�����ŷ��', 1, 1008, 1006, 10, 1072, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1253, '16��-ȼ�����ŷ��', 1, 1008, 1007, 10, 1073, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1254, '17��-ȼ�����ŷ��', 1, 1008, 1008, 10, 1074, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1255, '18��-ȼ�����ŷ��', 1, 1008, 1009, 10, 1075, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1256, '19��-ȼ�����ŷ��', 1, 1008, 1010, 10, 1076, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1257, '20��-ȼ�����ŷ��', 1, 1008, 1011, 10, 1077, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1258, '21��-ȼ�����ŷ��', 1, 1008, 1019, 10, 1078, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1259, '22��-ȼ�����ŷ��', 1, 1008, 1020, 10, 1079, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1260, '3��-ȼ����Ͷ�Ͼ�', 1, 1009, 1012, 10, 1060, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1261, '4��-ȼ����Ͷ�Ͼ�', 1, 1009, 1013, 10, 1061, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1262, '6��-ȼ����Ͷ�Ͼ�', 1, 1009, 1015, 10, 1063, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1263, '7��-ȼ����Ͷ�Ͼ�', 1, 1009, 1016, 10, 1064, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1264, '8��-ȼ����Ͷ�Ͼ�', 1, 1009, 1017, 10, 1065, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1265, '9��-ȼ����Ͷ�Ͼ�', 1, 1009, 1018, 10, 1066, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1266, '10��-ȼ����Ͷ�Ͼ�', 1, 1009, 1001, 10, 1067, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1267, '11��-ȼ����Ͷ�Ͼ�', 1, 1009, 1002, 10, 1068, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1268, '12��-ȼ����Ͷ�Ͼ�', 1, 1009, 1003, 10, 1069, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1269, '13��-ȼ����Ͷ�Ͼ�', 1, 1009, 1004, 10, 1070, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1270, '14��-ȼ����Ͷ�Ͼ�', 1, 1009, 1005, 10, 1071, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1271, '16��-ȼ����Ͷ�Ͼ�', 1, 1009, 1007, 10, 1073, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1272, '17��-ȼ����Ͷ�Ͼ�', 1, 1009, 1008, 10, 1074, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1273, '18��-ȼ����Ͷ�Ͼ�', 1, 1009, 1009, 10, 1075, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1274, '19��-ȼ����Ͷ�Ͼ�', 1, 1009, 1010, 10, 1076, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1275, '20��-ȼ����Ͷ�Ͼ�', 1, 1009, 1011, 10, 1077, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1276, '21��-ȼ����Ͷ�Ͼ�', 1, 1009, 1019, 10, 1078, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1277, '22��-ȼ����Ͷ�Ͼ�', 1, 1009, 1020, 10, 1079, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1278, '3��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1012, 10, 1278, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1279, '6��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1015, 10, 1279, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1280, '7��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1016, 10, 1280, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1281, '9��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1018, 10, 1281, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1282, '11��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1002, 10, 1282, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1283, '13��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1004, 10, 1283, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1284, '16��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1007, 10, 1284, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1285, '17��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1008, 10, 1285, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1286, '19��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1010, 10, 1286, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1287, '21��-��ȼ����Ͷ�Ͼ�', 1, 1004, 1019, 10, 1287, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1288, '1��-����', 6, 1017, 1021, 10, 1288, null, null, null, to_date('23-12-2018 09:53:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1016, '19��-��ˮ��', 1, 1011, 1010, 10, 1016, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1000, '3��-��ˮ��', 1, 1011, 1012, 10, 1000, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1001, '4��-��ˮ��', 1, 1011, 1013, 10, 1001, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1002, '5��-��ˮ��', 1, 1011, 1014, 10, 1002, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1003, '6��-��ˮ��', 1, 1011, 1015, 10, 1003, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1004, '7��-��ˮ��', 1, 1011, 1016, 10, 1004, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1005, '8��-��ˮ��', 1, 1011, 1017, 10, 1005, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1006, '9��-��ˮ��', 1, 1011, 1018, 10, 1006, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1007, '10��-��ˮ��', 1, 1011, 1001, 10, 1007, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1008, '11��-��ˮ��', 1, 1011, 1002, 10, 1008, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1009, '12��-��ˮ��', 1, 1011, 1003, 10, 1009, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1010, '13��-��ˮ��', 1, 1011, 1004, 10, 1010, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1012, '15��-��ˮ��', 1, 1011, 1006, 10, 1012, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1015, '18��-��ˮ��', 1, 1011, 1009, 10, 1015, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1018, '21��-��ˮ��', 1, 1011, 1019, 10, 1018, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1021, '4��-������', 1, 1001, 1013, 10, 1021, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1022, '5��-������', 1, 1001, 1014, 10, 1022, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1023, '6��-������', 1, 1001, 1015, 10, 1023, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1024, '7��-������', 1, 1001, 1016, 10, 1024, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1027, '10��-������', 1, 1001, 1001, 10, 1027, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1028, '11��-������', 1, 1001, 1002, 10, 1028, null, null, null, to_date('23-12-2018 09:53:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1029, '12��-������', 1, 1001, 1003, 10, 1029, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1030, '13��-������', 1, 1001, 1004, 10, 1030, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1031, '14��-������', 1, 1001, 1005, 10, 1031, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1032, '15��-������', 1, 1001, 1006, 10, 1032, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1033, '16��-������', 1, 1001, 1007, 10, 1033, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1034, '17��-������', 1, 1001, 1008, 10, 1034, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1035, '18��-������', 1, 1001, 1009, 10, 1035, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1036, '19��-������', 1, 1001, 1010, 10, 1036, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1037, '20��-������', 1, 1001, 1011, 10, 1037, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1038, '21��-������', 1, 1001, 1019, 10, 1038, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1039, '22��-������', 1, 1001, 1020, 10, 1039, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1040, '3��-�ۺϲ�', 1, 1014, 1012, 10, 1040, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1042, '5��-�ۺϲ�', 1, 1014, 1014, 10, 1042, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1043, '6��-�ۺϲ�', 1, 1014, 1015, 10, 1043, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1044, '7��-�ۺϲ�', 1, 1014, 1016, 10, 1044, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1045, '8��-�ۺϲ�', 1, 1014, 1017, 10, 1045, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1046, '9��-�ۺϲ�', 1, 1014, 1018, 10, 1046, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1047, '10��-�ۺϲ�', 1, 1014, 1001, 10, 1047, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1048, '11��-�ۺϲ�', 1, 1014, 1002, 10, 1048, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1049, '12��-�ۺϲ�', 1, 1014, 1003, 10, 1049, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1050, '13��-�ۺϲ�', 1, 1014, 1004, 10, 1050, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1051, '14��-�ۺϲ�', 1, 1014, 1005, 10, 1051, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1052, '15��-�ۺϲ�', 1, 1014, 1006, 10, 1052, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1053, '16��-�ۺϲ�', 1, 1014, 1007, 10, 1053, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1054, '17��-�ۺϲ�', 1, 1014, 1008, 10, 1054, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1055, '18��-�ۺϲ�', 1, 1014, 1009, 10, 1055, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1056, '19��-�ۺϲ�', 1, 1014, 1010, 10, 1056, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1057, '20��-�ۺϲ�', 1, 1014, 1011, 10, 1057, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1058, '21��-�ۺϲ�', 1, 1014, 1019, 10, 1058, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1059, '22��-�ۺϲ�', 1, 1014, 1020, 10, 1059, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1064, '7��-ȼ����', 1, 1006, 1016, 10, 1064, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1065, '8��-ȼ����', 1, 1006, 1017, 10, 1065, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1066, '9��-ȼ����', 1, 1006, 1018, 10, 1066, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1067, '10��-ȼ����', 1, 1006, 1001, 10, 1067, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1068, '11��-ȼ����', 1, 1006, 1002, 10, 1068, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1069, '12��-ȼ����', 1, 1006, 1003, 10, 1069, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1070, '13��-ȼ����', 1, 1006, 1004, 10, 1070, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1071, '14��-ȼ����', 1, 1006, 1005, 10, 1071, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1072, '15��-ȼ����', 1, 1006, 1006, 10, 1072, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1073, '16��-ȼ����', 1, 1006, 1007, 10, 1073, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1074, '17��-ȼ����', 1, 1006, 1008, 10, 1074, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1075, '18��-ȼ����', 1, 1006, 1009, 10, 1075, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1076, '19��-ȼ����', 1, 1006, 1010, 10, 1076, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1077, '20��-ȼ����', 1, 1006, 1011, 10, 1077, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1078, '21��-ȼ����', 1, 1006, 1019, 10, 1078, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1079, '22��-ȼ����', 1, 1006, 1020, 10, 1079, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1080, '3��-�豸��', 1, 1010, 1012, 10, 1080, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1081, '4��-�豸��', 1, 1010, 1013, 10, 1081, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1082, '5��-�豸��', 1, 1010, 1014, 10, 1082, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1083, '6��-�豸��', 1, 1010, 1015, 10, 1083, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1084, '7��-�豸��', 1, 1010, 1016, 10, 1084, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1085, '8��-�豸��', 1, 1010, 1017, 10, 1085, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1086, '9��-�豸��', 1, 1010, 1018, 10, 1086, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1087, '10��-�豸��', 1, 1010, 1001, 10, 1087, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1088, '11��-�豸��', 1, 1010, 1002, 10, 1088, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1089, '12��-�豸��', 1, 1010, 1003, 10, 1089, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1090, '13��-�豸��', 1, 1010, 1004, 10, 1090, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1092, '15��-�豸��', 1, 1010, 1006, 10, 1092, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1096, '19��-�豸��', 1, 1010, 1010, 10, 1096, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1097, '20��-�豸��', 1, 1010, 1011, 10, 1097, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1098, '21��-�豸��', 1, 1010, 1019, 10, 1098, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1099, '22��-�豸��', 1, 1010, 1020, 10, 1099, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1100, '3��-��ˮ�ս����', 1, 1012, 1012, 10, 1000, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1101, '4��-��ˮ�ս����', 1, 1012, 1013, 10, 1001, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1102, '5��-��ˮ�ս����', 1, 1012, 1014, 10, 1002, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1103, '6��-��ˮ�ս����', 1, 1012, 1015, 10, 1003, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1104, '7��-��ˮ�ս����', 1, 1012, 1016, 10, 1004, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1105, '8��-��ˮ�ս����', 1, 1012, 1017, 10, 1005, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1106, '9��-��ˮ�ս����', 1, 1012, 1018, 10, 1006, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1107, '10��-��ˮ�ս����', 1, 1012, 1001, 10, 1007, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1108, '11��-��ˮ�ս����', 1, 1012, 1002, 10, 1008, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1125, '8��-�����ս����', 1, 1002, 1017, 10, 1025, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1126, '9��-�����ս����', 1, 1002, 1018, 10, 1026, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1127, '10��-�����ս����', 1, 1002, 1001, 10, 1027, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1128, '11��-�����ս����', 1, 1002, 1002, 10, 1028, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1129, '12��-�����ս����', 1, 1002, 1003, 10, 1029, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1130, '13��-�����ս����', 1, 1002, 1004, 10, 1030, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1131, '14��-�����ս����', 1, 1002, 1005, 10, 1031, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1132, '15��-�����ս����', 1, 1002, 1006, 10, 1032, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1133, '16��-�����ս����', 1, 1002, 1007, 10, 1033, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1134, '17��-�����ս����', 1, 1002, 1008, 10, 1034, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1135, '18��-�����ս����', 1, 1002, 1009, 10, 1035, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1136, '19��-�����ս����', 1, 1002, 1010, 10, 1036, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1137, '20��-�����ս����', 1, 1002, 1011, 10, 1037, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1138, '21��-�����ս����', 1, 1002, 1019, 10, 1038, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1139, '22��-�����ս����', 1, 1002, 1020, 10, 1039, null, null, null, to_date('23-12-2018 09:53:20', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1168, '11��-ȼ���ս����', 1, 1007, 1002, 10, 1068, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1169, '12��-ȼ���ս����', 1, 1007, 1003, 10, 1069, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1170, '13��-ȼ���ս����', 1, 1007, 1004, 10, 1070, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1171, '14��-ȼ���ս����', 1, 1007, 1005, 10, 1071, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1172, '15��-ȼ���ս����', 1, 1007, 1006, 10, 1072, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1173, '16��-ȼ���ս����', 1, 1007, 1007, 10, 1073, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1174, '17��-ȼ���ս����', 1, 1007, 1008, 10, 1074, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1175, '18��-ȼ���ս����', 1, 1007, 1009, 10, 1075, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1176, '19��-ȼ���ս����', 1, 1007, 1010, 10, 1076, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1177, '20��-ȼ���ս����', 1, 1007, 1011, 10, 1077, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1178, '21��-ȼ���ս����', 1, 1007, 1019, 10, 1078, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1179, '22��-ȼ���ս����', 1, 1007, 1020, 10, 1079, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1180, '3��-��ˮ���ŷ��', 1, 1013, 1012, 10, 1000, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1181, '4��-��ˮ���ŷ��', 1, 1013, 1013, 10, 1001, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1182, '5��-��ˮ���ŷ��', 1, 1013, 1014, 10, 1002, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1183, '6��-��ˮ���ŷ��', 1, 1013, 1015, 10, 1003, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1184, '7��-��ˮ���ŷ��', 1, 1013, 1016, 10, 1004, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1185, '8��-��ˮ���ŷ��', 1, 1013, 1017, 10, 1005, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1186, '9��-��ˮ���ŷ��', 1, 1013, 1018, 10, 1006, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1187, '10��-��ˮ���ŷ��', 1, 1013, 1001, 10, 1007, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1188, '11��-��ˮ���ŷ��', 1, 1013, 1002, 10, 1008, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1189, '12��-��ˮ���ŷ��', 1, 1013, 1003, 10, 1009, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1190, '13��-��ˮ���ŷ��', 1, 1013, 1004, 10, 1010, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1191, '14��-��ˮ���ŷ��', 1, 1013, 1005, 10, 1011, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1192, '15��-��ˮ���ŷ��', 1, 1013, 1006, 10, 1012, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1193, '16��-��ˮ���ŷ��', 1, 1013, 1007, 10, 1013, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1194, '17��-��ˮ���ŷ��', 1, 1013, 1008, 10, 1014, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1195, '18��-��ˮ���ŷ��', 1, 1013, 1009, 10, 1015, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1196, '19��-��ˮ���ŷ��', 1, 1013, 1010, 10, 1016, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1197, '20��-��ˮ���ŷ��', 1, 1013, 1011, 10, 1017, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1198, '21��-��ˮ���ŷ��', 1, 1013, 1019, 10, 1018, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, TUNNEL_ID, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, PARENT_ID, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1199, '22��-��ˮ���ŷ��', 1, 1013, 1020, 10, 1019, null, null, null, to_date('23-12-2018 09:53:21', 'dd-mm-yyyy hh24:mi:ss'));

commit;


--user
insert into T_SECURITY_USER (ID, NAME, PASSWORD, CRT_TIME)
values (1000, 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', to_date('20-06-2018 10:47:02', 'dd-mm-yyyy hh24:mi:ss'));



--Ա����
insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID, CRT_TIME)
values (1, '������', 1, 1, to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID, CRT_TIME)
values (2, '����', 2, 2,to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID, CRT_TIME)
values (3, '��˧', 3, 3,to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID,CRT_TIME)
values (4, '������', 4, 4,to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID, CRT_TIME)
values (5, '����Ⱥ', 1, 3,to_date('26-06-2018', 'dd-mm-yyyy'));
commit;


--���ű�
insert into T_COMMON_DEPT (ID, NAME, CRT_TIME)
values (1, '��Ʒ��', to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_DEPT (ID, NAME, CRT_TIME)
values (2, '�з���', to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_DEPT (ID, NAME, CRT_TIME)
values (3, '��Ӫ��', to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_DEPT (ID, NAME, CRT_TIME)
values (4, '���۲�', to_date('26-06-2018', 'dd-mm-yyyy'));
commit;



--��˾��
insert into t_common_company (ID, NAME, CRT_TIME)
values (1, '����Ƽ�', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_company (ID, NAME, CRT_TIME)
values (2, '�й��ƶ�', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_company (ID, NAME, CRT_TIME)
values (3, '�й�ʯ��', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_company (ID, NAME, CRT_TIME)
values (4, '�й�����', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_company (ID, NAME, CRT_TIME)
values (5, '����ʮ����', to_date('22-06-2018', 'dd-mm-yyyy'));
commit;

--��˾�����м��
insert into T_COMMON_company_dept (ID, COMPANY_ID, DEPT_ID)
values (1, 1, 1);

insert into T_COMMON_company_dept (ID, COMPANY_ID, DEPT_ID)
values (2, 1, 2);

insert into T_COMMON_company_dept (ID, COMPANY_ID, DEPT_ID)
values (3, 1, 3);

insert into T_COMMON_company_dept (ID, COMPANY_ID, DEPT_ID)
values (4, 1, 4);
commit;

--ְλ��
insert into t_common_position (ID, NAME, CRT_TIME)
values (1, '�ܾ���', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_position (ID, NAME, CRT_TIME)
values (2, '��Ʒ�ܼ�', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_position (ID, NAME, CRT_TIME)
values (3, '����', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_position (ID, NAME, CRT_TIME)
values (4, '��ͨԱ��', to_date('22-06-2018', 'dd-mm-yyyy'));

insert into t_common_position (ID, NAME, CRT_TIME)
values (5, '��������', to_date('18-06-2018', 'dd-mm-yyyy'));
commit;

--schedule_job��
insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (2, 'job2', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjHour', '0 1 0/1 * * ?', 'ͳ���ϸ�Сʱ�����Сƽ��ֵ');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (3, 'job3', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjDay', '0 0 9 * * ?', 'ͳ�����������Сƽ��ֵ');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (4, 'job4', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjWeek', '0 0 9 ? * MON', 'ͳ�����������Сƽ��ֵ');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (5, 'job5', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjMonth', '0 0 9 1 * ?', 'ͳ�����������Сƽ��ֵ');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (6, 'job6', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'reportMeasObjYear', '0 0 9 1 1 ? *', 'ͳ�����������Сƽ��ֵ');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1, 'job1', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'saveDataScheduleBatch', '0 0/30 * * * ?', '���������ݶ�ʱ����');

insert into t_common_schedule_job (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (7, 'job7', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'deleteDataSchedule', '0 0 9 * * ?', '���������ݶ�ʱɾ��');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1000, 'job8', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'saveToConsumeData', '0 0 1 * * ?', '��ʱ����ͳ��ÿ����ܺĲ����浽ConsumeData����');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1001, 'job9', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'heartBeat', '0/5 * * * * ?', '��¼maxview�ն�֮�󣬳����������� ');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1002, 'job10', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'sendTestData', '0/15 * * * * ?', 'ģ��maxview��������');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1003, 'job11', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'sendTestAlarm', '0/30 * * * * ?', 'ģ��maxview���͸澯');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1004, 'job12', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'runDaysAutoAdded', '1 0 0 * * ?', '��������ʱ�䶨ʱ����');
insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1005, 'job1005', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'exportExcel', '0 0 6 ? * MON', '������');
commit;
