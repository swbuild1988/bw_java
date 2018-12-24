

--measobj


---------------------------------------------  监控中心相机  -----------------------------------------------------------

insert into t_mam_measobj (ID, TUNNEL_ID, STORE_ID, AREA_ID, SECTION_ID, NAME, DATATYPE_ID, OBJTYPE_ID, ACTIVED, DESCRIPTION, LONGITUDE, LATITUDE, HEIGHT, DEVIATION)
values (7001, 1, 1001, 1012, 1011, '红外摄像仪1', 7, 7, 1, null, '', '', '', 0);

insert into t_mam_measobj (ID, TUNNEL_ID, STORE_ID, AREA_ID, SECTION_ID, NAME, DATATYPE_ID, OBJTYPE_ID, ACTIVED, DESCRIPTION, LONGITUDE, LATITUDE, HEIGHT, DEVIATION)
values (7002, 1, 1001, 1012, 1011, '红外摄像仪2', 7, 7, 1, null, '', '', '', 0);

insert into t_mam_measobj (ID, TUNNEL_ID, STORE_ID, AREA_ID, SECTION_ID, NAME, DATATYPE_ID, OBJTYPE_ID, ACTIVED, DESCRIPTION, LONGITUDE, LATITUDE, HEIGHT, DEVIATION)
values (7003, 1, 1006, 1012, 1101, '红外摄像仪3', 7, 7, 1, null, '', '', '', 0);

insert into t_mam_measobj (ID, TUNNEL_ID, STORE_ID, AREA_ID, SECTION_ID, NAME, DATATYPE_ID, OBJTYPE_ID, ACTIVED, DESCRIPTION, LONGITUDE, LATITUDE, HEIGHT, DEVIATION)
values (7004, 1, 1006, 1012, 1101, '红外摄像仪4', 7, 7, 1, null, '', '', '', 0);

commit;

--video
--t_mam_video
------------------------------------------------------------------------

insert into t_mam_video (ID, SERVER_ID, VIDEO_SCENE_ID, VIDEO_Extend_SCENE_ID, CHANNEL_NO)
values (7001, 1, 1, 0, 1);

insert into t_mam_video (ID, SERVER_ID, VIDEO_SCENE_ID, VIDEO_Extend_SCENE_ID, CHANNEL_NO)
values (7002, 1, 1, 0, 2);

insert into t_mam_video (ID, SERVER_ID, VIDEO_SCENE_ID, VIDEO_Extend_SCENE_ID, CHANNEL_NO)
values (7003, 1, 1, 0, 3);

insert into t_mam_video (ID, SERVER_ID, VIDEO_SCENE_ID, VIDEO_Extend_SCENE_ID, CHANNEL_NO)
values (7004, 1, 1, 0, 4);

--------------------------------------管廊内相机 ------------------------------------

--T_MAM_VIDEO_SCENE
insert into T_MAM_VIDEO_SCENE (ID, NAME, TUNNEL_ID, IS_LOOP, LOOP_INDEX)
values (1, '场景0', 1, 1, 1);


--T_MAM_VIDEO_Extend_SCENE
--insert into T_MAM_VIDEO_Extend_SCENE (ID, NAME, TUNNEL_ID, IS_LOOP, LOOP_INDEX)
--values (1, '附加场景1', 1, 1, 1);

--insert into T_MAM_VIDEO_Extend_SCENE (ID, NAME, TUNNEL_ID, IS_LOOP, LOOP_INDEX)
--values (2, '附加场景2', 1, 1, 2);

--insert into T_MAM_VIDEO_Extend_SCENE (ID, NAME, TUNNEL_ID, IS_LOOP, LOOP_INDEX)
--values (3, '附加场景3', 1, 1, 3);


commit;

--T_MAM_VIDEO_SERVER
insert into T_MAM_VIDEO_SERVER (ID, NAME, VENDOR, VENDOR_VERSION, IP, PORT, USERNAME, PASSWORD, CHANNEL_NUM)
values (1, 'h5streamServer1', 5, 1, '192.168.0.30', 8078, 'admin', '827ccb0eea8a706c4c34a16891f84e7b', 1000);
commit;


---------------------- 定位设备------------------------------------------------------------------------------------

insert into t_mam_measobj (ID, TUNNEL_ID, STORE_ID, AREA_ID, SECTION_ID, NAME, DATATYPE_ID, OBJTYPE_ID, ACTIVED, DESCRIPTION, LONGITUDE, LATITUDE, HEIGHT, DEVIATION)
values (20001, 0, 0, 0, 0, '定位设备1', 97, 20, 0, null, null, null, null, null);

insert into t_mam_measobj (ID, TUNNEL_ID, STORE_ID, AREA_ID, SECTION_ID, NAME, DATATYPE_ID, OBJTYPE_ID, ACTIVED, DESCRIPTION, LONGITUDE, LATITUDE, HEIGHT, DEVIATION)
values (20002, 0, 0, 0, 0, '定位设备2', 97, 20, 0, null, null, null, null, null);

insert into t_mam_measobj_so (ID, REFRESH_TIME, CV)
values (20001, to_date('19-07-2018 17:23:30', 'dd-mm-yyyy hh24:mi:ss'), '{}');

insert into t_mam_measobj_so (ID, REFRESH_TIME, CV)
values (20002, to_date('19-07-2018 17:23:30', 'dd-mm-yyyy hh24:mi:ss'), '{}');
commit;

------------------------------------------------------------------------------------------------------------------------------------------------------------
--maxview配置表
insert into T_MAM_MAXVIEW_CONFIG (ID, NAME, IP, PORT, USERNAME, PASSWORD, CRT_TIME)
values (1, '终端1', '192.168.6.14', '80', 'admin', '14e24dc8cf60446f56eb163a7b85bce8a53d3f766badf15e86a1af857d9a9012', to_date('15-11-2018', 'dd-mm-yyyy'));
commit;

