package com.bandweaver.tunnel.dao.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.biz.vo.StoreTypeVo;

public interface StoreTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreType record);

    int insertSelective(StoreType record);

    StoreType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreType record);

    int updateByPrimaryKey(StoreType record);

	List<StoreType> getList();

	void deleteBatch(List<Integer> list);

	List<StoreType> getBycondition(StoreTypeVo vo);

	StoreType getByName(String name);

    StoreType getBySN(String sn);
}