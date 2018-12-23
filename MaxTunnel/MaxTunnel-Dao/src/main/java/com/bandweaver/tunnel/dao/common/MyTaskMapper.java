package com.bandweaver.tunnel.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.MyTaskDto;
import com.bandweaver.tunnel.common.biz.pojo.MyTask;
import com.bandweaver.tunnel.common.biz.vo.MyTaskVo;

public interface MyTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyTask record);

    int insertSelective(MyTask record);

    MyTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MyTask record);

    int updateByPrimaryKey(MyTask record);

	List<MyTaskDto> getDtoListByCondition(MyTaskVo vo);

	void updateStatusByProDefId(@Param("status")Integer status, @Param("processDefinitionId")String processDefinitionId);

	int getMsgCount(@Param("staffId")Integer staffId,@Param("status")Integer status);
}