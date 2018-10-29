package com.bandweaver.tunnel.dao.mam;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.pojo.mam.mapping.MeasObjMap;

public interface MeasObjMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeasObjMap record);

    int insertSelective(MeasObjMap record);

    MeasObjMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeasObjMap record);

    int updateByPrimaryKey(MeasObjMap record);

	List<MeasObjMap> getByObjectIdAndInputValue(@Param("objectId")Integer objectId, @Param("inputValue")Integer inputValue);

	void deleteBatch(List<Integer> list);
}