package com.bandweaver.tunnel.service.oam.energy;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.oam.EnergyDto;
import com.bandweaver.tunnel.common.biz.dto.oam.EnergySimpleDto;
import com.bandweaver.tunnel.common.biz.itf.oam.EnergyService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Energy;
import com.bandweaver.tunnel.common.biz.vo.oam.EnergyVo;
import com.bandweaver.tunnel.dao.oam.EnergyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class EnergyServiceImpl implements EnergyService {

	@Autowired
	private EnergyMapper energyMapper;

	@Override
	public void addEnergy(Energy energy) {
		energyMapper.insert(energy);
	}

	@Override
	public Double getTotalValueByTunnelId(Integer id) {
		return energyMapper.getTotalValueByTunnelId(id);
	}

	@Override
	public Double getAllTunnelValue() {
		return energyMapper.getAllTunnelValue();
	}

	@Override
	public Double getSumByTime(Date startTime, Date endTime) {
		return energyMapper.getSumByTime(startTime,endTime);
	}

	@Override
	public List<EnergyDto> getTunnelEngergyConsumptionListByTime(Date startTime, Date endTime) {
		return energyMapper.getTunnelEngergyConsumptionListByTime(startTime,endTime);
	}

	@Override
	public void addEnergyBatch(List<Energy> list) {
		energyMapper.addEnergyBatch(list);
	}

	@Override
	public Double getEnergyByTunnelIdAndTime(Integer id, Date startTime, Date endTime) {
		return energyMapper.getEnergyByTunnelIdAndTime(id,startTime,endTime);
	}

	@Override
	public List<EnergySimpleDto> getEnergiesByTunnelIdAndTimeCondition(Integer tunnelId, Date startTime, Date endTime) {
		return energyMapper.getEnergiesByTunnelIdAndTimeCondition(tunnelId,startTime,endTime);
	}

	@Override
	public List<EnergySimpleDto> getEnergiesByCondition(EnergyVo vo) {
		return energyMapper.getEnergiesByCondition(vo);
	}

	@Override
	public PageInfo<EnergySimpleDto> dataGrid(EnergyVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<EnergySimpleDto> list = energyMapper.getEnergiesByCondition(vo);
		return new PageInfo<>(list);
	}

}