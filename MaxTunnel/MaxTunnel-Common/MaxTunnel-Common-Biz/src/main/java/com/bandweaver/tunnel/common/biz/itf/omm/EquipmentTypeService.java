package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentType;

public interface EquipmentTypeService {

	/**
	 * 删除
	 * @param id
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月27日
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 添加
	 * @param record
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月27日
	 */
    int insert(EquipmentType record);

    /**
     * 通过id获取设备类型
     * @param id
     * @return
     * @author ya.liu
     * @Date 2018年11月27日
     */
    EquipmentType selectByPrimaryKey(Integer id);

	/**
	 * 获取所有设备类型列表
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月27日
	 */
	List<EquipmentType> getAllEquipmentTypeList();

}
