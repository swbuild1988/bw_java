package com.bandweaver.tunnel.dao.omm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionTemplateDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionTemplate;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionTemplateVo;

public interface InspectionTemplateMapper {
	int add(InspectionTemplate temp);
	
	int delete(@Param("id") Integer id);
	
	InspectionTemplateDto getDtoById(@Param("id") Integer id);
	
	List<InspectionTemplateDto> getListByCondition(InspectionTemplateVo vo);
}
