package com.bandweaver.tunnel.common.biz.itf.em;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.em.RelatedUnitDto;
import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;
import com.bandweaver.tunnel.common.biz.vo.em.RelatedUnitVo;
import com.github.pagehelper.PageInfo;

public interface RelatedUnitService {

	void add(RelatedUnit pojo);

	RelatedUnitDto getDtoById(Integer id);

	PageInfo<RelatedUnitDto> dataGrid(RelatedUnitVo vo);
	
	List<RelatedUnitDto> getDtoListByCondition(RelatedUnitVo vo);

	void delete(Integer id);

	void update(RelatedUnit pojo);

	List<RelatedUnitDto> getDtoListBySectionId(Integer id);

	List<RelatedUnitDto> getDtoListBySecionIdAndUnitType(Integer id, Integer unitType);

}
