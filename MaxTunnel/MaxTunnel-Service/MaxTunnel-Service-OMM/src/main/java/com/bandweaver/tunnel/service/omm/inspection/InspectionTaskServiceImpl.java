package com.bandweaver.tunnel.service.omm.inspection;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTaskDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionTaskService;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTask;
import com.bandweaver.tunnel.common.biz.vo.BaseVo;
import com.bandweaver.tunnel.common.biz.vo.omm.DefectVo;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.bandweaver.tunnel.common.biz.vo.omm.MaintenanceOrderVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.omm.DefectMapper;
import com.bandweaver.tunnel.dao.omm.InspectionTaskMapper;
import com.bandweaver.tunnel.dao.omm.MaintenanceOrderMapper;
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
    @Autowired
    private DefectMapper defectMapper;
    @Autowired
    private MaintenanceOrderMapper maintenanceOrderMapper;

    @Override
    public int add(InspectionTask inspectionTask) {
        inspectionTask.setProcessType(ProcessTypeEnum.INPECTION_TASK.getValue());
        inspectionTaskMapper.add(inspectionTask);
        return inspectionTask.getId();
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
	public int getCountByVo(InspectionVo inspectionVo) {
		return inspectionTaskMapper.getCountByVo(inspectionVo);
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

	/**
	 * 仅用于巡检报表
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author ya.liu
	 * @Date 2019年3月11日
	 */
	public JSONObject getInspectionExcel(Date startTime, Date endTime) {
		JSONObject obj = new JSONObject();
		DefectVo vo1 = new DefectVo();
		vo1.setEndTime(startTime);
		MaintenanceOrderVo vo2 = new MaintenanceOrderVo();
		vo2.setEndTime(startTime);
		// 获取本月之前的缺陷数
		int beforeDefect = defectMapper.getCountByCondition(vo1);
		// 获取本月之前的维修数
		int beforeOrder = maintenanceOrderMapper.getCountByCondition(vo2);
		// 获取本月之前剩余的缺陷数
		int odd = beforeDefect - beforeOrder;
		obj.put("odd", odd);
		vo1.setStartTime(startTime);
		vo1.setEndTime(endTime);
		vo2.setStartTime(startTime);
		vo2.setEndTime(endTime);
		InspectionVo vo3 = new InspectionVo();
		vo3.setStartTime(startTime);
		vo3.setEndTime(endTime);
		// 获取本月巡检任务数
		int task = inspectionTaskMapper.getCountByCondition(vo3);
		obj.put("task", task);
		// 获取本月缺陷数
		int defect = defectMapper.getCountByCondition(vo1);
		obj.put("defect", defect);
		// 获取本月维修数
		int order = maintenanceOrderMapper.getCountByCondition(vo2);
		obj.put("order", order);
		// 本月维修率
		int allDefect = odd + defect;// 缺陷数
		double proportion = allDefect == 0 ? 0.0 : (order * 10000 / allDefect) / 100.0;
		obj.put("proportion", proportion);
		// 尚未维修数
		obj.put("not", allDefect - order);
		
		return obj;
	}
    
}
