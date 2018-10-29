package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentModel;

public interface EquipmentModelService {

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
	 * @Description: 通过id获取设备型号
	 * @param @param id
	 * @param @return   
	 * @return EquipmentModel  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	EquipmentModel getEquipmentModelById(Integer id);

}
