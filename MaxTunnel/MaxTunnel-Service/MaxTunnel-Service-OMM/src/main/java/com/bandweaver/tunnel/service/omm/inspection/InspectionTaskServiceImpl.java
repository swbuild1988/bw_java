package com.bandweaver.tunnel.service.omm.inspection;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTaskDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionTaskService;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTask;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.omm.InspectionTaskMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InspectionTaskServiceImpl implements InspectionTaskService {
    @Autowired
    private InspectionTaskMapper inspectionTaskMapper;
    @Autowired
    private ActivitiService activitiService;

    @Override
    public int add(InspectionTask inspectionTask) {
        inspectionTask.setProcessType(ProcessTypeEnum.INPECTION_TASK.getValue());
        int id = inspectionTaskMapper.add(inspectionTask);
        return inspectionTask.getId().intValue();
    }

    @Override
    public int addWithBulk(List<InspectionTask> inspectionTasks) {
        return inspectionTaskMapper.addWithBulk(inspectionTasks);
    }

    @Override
    public InspectionTask getTask(int id) {
        return inspectionTaskMapper.getTask(id);
    }

    @Override
    public List<InspectionTask> getTaksByPlan(String planId) {
        return inspectionTaskMapper.getTasksByInspectionPlan2(planId);
    }

    @Override
    public InspectionTaskDto getTaskDto(int id) {
        return inspectionTaskMapper.getTaskDto(id);
    }

    @Override
    public InspectionTask getTaskByProcessInstance(String processInstanceId) {
        return inspectionTaskMapper.getTaskByProcessInstance(processInstanceId);
    }

    @Override
    public int update(InspectionTask inspectionTask) {
        return inspectionTaskMapper.update(inspectionTask);
    }

    @Override
    public int startTaskProcess(int inspectionTaskId) {

        InspectionTask inspectionTask = inspectionTaskMapper.getTask(inspectionTaskId);
        startTaskProcess(inspectionTask);

        return 1;
    }

    @Override
    public int startTaskProcess(InspectionTask inspectionTask) {

        inspectionTask.setProcessInstanceId(activitiService.startProcessInstance(ProcessTypeEnum.INPECTION_TASK, inspectionTask.getInspectManId()));
        inspectionTaskMapper.update(inspectionTask);

        // 自动结束第一步
        activitiService.completeTaskByProcessIntance(inspectionTask.getProcessInstanceId());

        return 1;
    }

    @Override
    public int startTaskProcessByPlan(int inspectionPlanId) {

        List<InspectionTask> inspectionTasks = inspectionTaskMapper.getTasksByInspectionPlan(inspectionPlanId);
        LogUtil.info("巡检任务s：" + inspectionTasks);

        for (InspectionTask inspectionTask : inspectionTasks) {
            startTaskProcess(inspectionTask);
        }

        return 1;
    }

	@Override
	public int getFinishedInspectionTaskSumByMonth(InspectionVo inspectionVo) {
		return inspectionTaskMapper.getFinishedInspectionTaskSumByMonth(inspectionVo);
	}

	@Override
	public int getInspectionTaskSumByYear(InspectionVo inspectionVo) {
		return inspectionTaskMapper.getInspectionTaskSumByYear(inspectionVo);
	}

	@Override
	public int getFinishedInspectionTaskSumByYear(InspectionVo inspectionVo) {
		return inspectionTaskMapper.getFinishedInspectionTaskSumByYear(inspectionVo);
	}

	@Override
	public PageInfo<InspectionTaskDto> dataGrid(InspectionVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<InspectionTaskDto> list = inspectionTaskMapper.getInspectionTasksByVo(vo);
		return new PageInfo<>(list);
	}

	@Override
	public List<InspectionTaskDto> getInspectionTasksByTaskTime(InspectionVo inspectionVo) {
		return inspectionTaskMapper.getInspectionTasksByTaskTime(inspectionVo);
	}


	

	
    
    
}
