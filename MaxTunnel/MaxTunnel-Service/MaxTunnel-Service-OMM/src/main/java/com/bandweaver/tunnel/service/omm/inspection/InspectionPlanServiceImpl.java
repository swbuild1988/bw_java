package com.bandweaver.tunnel.service.omm.inspection;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTaskDto;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionGroupService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionTaskService;
import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.constant.ProcessStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanSimpleDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionPlanService;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTask;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.omm.InspectionPlanMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class InspectionPlanServiceImpl implements InspectionPlanService {
    @Autowired
    private InspectionPlanMapper inspectionPlanMapper;
    @Autowired
    private InspectionTaskService inspectionTaskService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private InspectionGroupService inspectionGroupService;

    @Override
    @Transactional
    public int add(InspectionPlanDto inspectionPlanDto) {

        LogUtil.info("inspectionPlanDto:" + inspectionPlanDto);
        // 设置流程实例id
        inspectionPlanDto.setProcessInstanceId(activitiService.startProcessInstance(ProcessTypeEnum.INPECTION_PLAN, inspectionPlanDto.getRequestStaffId(), inspectionPlanDto.getApproverId()));
        // 先加入计划
        InspectionPlan inspectionPlan = (InspectionPlan) inspectionPlanDto;
        inspectionPlan.setProcessType(ProcessTypeEnum.INPECTION_PLAN.getValue());
        LogUtil.info("inspectionPlan:" + inspectionPlan);
        inspectionPlanMapper.add(inspectionPlan);

        // 再加入计划中的任务
        List<InspectionTask> inspectionTasks = new ArrayList<>();
        for (InspectionTaskDto taskdto : inspectionPlanDto.getTasks()) {
            InspectionTask inspectionTask = new InspectionTask();
            inspectionTask.setPlanId(inspectionPlanDto.getPlanId());
            inspectionTask.setTaskTime(taskdto.getTaskTime());
            // 设置巡检人，默认巡检小组组长
            Integer inspectMan = inspectionGroupService.getInspectionGroupDto(inspectionPlanDto.getGroupId()).getLeaderId();
            inspectionTask.setInspectManId(inspectMan.intValue());
            inspectionTasks.add(inspectionTask);
        }
        LogUtil.info("inspectionTasks:" + inspectionTasks);
        for (InspectionTask task : inspectionTasks) {
            inspectionTaskService.add(task);
        }

        // 将申请第一步结束
        activitiService.completeTaskByProcessIntance(inspectionPlanDto.getProcessInstanceId());

        return inspectionPlan.getId();
    }

    @Override
    public int update(InspectionPlanDto inspectionPlanDto) {
        return 0;
    }

    @Override
    public List<InspectionPlanSimpleDto> getInspectionPlans() {
        return inspectionPlanMapper.getInspectionPlans();
    }

    @Override
    public List<InspectionPlanSimpleDto> getInspectionPlansByTunnel(Integer tunnelId) {
        return inspectionPlanMapper.getInspectionPlansByTunnel(tunnelId);
    }

    @Override
    public List<InspectionPlanSimpleDto> getInspectionPlansByVo(InspectionVo inspectionVo) {
        return inspectionPlanMapper.getInspectionPlansByVo(inspectionVo);
    }

    @Override
    public InspectionPlanDto getInspectionDto(String id) {
        return inspectionPlanMapper.getInspectionPlanDto(id);
    }

    @Override
    public InspectionPlanDto getInspectionDtoByProcessInstanceId(String processInstanceId) {
        return inspectionPlanMapper.getInspectionDtoByProcessInstanceId(processInstanceId);
    }

    @Override
    public InspectionPlan getInspectionPlanByProcessInstanceId(String processInstanceId) {
        return inspectionPlanMapper.getInspectionPlanByProcessInstanceId(processInstanceId);
    }

    @Override
    public PageInfo<InspectionPlanSimpleDto> dataGrid(InspectionVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<InspectionPlanSimpleDto> list = inspectionPlanMapper.getInspectionPlansByVo(vo);
        return new PageInfo<>(list);
    }

    @Override
    public void updateBySelective(InspectionPlan plan) {
        LogUtil.info("update plan : " + plan);
        inspectionPlanMapper.update(plan);
    }

    @Transactional
    @Override
    public void auditRequest(String processIntanceId, int value, String remark) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("result", value == 1 ? "agree" : "disagree");
        activitiService.completeTask(activitiService.getActiveTaskByProcessIntance(processIntanceId).getId(), variables, remark);

        LogUtil.info("审批完成");
        LogUtil.info("流程：" + processIntanceId);
        LogUtil.info("结果：" + (value == 1 ? "agree" : "disagree"));
        LogUtil.info("批注：" + remark);

        // 如果审批不通过，结束
        if (value == 0) return;

        ProcessBase processBase = activitiService.getProcessBaseByProcessInstance(processIntanceId);
        // 审批通过，启动巡检任务流程
        LogUtil.info("审批通过，根据计划，开始巡检任务流程");
        inspectionTaskService.startTaskProcessByPlan(processBase.getId());
    }

    /**
     * 结束任务
     *
     * @param planId
     */
    @Override
    public void complete(String planId) {
        List<InspectionTask> inspectionTasks = inspectionTaskService.getTaksByPlan(planId);

        for (InspectionTask task : inspectionTasks) {
            // 如果任务没结束，说明计划还没完全结束，退出
            if (!task.getIsFinished()) return;
        }

        // 继续下一步，结束计划
        LogUtil.info("继续下一步，结束计划:" + planId);
        InspectionPlan plan = inspectionPlanMapper.getInspectionPlanDto(planId);
        activitiService.completeTaskByProcessIntance(plan.getProcessInstanceId());
    }

	@Override
	public int getInspectionPlanSumByYear(InspectionVo inspectionVo) {
		return inspectionPlanMapper.getInspectionPlanSumByYear(inspectionVo);
	}
    
	@Override
	public int getInspectionPlanSumByMonth(InspectionVo inspectionVo) {
		return inspectionPlanMapper.getInspectionPlanSumByMonth(inspectionVo);
	}

	@Override
	public List<InspectionPlan> getAllInspectionPlan() {
		return inspectionPlanMapper.getAllInspectionPlan();
	}

	@Override
	public List<InspectionPlan> getTunnelCountByTunnelId() {
		return inspectionPlanMapper.getTunnelCountByTunnelId();
	}
	
	
    
}
