package com.bandweaver.tunnel.common.biz.itf.omm;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTaskDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTask;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

public interface InspectionTaskService {

    int add(InspectionTask inspectionTask);

    int addWithBulk(List<InspectionTask> inspectionTasks);

    InspectionTask getTask(int id);

    List<InspectionTask> getTaksByPlan(String planId);

    InspectionTaskDto getTaskDto(int id);

    InspectionTask getTaskByProcessInstance(String processInstanceId);

    int update(InspectionTask inspectionTask);

    int startTaskProcess(int inspectionTaskId);

    int startTaskProcess(InspectionTask inspectionTask);

    int startTaskProcessByPlan(int inspectionPlanId);
    
    int getCountByVo(InspectionVo inspectionVo);
    
    PageInfo<InspectionTaskDto> dataGrid(InspectionVo inspectionVo);
    
    List<InspectionTaskDto> getInspectionTasksByTaskTime(InspectionVo inspectionVo);

    JSONObject getInspectionExcel(Date startTime, Date endTime);
    
    
}
