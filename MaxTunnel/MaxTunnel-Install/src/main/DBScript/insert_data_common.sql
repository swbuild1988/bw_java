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
values (1000, 'job8', 'group1', 1, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'saveToConsumeData', '0 0 1 * * ?', '定时任务统计每天的能耗并保存到ConsumeData表中');

insert into T_COMMON_SCHEDULE_JOB (JOB_ID, JOB_NAME, JOB_GROUP, JOB_STATUS, JOB_CLASS, JOB_METHOD, CRON_EXPRESSION, DESCRIPTION)
values (1001, 'job9', 'group1', 0, 'com.bandweaver.tunnel.controller.quartz.TaskEntrance', 'heartBeat', '0/5 * * * * ?', '登录maxview终端之后，持续发送心跳 ');

commit;
