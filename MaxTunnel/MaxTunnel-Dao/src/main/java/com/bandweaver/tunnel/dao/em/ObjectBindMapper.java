package com.bandweaver.tunnel.dao.em;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.pojo.em.ObjectBind;

public interface ObjectBindMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ObjectBind record);

    int insertSelective(ObjectBind record);

    ObjectBind selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ObjectBind record);

    int updateByPrimaryKey(ObjectBind record);

	List<ObjectBind> getPlansByObject(@Param("objectId")Integer objectId, @Param("type")Integer type);
}