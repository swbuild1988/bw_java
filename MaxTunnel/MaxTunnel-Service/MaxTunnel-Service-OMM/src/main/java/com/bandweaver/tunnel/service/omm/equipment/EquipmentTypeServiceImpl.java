package com.bandweaver.tunnel.service.omm.equipment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentTypeService;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentType;
import com.bandweaver.tunnel.dao.omm.EquipmentTypeMapper;

@Service
public class EquipmentTypeServiceImpl implements EquipmentTypeService {

	@Autowired
	private EquipmentTypeMapper equipmentTypeMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return equipmentTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(EquipmentType record) {
		return equipmentTypeMapper.insert(record);
	}

	@Override
	public EquipmentType selectByPrimaryKey(Integer id) {
		return equipmentTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<EquipmentType> getAllEquipmentTypeList() {
		return equipmentTypeMapper.getAllEquipmentTypeList();
	}

	
	
}
