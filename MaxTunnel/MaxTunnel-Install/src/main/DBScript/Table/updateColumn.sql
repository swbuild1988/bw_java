
-- ���Ա�T_TEST

-- ����ֶ�
-- alter table T_TEST add age number(3) default 18;

-- section����ӳ���length�ֶ�
alter table T_COMMON_SECTION add length number default 300;

-- store����ӿ�width�͸�height�ֶ�
alter table T_COMMON_STORE add width NUMBER default 2;
alter table T_COMMON_STORE add height NUMBER default 4;

-- staff������ֶ�
alter table T_COMMON_STAFF add account VARCHAR2(50);
alter table T_COMMON_STAFF add sex VARCHAR2(2);
alter table T_COMMON_STAFF add telphone VARCHAR2(20);
alter table T_COMMON_STAFF add hire_date DATE;


-- video�����vendor,ip,port,username,password����ֶ�
alter table T_MAM_VIDEO add vendor NUMBER;
alter table T_MAM_VIDEO add ip VARCHAR2(20);
alter table T_MAM_VIDEO add port NUMBER;
alter table T_MAM_VIDEO add username VARCHAR2(50);
alter table T_MAM_VIDEO add password VARCHAR2(50);

-- staff������ֶ�
--����� ���֤��
alter table T_COMMON_STAFF add identityNO VARCHAR2(50);
--����� ������Ա��ʶ
alter table T_COMMON_STAFF add outside number(1) default 1;

-- �޸��ֶ�
-- alter table T_TEST rename column age to ages;

-- ɾ���ֶ�
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

----------------------------------------------------
