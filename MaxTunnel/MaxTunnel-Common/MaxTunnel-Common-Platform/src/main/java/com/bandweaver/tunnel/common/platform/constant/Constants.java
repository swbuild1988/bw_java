package com.bandweaver.tunnel.common.platform.constant;

public class Constants {

    /**
     * session中存放用户信息的key值
     */
    public static final String SESSION_USER_INFO = "userInfo";
    public static final String SESSION_USER_ROLE = "userRole";
    public static final String SESSION_USER_PERMISSION = "userPermission";
    
    //配置文件信息
    public static final String MAXTUNNEL_VERSION = "maxtunnel.version";
    public static final String MAXTUNNEL_EMAIL = "maxtunnel.email";
    public static final String MAXTUNNEL_CONTACT = "maxtunnel.contact";
    public static final String MAXTUNNEL_COMPANY = "maxtunnel.company";
    public static final String MAXTUNNEL_ADDRESS = "maxtunnel.address";
    public static final String INIT_PASSWORD = "maxtunnel.cm.init.password";
    
    //环境监测国标值
    public static final String TEMPERATURE_MIN = "ai.temperature.min";
    public static final String TEMPERATURE_MAX = "ai.temperature.max";
    public static final String HUMIDITY_MIN = "ai.humidity.min";
    public static final String HUMIDITY_MAX = "ai.humidity.max";
    public static final String OXYGEN_MIN = "ai.oxygen.min";
    public static final String OXYGEN_MAX = "ai.oxygen.max";
    public static final String H2S_MIN = "ai.h2s.min";
    public static final String H2S_MAX = "ai.h2s.max";
    public static final String CH4_MIN = "ai.ch4.min";
    public static final String CH4_MAX = "ai.ch4.max";
    public static final String CO_MIN = "ai.co.min";
    public static final String CO_MAX = "ai.co.max";
    
    //文件管理上传路径
    public static final String FILE_PATH = "path.file.upload";
    
    //MQ队列
    public static final String QUEUE_ALARM_UM = "mq.queue.alarm.um";
    public static final String QUEUE_ALARM_VM = "mq.queue.alarm.vm";
    
    public static final String QUEUE_PLAN_UM = "mq.queue.plan.um";
    public static final String QUEUE_PLAN_VM = "mq.queue.plan.vm";

    
    //线程
    public static final int THREAD_POOL_SIZE = 5;
    
    //访问设置超时时间
    public static final String INIT_TIMEOUT = "init.timeout";

    //excel导出相关参数
    public static final String EXCEL_EXPORT_PATH = "excel.export.path";

    //#h5服务拍照的存储位置
    public static final String PATH_SNAP_H5 = "path.snap.h5";

    public static final String SHIRO_PERMISSION_KEY = "permissionSet";



    // 应急预案常量
    public final static String EMPLAN_NEXT_TASK_VALUE_KEY = "next_task_value";
    public final static String EMPLAN_OBJ_KEY = "emPlan";
    public final static String EMPLAN_OBJ_LIST_KEY = "measobj_list";
    public final static String EMPLAN_PROCESSINSTANCE_ID = "processInstanceId";






}
