package com.bandweaver.tunnel.dao.omm;

import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionStep;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectionStepMapper {
    int add(InspectionStep inspectionStep);

    int update(InspectionStep inspectionStep);
    
    int deleteBatch(List<Integer> list);

    InspectionStep getById(@Param("id") Integer id);

    List<InspectionStep> getInspectionStepsByCondition(InspectionStep inspectionStep);
    
}