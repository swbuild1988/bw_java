package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionRecordDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTask;

import java.io.Serializable;
import java.util.List;

public class InspectionTaskDto extends InspectionTask implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6746258736612487926L;

	/**
	 * 巡检任务对应的管廊id
	 */
	private Integer tunnelId;
    /**
     * 所属计划名
     */
    private String planName;
    /**
     * 巡检人名
     */
    private String inspectManName;
    /**
     * 记录
     */
    private List<InspectionRecordDto> records;

    public Integer getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}

	public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getInspectManName() {
        return inspectManName;
    }

    public void setInspectManName(String inspectManName) {
        this.inspectManName = inspectManName;
    }

    public List<InspectionRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<InspectionRecordDto> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "InspectionTaskDto{" +
                "planName='" + planName + '\'' +
                ", inspectManName='" + inspectManName + '\'' +
                ", records=" + records +
                "} " + super.toString();
    }
}
