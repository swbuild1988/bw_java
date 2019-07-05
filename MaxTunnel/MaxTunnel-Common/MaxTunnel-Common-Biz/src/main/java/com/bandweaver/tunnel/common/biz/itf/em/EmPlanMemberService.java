package com.bandweaver.tunnel.common.biz.itf.em;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.em.EmPlanMemberDto;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanMember;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanMemberVo;

public interface EmPlanMemberService {

	int deleteByPrimaryKey(Integer id);

    int insertSelective(EmPlanMember record, Integer role);

    EmPlanMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmPlanMember record, Integer role);

    EmPlanMemberDto getById(Integer id);

	List<EmPlanMemberDto> getByCondition(EmPlanMemberVo vo);
}
