package com.bandweaver.tunnel.service.omm.inspection;

import com.bandweaver.tunnel.common.biz.itf.omm.InspectionStepService;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionStep;
import com.bandweaver.tunnel.dao.omm.InspectionStepMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionStepServiceImpl implements InspectionStepService{
    @Autowired
    private InspectionStepMapper inspectionStepMapper;

	@Override
	public int add(InspectionStep inspectionStep) {
		return inspectionStepMapper.add(inspectionStep);
	}

	@Override
	public int update(InspectionStep inspectionStep) {
		return inspectionStepMapper.update(inspectionStep);
	}

	@Override
	public int deleteBatch(List<Integer> list) {
		return inspectionStepMapper.deleteBatch(list);
	}

	@Override
	public InspectionStep getById(Integer id) {
		return inspectionStepMapper.getById(id);
	}

	@Override
	public List<InspectionStep> getInspectionStepsByCondition(InspectionStep inspectionStep) {
		return inspectionStepMapper.getInspectionStepsByCondition(inspectionStep);
	}

}
