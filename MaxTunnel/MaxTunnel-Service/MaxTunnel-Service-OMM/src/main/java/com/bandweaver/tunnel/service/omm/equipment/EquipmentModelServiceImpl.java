package com.bandweaver.tunnel.service.omm.equipment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentModelService;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentModel;
import com.bandweaver.tunnel.dao.omm.EquipmentModelMapper;
@Service
public class EquipmentModelServiceImpl implements EquipmentModelService {

	@Autowired
	private EquipmentModelMapper equipmentModelMapper;

	@Override
	public List<EquipmentModel> getAllEquipmentModelList() {
		return equipmentModelMapper.getAllEquipmentModelList();
	}

	@Override
	public EquipmentModel getEquipmentModelById(Integer id) {
		return equipmentModelMapper.selectByPrimaryKey(id);
	}
}
