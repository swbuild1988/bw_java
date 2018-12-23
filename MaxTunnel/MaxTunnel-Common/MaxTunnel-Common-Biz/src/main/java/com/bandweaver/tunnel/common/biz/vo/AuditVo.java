package com.bandweaver.tunnel.common.biz.vo;

/**
 * 供提交activiti结果用
 */
public class AuditVo {

    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 结果
     */
    private int value;
    /**
     * 批注
     */
    private String remark;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AuditVo{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
