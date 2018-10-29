package com.bandweaver.tunnel.service.omm.equipment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentVenderService;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentVender;
import com.bandweaver.tunnel.dao.omm.EquipmentVenderMapper;

@Service
public class EquipmentVenderServiceImpl implements EquipmentVenderService {

	@Autowired
	private EquipmentVenderMapper equipmentVenderMapper;

	@Override
	public List<EquipmentVender> getAllEquipmentVenderList() {
		return equipmentVenderMapper.getAllEquipmentVenderList();
	}

	@Override
	public EquipmentVender getEquipmentVenderById(Integer id) {
		return equipmentVenderMapper.selectByPrimaryKey(id);
	}
}
