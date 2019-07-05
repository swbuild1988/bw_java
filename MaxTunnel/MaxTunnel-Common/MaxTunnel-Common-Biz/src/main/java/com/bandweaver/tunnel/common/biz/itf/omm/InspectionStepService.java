package com.bandweaver.tunnel.common.biz.itf.omm;

import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionStep;

import java.util.List;

public interface InspectionStepService {
	
	int add(InspectionStep inspectionStep);

    int update(InspectionStep inspectionStep);
    
    int deleteBatch(List<Integer> list);

    InspectionStep getById(Integer id);

    List<InspectionStep> getInspectionStepsByCondition(InspectionStep inspectionStep);
}
