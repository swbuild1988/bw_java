--管廊数据
insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, CRT_TIME)
values (1, '古城大街', 5000, 1, 5, 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, CRT_TIME)
values (2, '实验路', 1000, 1, 5, 1, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, CRT_TIME)
values (3, '经三路', 1000, 1, 5, 1, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, CRT_TIME)
values (4, '经二路', 1000, 1, 5, 1, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_TUNNEL (ID, NAME, LENGTH, RESPONSIBILITY_ID, CONSTRUCT_ID, OPERATION_ID, CAMERA, CRT_TIME)
values (5, '纬三路', 5000, 1, 5, 1, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

commit;


--管廊仓类型数据
insert into T_COMMON_STORE_TYPE (ID, NAME, CRT_TIME)
values (1, '燃气仓', to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (ID, NAME, CRT_TIME)
values (2, '综合仓', to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (ID, NAME, CRT_TIME)
values (3, '电力仓', to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE_TYPE (ID, NAME, CRT_TIME)
values (4, '污水仓', to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));
commit;

--area 区域表
insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (1, '1区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (2, '2区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (3, '3区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (4, '4区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (5, '5区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (6, '6区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (7, '7区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (8, '8区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (9, '9区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (10, '10区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (11, '11区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (12, '12区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (13, '13区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (14, '14区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (15, '15区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (16, '16区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (17, '17区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (18, '18区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (19, '19区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (20, '20区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (21, '21区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (22, '22区', '1', 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (23, '1区', '1', 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (24, '2区', '1', 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (25, '3区', '1', 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (26, '4区', '1', 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (27, '5区', '1', 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (28, '6区', '1', 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (29, '1区', '1', 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (30, '2区', '1', 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (31, '3区', '1', 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (32, '4区', '1', 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (33, '5区', '1', 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (34, '6区', '1', 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (35, '1区', '1', 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (36, '2区', '1', 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (37, '3区', '1', 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (38, '4区', '1', 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (39, '5区', '1', 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (40, '6区', '1', 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (41, '1区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (42, '2区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (43, '3区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (44, '4区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (45, '5区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (46, '6区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (47, '7区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (48, '8区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (49, '9区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (50, '10区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (51, '11区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (52, '12区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (53, '13区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (54, '14区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (55, '15区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (56, '16区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (57, '17区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (58, '18区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (59, '19区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (60, '20区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (61, '21区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_AREA (ID, NAME, LOCATION, TUNNEL_ID, CAMERA, CRT_TIME)
values (62, '22区', '1', 5, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

--store 管仓表
insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (1, '燃气仓', 1, 1, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (2, '综合仓', 1, 2, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (3, '电力仓', 1, 3, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (4, '污水仓', 1, 4, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (5, '燃气仓', 2, 1, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (6, '综合仓', 2, 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (7, '电力仓', 2, 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (8, '污水仓', 2, 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (9, '燃气仓', 3, 1, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (10, '综合仓', 3, 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (11, '电力仓', 3, 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (12, '污水仓', 3, 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (13, '燃气仓', 4, 1, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (14, '综合仓', 4, 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (15, '电力仓', 4, 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (16, '污水仓', 4, 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (17, '燃气仓', 5, 1, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (18, '综合仓', 5, 2, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (19, '电力仓', 5, 3, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_STORE (ID, NAME, TUNNEL_ID, STORE_TYPE_ID, CAMERA, CRT_TIME)
values (20, '污水仓', 5, 4, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

commit;


--section 管仓区间段
insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (1, '1区-燃气仓', 1, 1, 5, null,  '112.494028,37.707195,6.130', '112.494028,37.706120,6.130', to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (2, '2区-燃气仓', 1, 2, 5, null, null, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (3, '3区-燃气仓', 1, 3, 5, null, null, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (4, '4区-燃气仓', 1, 4, 5, null, null, null, to_date('30-08-2018 14:44:50', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (5, '5区-燃气仓', 1, 5, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (6, '6区-燃气仓', 1, 6, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (7, '7区-燃气仓', 1, 7, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (8, '8区-燃气仓', 1, 8, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (9, '9区-燃气仓', 1, 9, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (10, '10区-燃气仓', 1, 10, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (11, '11区-燃气仓', 1, 11, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (12, '12区-燃气仓', 1, 12, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (13, '13区-燃气仓', 1, 13, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (14, '14区-燃气仓', 1, 14, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (15, '15区-燃气仓', 1, 15, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (16, '16区-燃气仓', 1, 16, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (17, '17区-燃气仓', 1, 17, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (18, '18区-燃气仓', 1, 18, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (19, '19区-燃气仓', 1, 19, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (20, '20区-燃气仓', 1, 20, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (21, '21区-燃气仓', 1, 21, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (22, '22区-燃气仓', 1, 22, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (23, '1区-污水仓', 4, 1, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (24, '2区-污水仓', 4, 2, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (25, '3区-污水仓', 4, 3, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (26, '4区-污水仓', 4, 4, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (27, '5区-污水仓', 4, 5, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (28, '6区-污水仓', 4, 6, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (29, '7区-污水仓', 4, 7, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (30, '8区-污水仓', 4, 8, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (31, '9区-污水仓', 4, 9, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (32, '10区-污水仓', 4, 10, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (33, '11区-污水仓', 4, 11, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (34, '12区-污水仓', 4, 12, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (35, '13区-污水仓', 4, 13, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (36, '14区-污水仓', 4, 14, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (37, '15区-污水仓', 4, 15, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (38, '16区-污水仓', 4, 16, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (39, '17区-污水仓', 4, 17, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (40, '18区-污水仓', 4, 18, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (41, '19区-污水仓', 4, 19, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (42, '20区-污水仓', 4, 20, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (43, '21区-污水仓', 4, 21, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (44, '22区-污水仓', 4, 22, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (45, '1区-电力仓', 3, 1, 20, null, '112.494079,37.707195,6.130', '112.494079,37.706120,6.130', to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (46, '2区-电力仓', 3, 2, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (47, '3区-电力仓', 3, 3, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (48, '4区-电力仓', 3, 4, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (49, '5区-电力仓', 3, 5, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (50, '6区-电力仓', 3, 6, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (51, '7区-电力仓', 3, 7, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (52, '8区-电力仓', 3, 8, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (53, '9区-电力仓', 3, 9, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (54, '10区-电力仓', 3, 10, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (55, '11区-电力仓', 3, 11, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (56, '12区-电力仓', 3, 12, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (57, '13区-电力仓', 3, 13, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (58, '14区-电力仓', 3, 14, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (59, '15区-电力仓', 3, 15, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (60, '16区-电力仓', 3, 16, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (61, '17区-电力仓', 3, 17, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (62, '18区-电力仓', 3, 18, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (63, '19区-电力仓', 3, 19, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (64, '20区-电力仓', 3, 20, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (65, '21区-电力仓', 3, 21, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (66, '22区-电力仓', 3, 22, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (67, '1区-综合仓', 2, 1, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (68, '2区-综合仓', 2, 2, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (69, '3区-综合仓', 2, 3, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (70, '4区-综合仓', 2, 4, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (71, '5区-综合仓', 2, 5, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (72, '6区-综合仓', 2, 6, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (73, '7区-综合仓', 2, 7, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (74, '8区-综合仓', 2, 8, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (75, '9区-综合仓', 2, 9, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (76, '10区-综合仓', 2, 10, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (77, '11区-综合仓', 2, 11, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (78, '12区-综合仓', 2, 12, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (79, '13区-综合仓', 2, 13, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (80, '14区-综合仓', 2, 14, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (81, '15区-综合仓', 2, 15, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (82, '16区-综合仓', 2, 16, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (83, '17区-综合仓', 2, 17, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (84, '18区-综合仓', 2, 18, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (85, '19区-综合仓', 2, 19, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (86, '20区-综合仓', 2, 20, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (87, '21区-综合仓', 2, 21, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (88, '22区-综合仓', 2, 22, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (89, '1区-燃气仓', 5, 23, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (90, '2区-燃气仓', 5, 24, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (91, '3区-燃气仓', 5, 25, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (92, '4区-燃气仓', 5, 26, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (93, '5区-燃气仓', 5, 27, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (94, '6区-燃气仓', 5, 28, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (95, '1区-污水仓', 8, 23, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (96, '2区-污水仓', 8, 24, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (97, '3区-污水仓', 8, 25, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (98, '4区-污水仓', 8, 26, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (99, '5区-污水仓', 8, 27, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (100, '6区-污水仓', 8, 28, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (101, '1区-电力仓', 7, 23, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (102, '2区-电力仓', 7, 24, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (103, '3区-电力仓', 7, 25, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (104, '4区-电力仓', 7, 26, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (105, '5区-电力仓', 7, 27, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (106, '6区-电力仓', 7, 28, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (107, '1区-综合仓', 6, 23, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (108, '2区-综合仓', 6, 24, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (109, '3区-综合仓', 6, 25, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (110, '4区-综合仓', 6, 26, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (111, '5区-综合仓', 6, 27, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (112, '6区-综合仓', 6, 28, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (113, '1区-燃气仓', 9, 29, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (114, '2区-燃气仓', 9, 30, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (115, '3区-燃气仓', 9, 31, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (116, '4区-燃气仓', 9, 32, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (117, '5区-燃气仓', 9, 33, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (118, '6区-燃气仓', 9, 34, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (119, '1区-污水仓', 12, 29, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (120, '2区-污水仓', 12, 30, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (121, '3区-污水仓', 12, 31, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (122, '4区-污水仓', 12, 32, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (123, '5区-污水仓', 12, 33, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (124, '6区-污水仓', 12, 34, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (125, '1区-电力仓', 11, 29, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (126, '2区-电力仓', 11, 30, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (127, '3区-电力仓', 11, 31, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (128, '4区-电力仓', 11, 32, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (129, '5区-电力仓', 11, 33, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (130, '6区-电力仓', 11, 34, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (131, '1区-综合仓', 10, 29, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (132, '2区-综合仓', 10, 30, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (133, '3区-综合仓', 10, 31, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (134, '4区-综合仓', 10, 32, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (135, '5区-综合仓', 10, 33, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (136, '6区-综合仓', 10, 34, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (137, '1区-燃气仓', 13, 35, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (138, '2区-燃气仓', 13, 36, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (139, '3区-燃气仓', 13, 37, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (140, '4区-燃气仓', 13, 38, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (141, '5区-燃气仓', 13, 39, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (142, '6区-燃气仓', 13, 40, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (143, '1区-污水仓', 16, 35, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (144, '2区-污水仓', 16, 36, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (145, '3区-污水仓', 16, 37, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (146, '4区-污水仓', 16, 38, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (147, '5区-污水仓', 16, 39, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (148, '6区-污水仓', 16, 40, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (149, '1区-电力仓', 15, 35, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (150, '2区-电力仓', 15, 36, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (151, '3区-电力仓', 15, 37, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (152, '4区-电力仓', 15, 38, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (153, '5区-电力仓', 15, 39, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (154, '6区-电力仓', 15, 40, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (155, '1区-综合仓', 14, 35, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (156, '2区-综合仓', 14, 36, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (157, '3区-综合仓', 14, 37, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (158, '4区-综合仓', 14, 38, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (159, '5区-综合仓', 14, 39, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (160, '6区-综合仓', 14, 40, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (161, '1区-燃气仓', 17, 41, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (162, '2区-燃气仓', 17, 42, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (163, '3区-燃气仓', 17, 43, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (164, '4区-燃气仓', 17, 44, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (165, '5区-燃气仓', 17, 45, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (166, '6区-燃气仓', 17, 46, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (167, '7区-燃气仓', 17, 47, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (168, '8区-燃气仓', 17, 48, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (169, '9区-燃气仓', 17, 49, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (170, '10区-燃气仓', 17, 50, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (171, '11区-燃气仓', 17, 51, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (172, '12区-燃气仓', 17, 52, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (173, '13区-燃气仓', 17, 53, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (174, '14区-燃气仓', 17, 54, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (175, '15区-燃气仓', 17, 55, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (176, '16区-燃气仓', 17, 56, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (177, '17区-燃气仓', 17, 57, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (178, '18区-燃气仓', 17, 58, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (179, '19区-燃气仓', 17, 59, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (180, '20区-燃气仓', 17, 60, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (181, '21区-燃气仓', 17, 61, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (182, '22区-燃气仓', 17, 62, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (183, '1区-污水仓', 20, 41, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (184, '2区-污水仓', 20, 42, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (185, '3区-污水仓', 20, 43, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (186, '4区-污水仓', 20, 44, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (187, '5区-污水仓', 20, 45, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (188, '6区-污水仓', 20, 46, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (189, '7区-污水仓', 20, 47, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (190, '8区-污水仓', 20, 48, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (191, '9区-污水仓', 20, 49, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (192, '10区-污水仓', 20, 50, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (193, '11区-污水仓', 20, 51, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (194, '12区-污水仓', 20, 52, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (195, '13区-污水仓', 20, 53, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (196, '14区-污水仓', 20, 54, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (197, '15区-污水仓', 20, 55, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (198, '16区-污水仓', 20, 56, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (199, '17区-污水仓', 20, 57, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (200, '18区-污水仓', 20, 58, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (201, '19区-污水仓', 20, 59, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (202, '20区-污水仓', 20, 60, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (203, '21区-污水仓', 20, 61, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (204, '22区-污水仓', 20, 62, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (205, '1区-电力仓', 19, 41, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (206, '2区-电力仓', 19, 42, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (207, '3区-电力仓', 19, 43, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (208, '4区-电力仓', 19, 44, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (209, '5区-电力仓', 19, 45, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (210, '6区-电力仓', 19, 46, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (211, '7区-电力仓', 19, 47, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (212, '8区-电力仓', 19, 48, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (213, '9区-电力仓', 19, 49, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (214, '10区-电力仓', 19, 50, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (215, '11区-电力仓', 19, 51, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (216, '12区-电力仓', 19, 52, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (217, '13区-电力仓', 19, 53, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (218, '14区-电力仓', 19, 54, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (219, '15区-电力仓', 19, 55, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (220, '16区-电力仓', 19, 56, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (221, '17区-电力仓', 19, 57, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (222, '18区-电力仓', 19, 58, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (223, '19区-电力仓', 19, 59, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (224, '20区-电力仓', 19, 60, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (225, '21区-电力仓', 19, 61, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (226, '22区-电力仓', 19, 62, 20, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (227, '1区-综合仓', 18, 41, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (228, '2区-综合仓', 18, 42, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (229, '3区-综合仓', 18, 43, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (230, '4区-综合仓', 18, 44, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (231, '5区-综合仓', 18, 45, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (232, '6区-综合仓', 18, 46, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (233, '7区-综合仓', 18, 47, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (234, '8区-综合仓', 18, 48, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (235, '9区-综合仓', 18, 49, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (236, '10区-综合仓', 18, 50, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (237, '11区-综合仓', 18, 51, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (238, '12区-综合仓', 18, 52, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (239, '13区-综合仓', 18, 53, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (240, '14区-综合仓', 18, 54, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (241, '15区-综合仓', 18, 55, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (242, '16区-综合仓', 18, 56, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (243, '17区-综合仓', 18, 57, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (244, '18区-综合仓', 18, 58, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (245, '19区-综合仓', 18, 59, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (246, '20区-综合仓', 18, 60, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (247, '21区-综合仓', 18, 61, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_COMMON_SECTION (ID, NAME, STORE_ID, AREA_ID, TOTAL_CABLE_NUMBER, CAMERA, START_POINT, END_POINT, CRT_TIME)
values (248, '22区-综合仓', 18, 62, 5, null, null, null, to_date('30-08-2018 14:44:51', 'dd-mm-yyyy hh24:mi:ss'));
commit;


--shiro权限管理
--插入用户表数据
insert into T_SECURITY_USER (ID, NAME, PASSWORD, CRT_TIME)
values (1, 'admin', '123456', to_date('20-06-2018 10:47:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_SECURITY_USER (ID, NAME, PASSWORD,  CRT_TIME)
values (2, 'user', '123456', to_date('20-06-2018 10:47:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_SECURITY_USER (ID, NAME, PASSWORD, CRT_TIME)
values (3, 'tunneluser1', '123456',  to_date('20-06-2018 10:47:02', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_SECURITY_USER (ID, NAME, PASSWORD,  CRT_TIME)
values (4, 'tunneluser2', '123456',  to_date('20-06-2018 10:47:02', 'dd-mm-yyyy hh24:mi:ss'));

--插入角色表数据
insert into t_security_role (id,role_code,role_desc,crt_time) values (1,'admin','超级管理员',sysdate);
insert into t_security_role (id,role_code,role_desc,crt_time) values (2,'user','普通用户',sysdate);
insert into t_security_role (id,role_code,role_desc,crt_time) values (3,'tunnel','管廊管理员',sysdate);
insert into t_security_role (id,role_code,role_desc,crt_time) values (4,'tunnel-area1','管廊管理员-区域1',sysdate);

--插入权限表数据
insert into t_security_permission (id,per_code,per_desc,crt_time) values (1,'operation:*','增删改查',sysdate);
insert into t_security_permission (id,per_code,per_desc,crt_time) values (2,'operation:query','查询',sysdate);
insert into t_security_permission (id,per_code,per_desc,crt_time) values (3,'operation:add','添加',sysdate);
insert into t_security_permission (id,per_code,per_desc,crt_time) values (4,'operation:delete','删除',sysdate);
insert into t_security_permission (id,per_code,per_desc,crt_time) values (5,'operation:update','更新',sysdate);
insert into t_security_permission (id,per_code,per_desc,crt_time) values (6,'tunnel:*:*','所有管廊权限',sysdate);
insert into t_security_permission (id,per_code,per_desc,crt_time) values (7,'tunnel:*:1','管廊1权限',sysdate);
insert into t_security_permission (id,per_code,per_desc,crt_time) values (8,'tunnel:*:2','管廊2权限',sysdate);

--插入用户角色表数据
insert into t_security_user_role (id,user_id,role_id,crt_time) values (1,1,1,sysdate);
insert into t_security_user_role (id,user_id,role_id,crt_time) values (2,2,2,sysdate);
insert into t_security_user_role (id,user_id,role_id,crt_time) values (3,3,3,sysdate);
insert into t_security_user_role (id,user_id,role_id,crt_time) values (4,4,4,sysdate);

--插入角色权限表数据 
insert into t_security_role_permission (id,role_id,per_id,crt_time) values (1,1,1,sysdate);
insert into t_security_role_permission (id,role_id,per_id,crt_time) values (2,1,6,sysdate);
insert into t_security_role_permission (id,role_id,per_id,crt_time) values (3,2,2,sysdate);
insert into t_security_role_permission (id,role_id,per_id,crt_time) values (4,3,6,sysdate);
insert into t_security_role_permission (id,role_id,per_id,crt_time) values (5,4,7,sysdate);
commit;



--员工表
insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID,account_id, CRT_TIME)
values (1, '张三', 1, 1,1, to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID,account_id, CRT_TIME)
values (2, '李四', 2, 2, 2,to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID,account_id, CRT_TIME)
values (3, '王五', 3, 3, 3,to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID, account_id,CRT_TIME)
values (4, '赵六', 4, 4, 4,to_date('26-06-2018', 'dd-mm-yyyy'));

insert into T_COMMON_staff (ID, NAME, DEPT_ID, POSITION_ID,account_id, CRT_TIME)
values (5, '王二', 1, 3, 5,to_date('26-06-2018', 'dd-mm-yyyy'));
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
values (5, '中建一局', to_date('22-06-2018', 'dd-mm-yyyy'));
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
values (1000, 'job8', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'save2EnergyTable', '0 15 9 * * ?', '定时任务统计每条管廊每天能耗并保存到能耗表中');

commit;
