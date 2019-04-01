
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

----------------------------------------------------