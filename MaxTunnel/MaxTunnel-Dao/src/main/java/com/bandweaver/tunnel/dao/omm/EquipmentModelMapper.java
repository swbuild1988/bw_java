package com.bandweaver.tunnel.dao.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentModel;

public interface EquipmentModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EquipmentModel record);

    EquipmentModel selectByPrimaryKey(Integer id);

	/**
	 * @Description: 获取所有设备型号列表
	 * @param @return   
	 * @return List<EquipmentModel>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	List<EquipmentModel> getAllEquipmentModelList();
	
	/**
	 * 获取某一类型下的设备型号列表
	 * @param typeId
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月29日
	 */
	List<EquipmentModel> getEquipmentModelByTypeId(Integer typeId);
}