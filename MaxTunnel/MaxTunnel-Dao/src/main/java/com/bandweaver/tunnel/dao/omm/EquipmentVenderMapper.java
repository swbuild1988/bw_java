package com.bandweaver.tunnel.dao.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentVender;

public interface EquipmentVenderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EquipmentVender record);

    EquipmentVender selectByPrimaryKey(Integer id);

	/**
	 * @Description: 获取所有供应商列表
	 * @param @return   
	 * @return List<EquipmentVender>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	List<EquipmentVender> getAllEquipmentVenderList();
}