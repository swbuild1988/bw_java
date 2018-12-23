package com.bandweaver.tunnel.dao.em;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.em.RelatedUnitDto;
import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;
import com.bandweaver.tunnel.common.biz.vo.em.RelatedUnitVo;

public interface RelatedUnitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RelatedUnit record);

    int insertSelective(RelatedUnit record);

    RelatedUnit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RelatedUnit record);

    int updateByPrimaryKey(RelatedUnit record);

	RelatedUnitDto getDtoById(Integer id);

	List<RelatedUnitDto> getDtoListByCondition(RelatedUnitVo vo);
}