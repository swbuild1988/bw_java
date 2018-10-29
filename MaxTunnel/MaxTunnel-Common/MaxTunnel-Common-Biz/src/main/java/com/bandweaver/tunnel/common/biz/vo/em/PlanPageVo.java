package com.bandweaver.tunnel.common.biz.vo.em;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

/**
 * 所有应急预案管理接收参数vo
 * 
 * @author shaosen
 * @date 2018年8月9日
 */
public class PlanPageVo extends BaseVo {

	/**流程定义key*/
	private String processKey;
	/**流程实例id*/
	private String ProcessInstanceId;

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getProcessInstanceId() {
		return ProcessInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		ProcessInstanceId = processInstanceId;
	}

}
