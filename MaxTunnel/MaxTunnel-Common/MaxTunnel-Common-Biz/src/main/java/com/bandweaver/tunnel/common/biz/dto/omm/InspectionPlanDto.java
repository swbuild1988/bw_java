package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.constant.omm.InspectionObject;
import com.bandweaver.tunnel.common.biz.constant.omm.InspectionPath;
import com.bandweaver.tunnel.common.biz.constant.omm.InspectionWay;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionStep;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InspectionPlanDto extends InspectionPlan implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7474181577055298723L;
	private String tunnelName;
	private String groupName;
    private InspectionGroupDto inspectionGroup;
    private List<InspectionTaskDto> tasks;
    
    private Staff requestStaff;
    private Staff approver;
    
    private String comment;
    private String result;
    
    private String inspectionWayName; // 巡检方式
    private String inspectionObjectName; // 巡检对象
    private String inspectionPathName; // 巡检路径
    
    private List<InspectionStep> steps;

    public InspectionPlanDto() {
        super();
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public InspectionGroupDto getInspectionGroup() {
        return inspectionGroup;
    }

    public void setInspectionGroup(InspectionGroupDto inspectionGroup) {
        this.inspectionGroup = inspectionGroup;
    }

    public List<InspectionTaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<InspectionTaskDto> tasks) {
        this.tasks = tasks;
    }

    public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getInspectionWayName() {
		InspectionWay way = InspectionWay.getEnum(super.getInspectionWay());
		return way == null ? null : way.getName();
	}

	public String getInspectionObjectName() {
		InspectionObject obj = InspectionObject.getEnum(super.getInspectionObject());
		return obj == null ? null : obj.getName();
	}

	public String getInspectionPathName() {
		InspectionPath path = InspectionPath.getEnum(super.getInspectionPath());
		return path == null ? null : path.getName();
	}

	public List<InspectionStep> getSteps() {
		return steps;
	}

	public void setSteps(List<InspectionStep> steps) {
		this.steps = steps;
	}

	public Staff getRequestStaff() {
		return requestStaff;
	}

	public void setRequestStaff(Staff requestStaff) {
		this.requestStaff = requestStaff;
	}

	public Staff getApprover() {
		return approver;
	}

	public void setApprover(Staff approver) {
		this.approver = approver;
	}

	/**
     * 将其转为原型
     * @return
     */
    public InspectionPlan toInspectionPlan(){
        return (InspectionPlan)this;
    }

    /**
     * 提取巡检任务的原型列表
     * @return
     */
    public List<InspectionTask> inspectionTasks(){
        List<InspectionTask> inspectionTasks = new ArrayList<>();

        for (InspectionTaskDto task : tasks) {
            InspectionTask inspectionTask = new InspectionTask();
            inspectionTask.setId(task.getId());
            inspectionTask.setInspectManId(task.getInspectManId());
            inspectionTask.setInspectTime(task.getInspectTime());
            inspectionTask.setPlanId(this.getPlanId());
            inspectionTask.setProcessStatus(task.getProcessStatus());
            inspectionTask.setTaskTime(task.getTaskTime());
            inspectionTasks.add(inspectionTask);
        }

        return inspectionTasks;
    }

	@Override
    public String toString() {
        return "InspectionPlanDto{" +
                "tunnelName='" + tunnelName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", inspectionGroup=" + inspectionGroup +
                ", inspectionWayName=" + inspectionWayName +
                ", inspectionObjectName=" + inspectionObjectName +
                ", inspectionPathName=" + inspectionPathName +
                ", steps=" + steps +
                ", tasks=" + tasks +
                ", requestStaff=" + requestStaff +
                ", approver=" + approver +
                "} " + super.toString();
    }
}
