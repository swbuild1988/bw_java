package com.bandweaver.tunnel.dao.em;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.em.EmPlanMemberDto;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanMember;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanMemberVo;

public interface EmPlanMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EmPlanMember record);

    EmPlanMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmPlanMember record);

    EmPlanMemberDto getById(Integer id);

	List<EmPlanMemberDto> getByCondition(EmPlanMemberVo vo);

}