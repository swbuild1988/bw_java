
-- 测试表T_TEST

-- 添加字段
-- alter table T_TEST add age number(3) default 18;

-- section表添加长度length字段
alter table T_COMMON_SECTION add length number default 300;

-- store表添加宽width和高height字段
alter table T_COMMON_STORE add width NUMBER default 1;
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

-- 修改字段
-- alter table T_TEST rename column age to ages;

-- 删除字段
-- alter table T_TEST drop column ages;