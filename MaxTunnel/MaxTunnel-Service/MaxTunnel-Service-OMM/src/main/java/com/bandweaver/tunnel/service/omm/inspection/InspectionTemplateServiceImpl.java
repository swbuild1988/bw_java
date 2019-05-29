package com.bandweaver.tunnel.service.omm.inspection;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTemplateDto;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionTemplateService;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTemplate;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionTemplateVo;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.dao.omm.InspectionTemplateMapper;

@Service
public class InspectionTemplateServiceImpl implements InspectionTemplateService {

	@Autowired
	private InspectionTemplateMapper inspectionTemplateMapper;
	
	@Override
	public int add(InspectionTemplate temp) {
		Integer userId = ContextUtil.getLoginUser().getId();
		Date time = new Date();
		temp.setCrtTime(time);
		temp.setStaffId(userId);
		return inspectionTemplateMapper.add(temp);
	}

	@Override
	public int delete(Integer id) {
		return inspectionTemplateMapper.delete(id);
	}

	@Override
	public InspectionTemplateDto getDtoById(Integer id) {
		return inspectionTemplateMapper.getDtoById(id);
	}

	@Override
	public List<InspectionTemplateDto> getListByCondition(InspectionTemplateVo vo) {
		return inspectionTemplateMapper.getListByCondition(vo);
	}

}
