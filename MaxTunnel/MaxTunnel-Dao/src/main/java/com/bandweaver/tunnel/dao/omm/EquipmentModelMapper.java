package com.bandweaver.tunnel.dao.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentModel;

public interface EquipmentModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EquipmentModel record);

    int insertSelective(EquipmentModel record);

    EquipmentModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EquipmentModel record);

    int updateByPrimaryKey(EquipmentModel record);

	/**
	 * @Description: 获取所有设备型号列表
	 * @param @return   
	 * @return List<EquipmentModel>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	List<EquipmentModel> getAllEquipmentModelList();
}