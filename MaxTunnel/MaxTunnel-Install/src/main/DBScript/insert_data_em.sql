--相关单位
insert into T_EM_RELATED_UNIT (id, name, contact, tel, unit_type, address, section_ids, longitude, latitude, crt_time)
values (2, '上海市第一人民医院', '王梦飞', '13702569817', 1, '徐汇区人民路001号', '[1],[2],[3],[4],[5]', '112.49326617606197', '37.70596780087469', to_date('09-07-2018 10:59:56', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_EM_RELATED_UNIT (id, name, contact, tel, unit_type, address, section_ids, longitude, latitude, crt_time)
values (3, '上海市浦东中学', '张大烛', '13702569017', 2, '浦东新区张恒路988号', '[1]', '112.49455482427953', '37.7070977914188', to_date('09-07-2018 13:13:44', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_EM_RELATED_UNIT (id, name, contact, tel, unit_type, address, section_ids, longitude, latitude, crt_time)
values (4, '上海市黄埔区人民政府', '李二亚', '18702569019', 3, '南京西路100号', '[1],[4]', '112.49451787909933', '37.70641769761464', to_date('09-07-2018 13:15:14', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_EM_RELATED_UNIT (id, name, contact, tel, unit_type, address, section_ids, longitude, latitude, crt_time)
values (5, '山姆大叔会员店', '大刘', '18702563019', 4, '锦绣路190号', '[2],[4]', '112.49367103150836', '37.70647176505491', to_date('09-07-2018 13:16:23', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_EM_RELATED_UNIT (id, name, contact, tel, unit_type, address, section_ids, longitude, latitude, crt_time)
values (6, '世纪公园', '大壮', '18702563039', 6, '上海市浦东新区向城路80号', '[1],[9]', '112.494090', '37.706188', to_date('09-07-2018 13:17:55', 'dd-mm-yyyy hh24:mi:ss'));

insert into T_EM_RELATED_UNIT (id, name, contact, tel, unit_type, address, section_ids, longitude, latitude, crt_time)
values (7, '浦东国际机场', '小壮', '(021)68347575', 6, '上海市浦东新区迎宾大道6000号', '[15]', '112.494082', '37.706135', to_date('09-07-2018 13:19:18', 'dd-mm-yyyy hh24:mi:ss'));
commit;

--应急预案表
insert into T_EM_PLAN (ID, PROCESS_KEY, TASK_KEY, TARGET_KEY, TARGET_VALUE, ACTION_KEY, ACTION_VALUE, FINISH_KEY, FINISH_VALUE, CRT_TIME, IS_FINISHED, TASK_NAME)
values (1, 'FirePlanProcess', 'usertask1', 2, 41, 3, 1, 1, 1, to_date('09-10-2018', 'dd-mm-yyyy'), 0, '开启声光报警');

insert into T_EM_PLAN (ID, PROCESS_KEY, TASK_KEY, TARGET_KEY, TARGET_VALUE, ACTION_KEY, ACTION_VALUE, FINISH_KEY, FINISH_VALUE, CRT_TIME, IS_FINISHED, TASK_NAME)
values (2, 'FirePlanProcess', 'usertask2', 1, 1, 1, 1, 1, 1, to_date('10-10-2018', 'dd-mm-yyyy'), 0, '调用摄像头');

insert into T_EM_PLAN (ID, PROCESS_KEY, TASK_KEY, TARGET_KEY, TARGET_VALUE, ACTION_KEY, ACTION_VALUE, FINISH_KEY, FINISH_VALUE, CRT_TIME, IS_FINISHED, TASK_NAME)
values (3, 'FirePlanProcess', 'usertask3', 1, 1, 1, 1, 2, 1, to_date('10-10-2018', 'dd-mm-yyyy'), 0, '值班人员确认');

insert into T_EM_PLAN (ID, PROCESS_KEY, TASK_KEY, TARGET_KEY, TARGET_VALUE, ACTION_KEY, ACTION_VALUE, FINISH_KEY, FINISH_VALUE, CRT_TIME, IS_FINISHED, TASK_NAME)
values (4, 'FirePlanProcess', 'usertask4', 1, 1, 1, 1, 1, 1, to_date('10-10-2018', 'dd-mm-yyyy'), 0, '打开风机');

insert into T_EM_PLAN (ID, PROCESS_KEY, TASK_KEY, TARGET_KEY, TARGET_VALUE, ACTION_KEY, ACTION_VALUE, FINISH_KEY, FINISH_VALUE, CRT_TIME, IS_FINISHED, TASK_NAME)
values (5, 'FirePlanProcess', 'usertask5', 1, 1, 1, 1, 1, 1, to_date('10-10-2018', 'dd-mm-yyyy'), 0, '打开风阀');

insert into T_EM_PLAN (ID, PROCESS_KEY, TASK_KEY, TARGET_KEY, TARGET_VALUE, ACTION_KEY, ACTION_VALUE, FINISH_KEY, FINISH_VALUE, CRT_TIME, IS_FINISHED, TASK_NAME)
values (6, 'FirePlanProcess', 'usertask6', 1, 1, 1, 1, 1, 1, to_date('10-10-2018', 'dd-mm-yyyy'), 0, '打开百叶');

insert into T_EM_PLAN (ID, PROCESS_KEY, TASK_KEY, TARGET_KEY, TARGET_VALUE, ACTION_KEY, ACTION_VALUE, FINISH_KEY, FINISH_VALUE, CRT_TIME, IS_FINISHED, TASK_NAME)
values (7, 'FirePlanProcess', 'usertask7', 1, 1, 1, 1, 1, 1, to_date('10-10-2018', 'dd-mm-yyyy'), 0, '启动干粉灭火');

insert into T_EM_PLAN (ID, PROCESS_KEY, TASK_KEY, TARGET_KEY, TARGET_VALUE, ACTION_KEY, ACTION_VALUE, FINISH_KEY, FINISH_VALUE, CRT_TIME, IS_FINISHED, TASK_NAME)
values (8, 'FirePlanProcess', 'usertask8', 1, 1, 1, 1, 1, 1, to_date('10-10-2018', 'dd-mm-yyyy'), 1, '通知相关单位');
commit;
