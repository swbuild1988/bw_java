package com.bandweaver.tunnel.dao.oam;

import com.bandweaver.tunnel.common.biz.pojo.oam.CableType;

import java.util.List;

public interface CableTypeMapper {

	List<CableType> getAllCables();

	CableType selectByPrimaryKey(int id);

    int deleteByPrimaryKey(int id);

    int insert(CableType record);

    int updateByPrimaryKey(CableType record);
}