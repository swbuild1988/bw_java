package com.bandweaver.tunnel.service.omm.inspection.activiti;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.bandweaver.tunnel.common.biz.itf.omm.InspectionPlanService;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.SpringContextHolder;
public class InspectionPlanApproveListener implements TaskListener {
	
	@Resource(name="inspectionPlanService")
    private InspectionPlanService inspectionPlanService;

    @Override
    public void notify(DelegateTask delegateTask) {
//    	if(null == inspectionPlanService) {
//    		inspectionPlanService = SpringContextHolder.getBean("inspectionPlanService");
//    	}
//
//        String processDefinitionId = delegateTask.getProcessDefinitionId();
//        InspectionPlan plan = inspectionPlanService.getProcessDefinitionId(processDefinitionId);
//
//        delegateTask.setAssignee(String.valueOf(plan.getApproverId()));
//        LogUtil.info("任务" + delegateTask.getId() + "已将审批人赋值为：" + plan.getApproverId());
    }
}
