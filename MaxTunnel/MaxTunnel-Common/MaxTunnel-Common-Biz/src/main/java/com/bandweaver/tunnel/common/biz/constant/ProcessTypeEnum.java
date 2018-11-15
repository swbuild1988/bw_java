package com.bandweaver.tunnel.common.biz.constant;

/**
 * ClassName: ProcessTypeEnum
 *
 * @author shaosen
 * @Description: 流程类型枚举
 * @date 2018年7月18日
 */
public enum ProcessTypeEnum {
    NONE("其他", 0, "none", null, null, null, null),
    INPECTION_PLAN("巡检计划申请", 1001, "InspectionPlan", "path.bpmn.activiti.inspection", "path.png.activiti.inspection", null, null),
    INPECTION_TASK("巡检任务", 1002, "InspectionTask", "path.bpmn.activiti.inspectionTask", "path.png.activiti.inspectionTask", null, null),
    MAINTENANCE_ORDER("维修工单", 1003, "MaintenanceOrder", "path.bpmn.activiti.maintenanceOrder", "path.png.activiti.maintenanceOrder", null, null),

    REQ_HISTORY("入廊申请", 2001, "reqHistory", "path.bpmn.activiti.reqhistory", "path.png.activiti.reqhistory", null, null),

    //应急预案
	FIRE_PLAN("消防预案", 4001, "FirePlanProcess", "path.bpmn.activiti.fire", "path.png.activiti.fire","mq.queue.plan.fire",50.0),
	WIND_PLAN("通风预案", 4003, "WindPlanProcess", "path.bpmn.activiti.wind", "path.png.activiti.wind","",55.5),
	WATER_PLAN("排水预案", 4002, "WaterPlanProcess", "path.bpmn.activiti.water", "path.png.activiti.water","",55.1);
	
	
	/**预案数组 
	 * @return   
	 * @author shaosen
	 * @Date 2018年10月16日
	 */
	public static ProcessTypeEnum[] getProcessTypeList(){
        return new ProcessTypeEnum[]{
        		FIRE_PLAN,
        		WIND_PLAN,
        		WATER_PLAN
        };
    }

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

	ProcessTypeEnum(String name, int value, String processKey, String bpmnPath, String pngPath,String queue,Double range) {
        this.name = name;
        this.value = value;
        this.processKey = processKey;
        this.bpmnPath = bpmnPath;
        this.pngPath = pngPath;
        this.queue = queue;
        this.range = range;
    }

    
    /**根据value值获取enum
     * @param   value
     * @return ActionEnum
     * @author shaosen
     * @date 2018年6月23日
     */
    public static ProcessTypeEnum getEnum(int value) {
        for (ProcessTypeEnum actionEnum : ProcessTypeEnum.values()) {
            if (value == actionEnum.getValue())
                return actionEnum;
        }
        return ProcessTypeEnum.NONE;
    }

    /**
     *
     * @param processKey
     * @return
     */
    public static ProcessTypeEnum getEnum(String processKey) {
        for (ProcessTypeEnum actionEnum : ProcessTypeEnum.values()) {
            if (processKey.toLowerCase().equals(actionEnum.getProcessKey().toLowerCase()))
                return actionEnum;
        }
        return ProcessTypeEnum.NONE;
    }

}
