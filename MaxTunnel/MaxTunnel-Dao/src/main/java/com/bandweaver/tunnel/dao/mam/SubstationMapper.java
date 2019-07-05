package com.bandweaver.tunnel.dao.mam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.mam.substation.SubstationDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.substation.Substation;
import com.bandweaver.tunnel.common.biz.vo.mam.substation.SubstationVo;

public interface SubstationMapper {
	
	SubstationDto selectByPrimaryKey(Integer id);
	
	int deleteByPrimaryKey(Integer id);
	
	int insertSelective(Substation substation);
	
	int updateByPrimaryKeySelective(Substation substation);
	
	List<SubstationDto> getListByCondition(SubstationVo vo);
	
	SubstationDto getDtoById(Integer id);
}
