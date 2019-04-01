package com.bandweaver.tunnel.common.biz.constant;

/**
 * 定时任务类型枚举
 * @author sen.shao
 */
public enum  JobType {

    /**
     * 周报导出
     */
    JOB_TYPE_1(1, "周报导出", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "weekExport"),
    /**
     * 月报导出
     */
    JOB_TYPE_2(2, "月报导出", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "monthExport"),
    /**
     * 模拟量上个小时最大最小平均值
     */
    JOB_TYPE_3(3, "模拟量上个小时最大最小平均值", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "reportMeasObjHour"),
    /**
     * 模拟量昨天最大最小平均值
     */
    JOB_TYPE_4(4, "模拟量昨天最大最小平均值", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "reportMeasObjDay"),
    /**
     * 模拟量上周最大最小平均值
     */
    JOB_TYPE_5(5, "模拟量上周最大最小平均值", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "reportMeasObjWeek"),
    /**
     * 模拟量上月最大最小平均值
     */
    JOB_TYPE_6(6, "模拟量上月最大最小平均值", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "reportMeasObjMonth"),
    /**
     * 模拟量上年最大最小平均值
     */
    JOB_TYPE_7(7, "模拟量上年最大最小平均值", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "reportMeasObjYear"),
    /**
     * 监测对象数据定时保存
     */
    JOB_TYPE_8(8, "监测对象数据定时保存", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "saveDataScheduleBatch"),

    /**
     * 监测对象数据定时删除
     */
    JOB_TYPE_9(9, "监测对象数据定时删除", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "deleteDataSchedule"),

    /**
     * 能耗保存
     */
    JOB_TYPE_10(10, "能耗保存", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "saveToConsumeData"),

    /**
     * 发送心跳
     */
    JOB_TYPE_11(11, "发送心跳", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "heartBeat"),

    /**
     * 模拟maxview发送数据
     */
    JOB_TYPE_12(12, "模拟maxview发送数据", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "sendTestData"),

    /**
     * 模拟maxview发送告警
     */
    JOB_TYPE_13(13, "模拟maxview发送告警", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "sendTestAlarm"),

    /**
     * 管廊运行时间定时自增
     */
    JOB_TYPE_14(14, "管廊运行时间定时自增", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "runDaysAutoAdded"),

	/**
     * 录像视频定时拍照
     */
    JOB_TYPE_15(15, "录像视频定时拍照", "com.bandweaver.tunnel.controller.quartz.TaskEntrance", "addSnaps");


    private int value;

    private String name;

    private String jobClass;

    private String jobMethod;


    JobType(int value, String name, String jobClass, String jobMethod) {
        this.value = value;
        this.name = name;
        this.jobClass = jobClass;
        this.jobMethod = jobMethod;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public void setJobMethod(String jobMethod) {
        this.jobMethod = jobMethod;
    }


    public static JobType getEnum(int value) {
        JobType[] values = JobType.values();
        for (JobType e : values) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}
