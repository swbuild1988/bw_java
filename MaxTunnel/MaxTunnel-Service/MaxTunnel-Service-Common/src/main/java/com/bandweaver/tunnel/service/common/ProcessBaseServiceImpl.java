package com.bandweaver.tunnel.service.common;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanDto;
import com.bandweaver.tunnel.common.biz.itf.ProcessBaseService;
import com.bandweaver.tunnel.common.biz.itf.oam.ReqHistoryService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionPlanService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionTaskService;
import com.bandweaver.tunnel.common.biz.itf.omm.MaintenanceOrderService;
import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqHistory;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTask;
import com.bandweaver.tunnel.common.biz.pojo.omm.MaintenanceOrder;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;


import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.apache.ibatis.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessBaseServiceImpl implements ProcessBaseService {
    @Autowired
    private InspectionTaskService inspectionTaskService;
    @Autowired
    private InspectionPlanService inspectionPlanService;
    @Autowired
    private MaintenanceOrderService maintenanceOrderService;
    @Autowired
    private ReqHistoryService reqHistoryService;

    @Override
    public ProcessBase getProcessBaseByTypeAndProcessInstance(ProcessTypeEnum typeEnum, String processInstanceId) {
        ProcessBase processBase = null;

        LogUtil.info("类似：" + typeEnum + " processInstanceId:" + processInstanceId);
        switch (typeEnum) {
            case INPECTION_PLAN:
                    processBase = (ProcessBase)inspectionPlanService.getInspectionPlanByProcessInstanceId(processInstanceId);
                break;

            case INPECTION_TASK:
                    processBase = (ProcessBase)inspectionTaskService.getTaskByProcessInstance(processInstanceId);
                break;

            case MAINTENANCE_ORDER:
                    processBase = (ProcessBase)maintenanceOrderService.getOrderByInstance(processInstanceId);
                break;

            case REQ_HISTORY:
                    processBase = (ProcessBase)reqHistoryService.getReqHistoryByProcessInstanceId(processInstanceId);
                break;

            default:

                break;
        }

        return processBase;
    }

    @Override
    public Object getProcessByTypeAndProcessInstance(ProcessTypeEnum typeEnum, String processInstanceId) {
        switch (typeEnum) {
            case INPECTION_PLAN:
                return inspectionPlanService.getInspectionDtoByProcessInstanceId(processInstanceId);

            case INPECTION_TASK:
                return inspectionTaskService.getTaskByProcessInstance(processInstanceId);

            case MAINTENANCE_ORDER:
                return maintenanceOrderService.getOrderByInstance(processInstanceId);

            case REQ_HISTORY:
                return reqHistoryService.getReqHistoryDtoByProcessInstanceId(processInstanceId);

            default:

                break;
        }
        return null;
    }

    @Override
    public void updateProcessStatus(ProcessTypeEnum typeEnum, String processInstanceId, String processStatus) {
        switch (typeEnum) {
            case INPECTION_PLAN:
                LogUtil.info("get inspectionPlan by processInstanceId: " + processInstanceId);
                InspectionPlan inspectionPlan = inspectionPlanService.getInspectionPlanByProcessInstanceId(processInstanceId);
                LogUtil.info("get inpsectionPlan : " + inspectionPlan);
                if (inspectionPlan == null || inspectionPlan.getId() == null) return;
                inspectionPlan.setProcessStatus(processStatus);
                inspectionPlanService.updateBySelective(inspectionPlan);
                break;

            case INPECTION_TASK:
                LogUtil.info("准备获取任务");
                InspectionTask inspectionTask = inspectionTaskService.getTaskByProcessInstance(processInstanceId);
                LogUtil.info("get inspectionTask: " + inspectionTask);
                if (inspectionTask == null || inspectionTask.getId() == null) return;
                inspectionTask.setProcessStatus(processStatus);
                inspectionTaskService.update(inspectionTask);
                break;

            case MAINTENANCE_ORDER:
                MaintenanceOrder maintenanceOrder = maintenanceOrderService.getOrderByInstance(processInstanceId);
                if (maintenanceOrder == null || maintenanceOrder.getId() == null) return;
                maintenanceOrder.setProcessStatus(processStatus);
                maintenanceOrderService.update(maintenanceOrder);
                break;

            case REQ_HISTORY:
                LogUtil.info("get req-history by processInstanceId: " + processInstanceId);
                ReqHistory history = reqHistoryService.getReqHistoryByProcessInstanceId(processInstanceId);
                if(history == null) {
                	 history = (ReqHistory) ContextUtil.getSession().getAttribute(typeEnum.getName());
                	 LogUtil.info(" get history from session : " + history);
                	 history.setProcessStatus(processStatus);
                	 history.setProcessInstanceId(processInstanceId);
                	 history.setProcessType(typeEnum.getValue());
                	 LogUtil.info(" set history to session : " + history);
                	 ContextUtil.getSession().setAttribute(typeEnum.getName(), history);
                	 return;
                }
                LogUtil.info("get history from DB : " + history);
                history.setProcessStatus(processStatus);
                reqHistoryService.updateById(history);
                break;

            default:

                break;
        }
    }

    @Override
    public void finishProcess(ProcessTypeEnum typeEnum, String processInstanceId) {
        switch (typeEnum) {
            case INPECTION_PLAN:
                LogUtil.info("get inspectionPlan by processInstanceId: " + processInstanceId);
                InspectionPlan inspectionPlan = inspectionPlanService.getInspectionPlanByProcessInstanceId(processInstanceId);
                LogUtil.info("get inpsectionPlan : " + inspectionPlan);
                if (inspectionPlan == null || inspectionPlan.getId() == null) return;
                inspectionPlan.setProcessStatus("流程结束");
                inspectionPlan.setIsFinished(true);
                inspectionPlanService.updateBySelective(inspectionPlan);
                break;

            case INPECTION_TASK:
                InspectionTask inspectionTask = inspectionTaskService.getTaskByProcessInstance(processInstanceId);
                if (inspectionTask == null || inspectionTask.getId() == null) return;
                inspectionTask.setProcessStatus("流程结束");
                inspectionTask.setIsFinished(true);
                inspectionTaskService.update(inspectionTask);
                break;

            case MAINTENANCE_ORDER:
                MaintenanceOrder maintenanceOrder = maintenanceOrderService.getOrderByInstance(processInstanceId);
                if (maintenanceOrder == null || maintenanceOrder.getId() == null) return;
                maintenanceOrder.setProcessStatus("流程结束");
                maintenanceOrder.setIsFinished(true);
                maintenanceOrderService.update(maintenanceOrder);
                break;

            case REQ_HISTORY:
                ReqHistory history = reqHistoryService.getReqHistoryByProcessInstanceId(processInstanceId);
                assertNotNull(history);
                history.setProcessStatus("流程结束");
                history.setIsFinished(true);
                reqHistoryService.updateById(history);
                break;

            default:

                break;
        }
    }
}
