
-- 测试表T_TEST

-- 添加字段
-- alter table T_TEST add age number(3) default 18;

-- section表添加长度length字段
alter table T_COMMON_SECTION add length number default 300;

-- store表添加宽width和高height字段
alter table T_COMMON_STORE add width NUMBER default 2;
alter table T_COMMON_STORE add height NUMBER default 4;

-- staff表添加字段
alter table T_COMMON_STAFF add account VARCHAR2(50);
alter table T_COMMON_STAFF add sex VARCHAR2(2);
alter table T_COMMON_STAFF add telphone VARCHAR2(20);
alter table T_COMMON_STAFF add hire_date DATE;


-- video表添加vendor,ip,port,username,password五个字段
alter table T_MAM_VIDEO add vendor NUMBER;
alter table T_MAM_VIDEO add ip VARCHAR2(20);
alter table T_MAM_VIDEO add port NUMBER;
alter table T_MAM_VIDEO add username VARCHAR2(50);
alter table T_MAM_VIDEO add password VARCHAR2(50);

-- staff表添加字段
--添加列 身份证号
alter table T_COMMON_STAFF add identityNO VARCHAR2(50);
--添加列 外来人员标识
alter table T_COMMON_STAFF add outside number(1) default 1;

-- 修改字段
-- alter table T_TEST rename column age to ages;

-- 删除字段
-- alter table T_TEST drop column ages;

--------------- V110B02 ----------------------------

alter table T_COMMON_TUNNEL add START_POINT VARCHAR2(50);
alter table T_COMMON_TUNNEL add END_POINT VARCHAR2(50);
alter table T_COMMON_TUNNEL add DIRECTION NUMBER default 0;

alter table T_COMMON_STORE add START_POINT VARCHAR2(50);
alter table T_COMMON_STORE add END_POINT VARCHAR2(50);
alter table T_COMMON_STORE add K NUMBER default 0.0;
alter table T_COMMON_STORE add L NUMBER default 0.0;

alter table T_COMMON_AREA add START_POINT VARCHAR2(50);
alter table T_COMMON_AREA add END_POINT VARCHAR2(50);
alter table T_COMMON_AREA add LENGTH NUMBER default 0;

alter table T_COMMON_SECTION add S1 NUMBER default 0;
alter table T_COMMON_SECTION add S2 NUMBER default 0;

alter table t_mam_measobj add video_ids varchar2(100);
alter table t_mam_measobj add plan_ids varchar2(100);

alter table T_COMMON_SCHEDULE_JOB add job_type number;

------------------V110B03----------------------------

alter table T_OMM_EQUIPMENT add section_id NUMBER;
alter table T_OMM_EQUIPMENT add qa_term VARCHAR2(20);
alter table T_OMM_EQUIPMENT add rated_voltage VARCHAR2(20);
alter table T_OMM_EQUIPMENT add range VARCHAR2(20);
alter table T_OMM_EQUIPMENT add factory VARCHAR2(20);
alter table T_OMM_EQUIPMENT add brand VARCHAR2(20);

alter table T_OMM_SPARE add qa_term VARCHAR2(20);
alter table T_OMM_SPARE add rated_voltage VARCHAR2(20);
alter table T_OMM_SPARE add range VARCHAR2(20);
alter table T_OMM_SPARE add factory VARCHAR2(20);
alter table T_OMM_SPARE add brand VARCHAR2(20);

alter table T_COMMON_COMPANY add credit_no VARCHAR2(20);
alter table T_COMMON_COMPANY add bank NUMBER;
alter table T_COMMON_COMPANY add account VARCHAR2(20);
alter table T_COMMON_COMPANY add address VARCHAR2(100);
alter table T_COMMON_COMPANY add phone VARCHAR2(20);
alter table T_COMMON_COMPANY add mail VARCHAR2(30);

alter table T_COMMON_COMPANY add inspection_no VARCHAR2(20);
alter table T_COMMON_COMPANY add inspection_time VARCHAR2(20);
alter table T_COMMON_COMPANY add inspection_info VARCHAR2(100);
alter table T_COMMON_COMPANY add img_url VARCHAR2(200);

alter table T_OAM_CUSTOMER rename column contact2 to duty;
alter table T_OAM_CUSTOMER rename column contact3 to mail;
alter table T_OAM_CUSTOMER rename column tel3 to type;


------------------------V110----------------------------
alter table T_OAM_CABLE_CONTRACT rename column customer_id to company_id;

alter table T_SECURITY_ROLE add rout_list varchar2(2000);
alter table T_SECURITY_PERMISSION modify menu_code null;
alter table T_SECURITY_PERMISSION modify menu_name null;
alter table T_SECURITY_PERMISSION modify permission_name null;

alter table T_OAM_CABLE_CONTRACT add operate_username varchar2(50);
alter table T_OAM_CABLE_CONTRACT add path varchar2(200);
