package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentVender;

public interface EquipmentVenderService {

	/**
	 * @Description: 获取所有供应商列表
	 * @param @return   
	 * @return List<EquipmentVender>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	List<EquipmentVender> getAllEquipmentVenderList();

	/**
	 * @Description: 通过id获取设备供应商
	 * @param @param id
	 * @param @return   
	 * @return EquipmentVender  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	EquipmentVender getEquipmentVenderById(Integer id);

}
