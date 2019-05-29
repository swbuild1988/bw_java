package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTemplateDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTemplate;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionTemplateVo;

public interface InspectionTemplateService {

	int add(InspectionTemplate temp);
	
	int delete(Integer id);
	
	InspectionTemplateDto getDtoById(Integer id);
	
	List<InspectionTemplateDto> getListByCondition(InspectionTemplateVo vo);
}
