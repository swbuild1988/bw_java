
-- �޸������� ͬ����ת��params [VARCHAR2(200 CHAR) -> VARCHAR2(500 CHAR)]
-- ��ֱ��ִ�� alter table �� modify(���� VARCHAR2(500 CHAR));

alter table T_OPERATION_LOG modify(params VARCHAR2(500 CHAR));
alter table T_OPERATION_LOG modify(description VARCHAR2(500 CHAR));

-- staff���޸�
--�����ſ�Ϊ��
alter table T_COMMON_STAFF modify DEPT_ID null;
--����ְλ��Ϊ��
alter table T_COMMON_STAFF modify POSITION_ID null;
--��������Ϊ��
alter table T_COMMON_STAFF modify name not null;

-- �޸������� ��ͬ����ת��card(number -> varchar2)
-- ʹ�ô��������滻���еķ�����������ʾ

-- ����������
-- alter table T_TEST rename column card to temp;

-- ��������
-- alter table T_TEST add card varchar2(50);

-- ���и�ֵ
-- update T_TEST set card = temp;
-- commit;

-- ɾ������
-- alter table T_TEST drop column temp;