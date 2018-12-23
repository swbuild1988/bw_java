package com.bandweaver.tunnel.service.omm.equipment;

import com.bandweaver.tunnel.common.biz.dto.omm.EquipmentDto;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentService;
import com.bandweaver.tunnel.common.biz.pojo.omm.Equipment;
import com.bandweaver.tunnel.common.biz.vo.omm.EquipmentVo;
import com.bandweaver.tunnel.dao.omm.EquipmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {
	@Autowired
	private EquipmentMapper equipmentMapper;

	@Override
	public Equipment getEquipmentByAssetNo(String AssetNo) {
		return equipmentMapper.getEquipmentByAssetNo(AssetNo);
	}

	@Override
	public Integer addEquipment(Equipment equipment) {
		equipment.setCrtTime(new Date());
		return equipmentMapper.addEquipment(equipment);
	}

	@Override
	public Integer updateEquipment(Equipment equipment) {
		return equipmentMapper.updateEquipment(equipment);
	}

	@Override
	public List<Equipment> getList() {
		return equipmentMapper.getList();
	}

	@Override
	public List<Equipment> getListByType(Integer typeId) {
		return equipmentMapper.getListByType(typeId);
	}

	@Override
	public List<Equipment> getListByTunnel(Integer tunnelId) {
		return equipmentMapper.getListByTunnel(tunnelId);
	}

	@Override
	public List<EquipmentDto> getEquipmentListByCondition(EquipmentVo equipmentVo) {

		return equipmentMapper.getEquipmentListByCondition(equipmentVo);
	}

	@Override
	public void updateEquipmentById(Equipment equipment) {
		equipmentMapper.updateEquipmentById(equipment);
	}

	@Override
	public List<EquipmentDto> getAllEquipmentList() {
		return equipmentMapper.getEquipmentListByCondition(null);
	}

	@Override
	public EquipmentDto getEquipmentById(Integer id) {
		return equipmentMapper.getEquipmentById(id);
	}

	@Override
	public void addEquipmentBatch(List<Equipment> list) {
		equipmentMapper.addEquipmentBatch(list);
	}

	@Override
	public void updateEquipmentByModelId(Equipment e) {
		equipmentMapper.updateEquipmentByModelId(e);
	}

	@Override
	public int getCountByCondition(Integer tunnelId, Integer status, Integer type) {
		return equipmentMapper.getCountByCondition(tunnelId, status, type);
	}

	@Override
	public void deleteById(Integer id) {
		equipmentMapper.deleteById(id);
	}

	@Override
	public PageInfo<EquipmentDto> dataGrid(EquipmentVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<EquipmentDto> list = equipmentMapper.getEquipmentListByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public EquipmentDto getEquipmentListByObj(Integer objId) {
		List<EquipmentDto> list = equipmentMapper.getEquipmentListByObj(objId);
		return  list.size() > 0 ? list.get(0) : null ;
	}

	@Override
	public void updateEquipmentOfObj(Equipment e) {
		equipmentMapper.updateEquipmentOfObj(e);
	}
	
}
