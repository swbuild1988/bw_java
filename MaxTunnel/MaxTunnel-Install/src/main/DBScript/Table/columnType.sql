
-- 修改列类型card(number -> varchar2)

-- 重命名旧列
-- alter table T_TEST rename column card to temp;

-- 创建新列
-- alter table T_TEST add card varchar2(50);

-- 新列赋值
-- update T_TEST set card = temp;
-- commit;

-- 删除旧列
-- alter table T_TEST drop column temp;