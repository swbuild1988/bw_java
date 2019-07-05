package com.bandweaver.tunnel.dao.em;

import java.util.List;


import com.bandweaver.tunnel.common.biz.dto.em.EmPlanGroupDto;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanGroup;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanGroupVo;

public interface EmPlanGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EmPlanGroup record);

    EmPlanGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmPlanGroup record);

	EmPlanGroupDto getById(Integer id);

	List<EmPlanGroupDto> getByCondition(EmPlanGroupVo vo);

}