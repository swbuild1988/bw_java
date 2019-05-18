package com.bandweaver.tunnel.common.biz.constant;

/**
 * ClassName: ProcessTypeEnum
 *
 * @author shaosen
 * @Description: 流程类型枚举
 * @date 2018年7月18日
 */
public enum ProcessTypeEnum {
    /**
     * 其他
     */
    NONE("其他", 0, "none", null, null, null, null),
    /**
     * 巡检计划申请
     */
    INPECTION_PLAN("巡检计划申请", 1001, "InspectionPlan", "path.bpmn.activiti.inspection", "path.png.activiti.inspection", null, null),
    /**
     * 巡检任务
     */
    INPECTION_TASK("巡检任务", 1002, "InspectionTask", "path.bpmn.activiti.inspectionTask", "path.png.activiti.inspectionTask", null, null),
    /**
     * 维修工单
     */
    MAINTENANCE_ORDER("维修工单", 1003, "MaintenanceOrder", "path.bpmn.activiti.maintenanceOrder", "path.png.activiti.maintenanceOrder", null, null),
    /**
     * 入廊申请
     */
    REQ_HISTORY("入廊申请", 2001, "reqHistory", "path.bpmn.activiti.reqhistory", "path.png.activiti.reqhistory", null, null),
    /**
     * 消防预案
     */
    FIRE_PLAN("消防预案", 4001, "FirePlanProcess", "path.bpmn.activiti.fire", "path.png.activiti.fire", "", 50.0),
    /**
     * 排水预案
     */
    WATER_PLAN("排水预案", 4002, "WaterPlanProcess", "path.bpmn.activiti.water", "path.png.activiti.water", "", 55.1),
    /**
     * 通风预案
     */
    WIND_PLAN("通风预案", 4003, "WindPlanProcess", "path.bpmn.activiti.wind", "path.png.activiti.wind", "", 55.5),
    /**
     * 温湿度SOP
     */
    TEMPERATURE_HUMIDITY_ALARM_SOP("温湿度SOP", 4004, "TemperatureAndHumidityAlarmSopProcess", "path.bpmn.activiti.sop", "path.png.activiti.sop", "", null);


    private String name;
    private int value;
    private String processKey;
    private String bpmnPath;
    private String pngPath;
    private String queue;
    private Double range;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getBpmnPath() {
        return bpmnPath;
    }

    public void setBpmnPath(String bpmnPath) {
        this.bpmnPath = bpmnPath;
    }

    public String getPngPath() {
        return pngPath;
    }

    public void setPngPath(String pngPath) {
        this.pngPath = pngPath;
    }


    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }


    public Double getRange() {
        return range;
    }

    public void setRange(Double range) {
        this.range = range;
    }

    ProcessTypeEnum(String name, int value, String processKey, String bpmnPath, String pngPath, String queue, Double range) {
        this.name = name;
        this.value = value;
        this.processKey = processKey;
        this.bpmnPath = bpmnPath;
        this.pngPath = pngPath;
        this.queue = queue;
        this.range = range;
    }


    public static ProcessTypeEnum[] getEmPlanList() {
        return new ProcessTypeEnum[]{
                FIRE_PLAN,
                WATER_PLAN,
                WIND_PLAN
        };
    }


    public static ProcessTypeEnum getEnum(int value) {
        for (ProcessTypeEnum actionEnum : ProcessTypeEnum.values()) {
            if (value == actionEnum.getValue()) {
                return actionEnum;

            }
        }
        return ProcessTypeEnum.NONE;
    }

    public static ProcessTypeEnum getEnum(String processKey) {
        for (ProcessTypeEnum actionEnum : ProcessTypeEnum.values()) {
            if (processKey.toLowerCase().equals(actionEnum.getProcessKey().toLowerCase())) {
                return actionEnum;

            }
        }
        return ProcessTypeEnum.NONE;
    }

}
