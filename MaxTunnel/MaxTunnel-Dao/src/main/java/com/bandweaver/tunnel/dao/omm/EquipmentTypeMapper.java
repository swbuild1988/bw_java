package com.bandweaver.tunnel.dao.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentType;

public interface EquipmentTypeMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(EquipmentType record);

    EquipmentType selectByPrimaryKey(Integer id);

	/**
	 * 获取所有设备类型列表
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月27日
	 */
	List<EquipmentType> getAllEquipmentTypeList();
}