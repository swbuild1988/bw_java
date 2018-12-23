package com.bandweaver.tunnel.dao.em;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.em.EmPlanDto;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlan;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanVo;

public interface EmPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmPlan record);

    int insertSelective(EmPlan record);

    EmPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmPlan record);

    int updateByPrimaryKey(EmPlan record);

	EmPlan getEmPlanByProcessKeyAndTaskKey(@Param("processKey")String processKey, @Param("taskKey")String taskKey);

	EmPlanDto getById(Integer id);

	void deleteBatch(List<Integer> list);

	List<EmPlanDto> getByCondition(EmPlanVo vo);

	List<EmPlanDto> getNodeListByProcessKey(String processKey);
}