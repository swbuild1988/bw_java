package com.bandweaver.tunnel.service.omm.spare;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.omm.SpareWhitherEnum;
import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareOutDto;
import com.bandweaver.tunnel.common.biz.itf.omm.SpareOutService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.Equipment;
import com.bandweaver.tunnel.common.biz.pojo.omm.Instrument;
import com.bandweaver.tunnel.common.biz.pojo.omm.Spare;
import com.bandweaver.tunnel.common.biz.pojo.omm.SpareOut;
import com.bandweaver.tunnel.common.biz.vo.omm.SpareOutVo;
import com.bandweaver.tunnel.dao.omm.EquipmentMapper;
import com.bandweaver.tunnel.dao.omm.InstrumentMapper;
import com.bandweaver.tunnel.dao.omm.SpareMapper;
import com.bandweaver.tunnel.dao.omm.SpareOutMapper;

@Service
@Transactional
public class SpareOutServiceImpl implements SpareOutService {

	@Autowired
	private SpareOutMapper spareOutMapper;
	@Autowired
	private SpareMapper spareMapper;
	@Autowired
	private InstrumentMapper instrumentMapper;
	@Autowired
	private EquipmentMapper equipmentMapper;

	@Override
	public int update(SpareOut s) {
		SpareOutDto dto = spareOutMapper.getSpareOutDtoById(s.getId());
		// 判断是否改变备品去向
		if(s.getWhither() != null && !s.getWhither().equals(dto.getWhither())) {
			// 改变备品去向操作
			if(SpareWhitherEnum.PIPE.getValue() == s.getWhither()) {
				
			}else if(SpareWhitherEnum.INSTRUMENT.getValue() == s.getWhither()) {
				
			}
		}
		return spareOutMapper.update(s);
	}

	@Override
	@Transactional
<<<<<<< HEAD
	public int addBatch(List<SpareOut> list, Integer tunnelId) {
=======
	public int addBatch(List<SpareOut> list, Integer tunnelId, Integer objId) {
>>>>>>> bf512039ff8442b3d1853c03de35f9d29734072e
		// 备品去向为仪表工具集合
		List<Instrument> instrumentList = new ArrayList<>();
		// 备品去向为管廊设备集合
		List<Equipment> equipmentList = new ArrayList<>();
		for(SpareOut out : list) {
			//修改备品状态
			Spare spare = new Spare();
			spare.setId(out.getId());
			spare.setStatus(false);
			spareMapper.update(spare);
			
			//备品去向
			SpareDto dto = spareMapper.getSpareDtoById(out.getId());
			
			if(SpareWhitherEnum.PIPE.getValue() == out.getWhither()) {
				Equipment eq = new Equipment();
				eq.setCrtTime(out.getOutTime());
				eq.setRunTime(out.getOutTime());
				eq.setModelId(dto.getModelId());
				eq.setType(dto.getTypeId());
				eq.setName(dto.getName());
				eq.setStatus(1);
				eq.setTunnelId(tunnelId);
<<<<<<< HEAD
=======
				eq.setObjId(objId);
>>>>>>> bf512039ff8442b3d1853c03de35f9d29734072e
				eq.setVenderId(dto.getVenderId());
				eq.setAlarmNo(0);
				equipmentList.add(eq);
			}else if(SpareWhitherEnum.INSTRUMENT.getValue() == out.getWhither()) {
				Instrument in = new Instrument();
				in.setSpareId(dto.getId());
				in.setName(dto.getName());
				in.setTypeId(dto.getTypeId());
				in.setModelId(dto.getModelId());
				in.setVenderId(dto.getVenderId());
				in.setUseStatus(EquipmentStatusEnum.NORMAL.getValue());
				in.setStatus(true);
				in.setInTime(out.getOutTime());
				instrumentList.add(in);
			}
		}
		//备品出库
		int i = spareOutMapper.addBatch(list);
		
		// 批量添加仪表工具
		if(instrumentList.size() > 0) instrumentMapper.addBatchBySpare(instrumentList);
		// 批量添加管廊设备
		if(equipmentList.size() > 0) equipmentMapper.addEquipmentBatch(equipmentList);
		return i;
	}

	@Override
	public List<SpareOutDto> getSpareOutDtoByCondition(SpareOutVo vo) {
		return spareOutMapper.getSpareOutDtoByCondition(vo);
	}

	@Override
	public ListPageUtil<SpareOutDto> dataGrid(SpareOutVo vo) {
		List<SpareOutDto> dto = spareOutMapper.getSpareOutDtoByCondition(vo);
		ListPageUtil<SpareOutDto> page = new ListPageUtil<>(dto, vo.getPageNum(), vo.getPageSize());
		return page;
	}

	@Override
	public SpareOutDto getSpareOutDtoById(Integer id) {
		return spareOutMapper.getSpareOutDtoById(id);
	}

	@Override
	public int deleteByIds(List<Integer> list) {
		return spareOutMapper.deleteByIds(list);
	}

	@Override
	public List<CommonDto> getCountGroupByStaffId() {
		return spareOutMapper.getCountGroupByStaffId();
	}

	@Override
	public int getCountByWhither(Integer whither) {
		return spareOutMapper.getCountByWhither(whither);
	}

}
