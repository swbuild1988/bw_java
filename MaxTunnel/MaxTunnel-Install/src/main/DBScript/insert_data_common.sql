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


--section


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
values (1002, 'job10', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'sendTestData', '0/15 * * * * ?', '模拟maxview发送数据');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1003, 'job11', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'sendTestAlarm', '0/30 * * * * ?', '模拟maxview发送告警');
commit;
