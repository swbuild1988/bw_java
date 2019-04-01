
-- 修改列类型 同类型转换params [VARCHAR2(200 CHAR) -> VARCHAR2(500 CHAR)]
-- 可直接执行 alter table 表 modify(列名 VARCHAR2(500 CHAR));

alter table T_OPERATION_LOG modify(params VARCHAR2(500 CHAR));
alter table T_OPERATION_LOG modify(description VARCHAR2(500 CHAR));

-- staff表修改
--所属部门可为空
alter table T_COMMON_STAFF modify DEPT_ID null;
--所属职位可为空
alter table T_COMMON_STAFF modify POSITION_ID null;
--姓名不可为空
alter table T_COMMON_STAFF modify name not null;

-- 修改列类型 不同类型转换card(number -> varchar2)
-- 使用创建新列替换旧列的方法，如下所示

-- 重命名旧列
-- alter table T_TEST rename column card to temp;

-- 创建新列
-- alter table T_TEST add card varchar2(50);

-- 新列赋值
-- update T_TEST set card = temp;
-- commit;

-- 删除旧列
-- alter table T_TEST drop column temp;