package com.bandweaver.tunnel.dao.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTaskDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTask;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface InspectionTaskMapper {
    int add(InspectionTask inspectionTask);

    int addWithBulk(List<InspectionTask> inspectionTasks);

    int update(InspectionTask inspectionTask);

    InspectionTask getTask(@Param("taskId") int taskId);

    InspectionTaskDto getTaskDto(@Param("id") int id);

    InspectionTask getTaskByProcessInstance(@Param("processInstanceId") String processInstanceId);

    List<InspectionTask> getTasksByInspectionPlan(@Param("inspectionPlanId") int inspectionPlanId);

    List<InspectionTask> getTasksByInspectionPlan2(@Param("planId") String planId);
    
    int getFinishedInspectionTaskSumByMonth(InspectionVo inspectionVo);
    
    int getInspectionTaskSumByYear(InspectionVo inspectionVo);
    
    int getFinishedInspectionTaskSumByYear(InspectionVo inspectionVo);
    
    List<InspectionTaskDto> getInspectionTasksByVo(InspectionVo inspectionVo);
    
    List<InspectionTaskDto> getAllTaskByUserId(@Param("userId") Integer userId);
    
    List<InspectionTaskDto> getInspectionTasksByTaskTime(InspectionVo inspectionVo);
}
