package com.bandweaver.tunnel.common.biz.itf.omm;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.github.pagehelper.PageInfo;

public interface InspectionPlanService {
    int add(InspectionPlanDto inspectionPlanDto);

    int update(InspectionPlanDto inspectionPlanDto);

    List<InspectionPlanSimpleDto> getInspectionPlans();

    List<InspectionPlanSimpleDto> getInspectionPlansByTunnel(Integer tunnelId);

    List<InspectionPlanSimpleDto> getInspectionPlansByVo(InspectionVo inspectionVo);

    InspectionPlanDto getInspectionDto(String id);
    /**
     * 
     * @Description: 获取详细计划
     * @param @param id
     * @param @return   
     * @return InspectionPlanDto  
     * @throws
     * @author shaosen
     * @date 2018年7月18日
     */
    InspectionPlanDto getInspectionDtoByProcessInstanceId(String processInstanceId);

	InspectionPlan getInspectionPlanByProcessInstanceId(String processInstanceId);

	PageInfo<InspectionPlanSimpleDto> dataGrid(InspectionVo inspectionVo);

	void updateBySelective(InspectionPlan plan);

	void auditRequest(String processIntanceId, int value, String remark);

    /**
     * 结束任务
     * @param planId
     */
	void complete(String planId);
	
	int getInspectionPlanSumByYear(InspectionVo inspectionVo);
	
	int getInspectionPlanSumByMonth(InspectionVo inspectionVo);
	
	List<InspectionPlan> getAllInspectionPlan();
	
	List<InspectionPlan> getTunnelCountByTunnelId();
}
