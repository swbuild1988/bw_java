package com.bandweaver.tunnel.common.biz.itf.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.EquipmentDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.Equipment;
import com.bandweaver.tunnel.common.biz.vo.omm.EquipmentVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface EquipmentService {
	/**
	 * 通过资产号获取设备具体信息
	 * 
	 * @param AssetNo
	 * @return
	 */
	Equipment getEquipmentByAssetNo(String AssetNo);

	/**
	 * 新增设备
	 * 
	 * @param equipment
	 * @return
	 */
	Integer addEquipment(Equipment equipment);

	/**
	 * 更新设备
	 * 
	 * @param equipment
	 * @return
	 */
	Integer updateEquipment(Equipment equipment);

	/**
	 * 获得所有的设备
	 * 
	 * @return
	 */
	List<Equipment> getList();

	/**
	 * 获取某个设备类型的设备
	 * 
	 * @param typeId
	 * @return
	 */
	List<Equipment> getListByType(Integer typeId);

	/**
	 * 获取管廊类的所有设备
	 * 
	 * @param tunnelId
	 * @return
	 */
	List<Equipment> getListByTunnel(Integer tunnelId);

	/**
	 * @Description: 查询符合条件的设备列表
	 * @param @param tunnelId
	 * @param @param type
	 * @param @return
	 * @return List<Equipment>
	 * @throws @author shaosen
	 * @date 2018年5月29日
	 */
	List<EquipmentDto> getEquipmentListByCondition(EquipmentVo equipmentVo);

	/**
	 * @Description: 通过id更新设备
	 * @param @param equipment
	 * @return void
	 * @throws @author shaosen
	 * @date 2018年5月29日
	 */
	void updateEquipmentById(Equipment equipment);

	/**
	 * @Description:获取所有设备列表
	 * @param @return
	 * @return List<EquipmentDto>
	 * @throws @author shaosen
	 * @date 2018年6月6日
	 */
	List<EquipmentDto> getAllEquipmentList();

	/**
	 * @Description: 通过id获取设备信息
	 * @param @param id
	 * @param @return
	 * @return EquipmentDto
	 * @throws @author shaosen
	 * @date 2018年6月6日
	 */
	EquipmentDto getEquipmentById(Integer id);

	/**
	 * @Description: 批量添加
	 * @param @param list
	 * @return void
	 * @throws @author shaosen
	 * @date 2018年6月11日
	 */
	void addEquipmentBatch(List<Equipment> list);

	/**
	 * @Description: 选择更新
	 * @param @param e
	 * @return void
	 * @throws @author shaosen
	 * @date 2018年6月13日
	 */
	void updateEquipmentByModelId(Equipment e);

	/**
	 * @Description: 根据条件统计设备数
	 * @param @param tunnelId 管廊id
	 * @param @param status 状态id
	 * @param @param type 类型id
	 * @param @return
	 * @return int
	 * @throws @author shaosen
	 * @date 2018年6月13日
	 */
	int getCountByCondition(Integer tunnelId, Integer status, Integer type);

	/**
	 * @Description: 根据id删除设备
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月20日
	 */
	void deleteById(Integer id);

	PageInfo<EquipmentDto> dataGrid(EquipmentVo vo);
	
	/**
	 * 根据objId获取设备信息
	 * @param objId
	 * @return
	 * @author liuya
	 * @Date 2018年9月4日
	 */
	EquipmentDto getEquipmentListByObj(Integer objId);
	
	void updateEquipmentOfObj(Equipment e);

}
