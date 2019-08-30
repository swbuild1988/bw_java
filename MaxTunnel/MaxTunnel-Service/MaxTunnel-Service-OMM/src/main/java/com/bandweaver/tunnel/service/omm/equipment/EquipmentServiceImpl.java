package com.bandweaver.tunnel.service.omm.equipment;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.omm.EquipmentDto;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentService;
import com.bandweaver.tunnel.common.biz.pojo.omm.Equipment;
import com.bandweaver.tunnel.common.biz.vo.omm.DefectVo;
import com.bandweaver.tunnel.common.biz.vo.omm.EquipmentVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.common.TunnelMapper;
import com.bandweaver.tunnel.dao.omm.DefectMapper;
import com.bandweaver.tunnel.dao.omm.EquipmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImpl implements EquipmentService {
	@Autowired
	private EquipmentMapper equipmentMapper;
	@Autowired
	private TunnelMapper tunnelMapper;
	@Autowired
	private DefectMapper defectMapper;

	@Override
	public Integer addEquipment(Equipment equipment) {
		equipment.setCrtTime(new Date());
		equipment.setAlarmNo(0);
		if(equipment.getObjId() != null) {
			List<EquipmentDto> list = equipmentMapper.getEquipmentListByObj(equipment.getObjId());
			if(list != null && list.size() > 0) {
				LogUtil.info("监测对象【" + equipment.getObjId() + "】已绑定设备！");
				return 0;
			}
		}
		return equipmentMapper.addEquipment(equipment);
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
	public List<EquipmentDto> getEquipmentListByCondition(EquipmentVo equipmentVo) {

		return equipmentMapper.getEquipmentListByCondition(equipmentVo);
	}

	@Override
	public void updateEquipmentById(Equipment equipment) {
		if(equipment.getObjId() != null) {
			List<EquipmentDto> list = equipmentMapper.getEquipmentListByObj(equipment.getObjId());
			if(list != null && list.size() > 0) {
				list = list.stream().filter(dto -> !dto.getId().equals(equipment.getId())).collect(Collectors.toList());
				for(Equipment e : list) {
					e.setObjId(null);
					updateEquipmentOfObj(e);
				}
			}
		}
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
	public EquipmentDto getEquipmentByObj(Integer objId) {
		List<EquipmentDto> list = equipmentMapper.getEquipmentListByObj(objId);
		return  list.size() > 0 ? list.get(0) : null ;
	}

	@Override
	public void updateEquipmentOfObj(Equipment e) {
		equipmentMapper.updateEquipmentOfObj(e);
	}
	
	/**
	 * 仅用于设备报表
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author ya.liu
	 * @Date 2019年3月9日
	 */
	public List<JSONObject> getEquipmentExcel(Date startTime, Date endTime){
		List<TunnelSimpleDto> tunnelList = tunnelMapper.getList();
		List<JSONObject> list = new ArrayList<>();
		for (TunnelSimpleDto tunnel : tunnelList) {
			JSONObject obj = new JSONObject();
			obj.put("tunnel", tunnel.getName());
			DefectVo vo = new DefectVo();
			vo.setTunnelId(tunnel.getId());
			obj.put("brokenSum", defectMapper.getCountByCondition(vo));
			obj.put("all", getCountByCondition(tunnel.getId(), null, null));
			vo.setStartTime(startTime);
			vo.setEndTime(endTime);
			int i = defectMapper.getCountByCondition(vo);
			obj.put("broken", i);
			list.add(obj);
		}
		return list;
	}
	
	@Override
	public int getCountBySectionId(Integer sectionId) {
		
		return equipmentMapper.getCountBySectionId(sectionId);
	}
}
