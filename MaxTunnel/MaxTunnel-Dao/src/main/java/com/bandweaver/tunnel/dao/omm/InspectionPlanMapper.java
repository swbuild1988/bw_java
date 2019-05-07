package com.bandweaver.tunnel.dao.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectionPlanMapper {

    int add(InspectionPlan inspectionPlan);

    int update(InspectionPlan inspectionPlan);
    
    int delete(@Param("planId") String planId);

    List<InspectionPlanSimpleDto> getInspectionPlans();

    List<InspectionPlanSimpleDto> getInspectionPlansByTunnel(Integer tunnelId);

    InspectionPlanDto getInspectionPlanDto(@Param("planId") String planId);
    
    InspectionPlanDto getInspectionDtoByProcessInstanceId(String processInstanceId);

    List<InspectionPlanSimpleDto> getInspectionPlansByVo(InspectionVo inspectionVo);

	InspectionPlan getInspectionPlanByProcessInstanceId(String processInstanceId);

	int getCountByCondition(InspectionVo inspectionVo);
	
	List<InspectionPlan> getAllInspectionPlan();
	
}
