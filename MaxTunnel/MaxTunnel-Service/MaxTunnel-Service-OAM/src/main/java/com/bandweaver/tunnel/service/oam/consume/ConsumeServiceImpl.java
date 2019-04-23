package com.bandweaver.tunnel.service.oam.consume;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ConsumeDto;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;
import com.bandweaver.tunnel.common.biz.pojo.oam.ConsumeData;
import com.bandweaver.tunnel.common.biz.vo.oam.ConsumeDataVo;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.dao.common.TunnelMapper;
import com.bandweaver.tunnel.dao.oam.ConsumeDataMapper;
import com.bandweaver.tunnel.dao.oam.ConsumeMapper;

@Service
public class ConsumeServiceImpl implements ConsumeService {

	@Autowired
	private ConsumeMapper consumeMapper;
	@Autowired
	private ConsumeDataMapper consumeDataMapper;
	@Autowired
	private TunnelMapper tunnelMapper;

	@Override
	public int insert(Consume consume) {
		return consumeMapper.insert(consume);
	}

	@Override
	public int update(Consume consume) {
		return consumeMapper.update(consume);
	}

	@Override
	public int delete(Integer id) {
		return consumeMapper.delete(id);
	}

	@Override
	public int addBatch(List<Consume> list) {
		return consumeMapper.addBatch(list);
	}
	
	@Override
	public int addBatchWithObjectId(List<Consume> list) {
		return consumeMapper.addBatchWithObjectId(list);
	}

	@Override
	public int deleteBatch(List<Integer> list) {
		return consumeMapper.deleteBatch(list);
	}

	@Override
	public Consume getConsumeById(Integer id) {
		return consumeMapper.getConsumeById(id);
	}

	@Override
	public ConsumeDto getConsumeDtoById(Integer id) {
		return consumeMapper.getConsumeDtoById(id);
	}

	@Override
	public List<ConsumeDto> getConsumesByCondition(ConsumeDataVo vo) {
		return consumeMapper.getConsumesByCondition(vo);
	}

	@Override
	public List<ConsumeDto> getConsumes() {
		return consumeMapper.getConsumeDtos();
	}
	
	/**
	 * 仅用于能耗报表
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author ya.liu
	 * @Date 2019年3月9日
	 */
	public List<JSONObject> getConsumeExcel(Date startTime, Date endTime) {
		// 获取所有管廊
		List<TunnelSimpleDto> list = tunnelMapper.getList();
		
		List<JSONObject> ls = new ArrayList<>();
		for(TunnelSimpleDto dto : list) {
			JSONObject obj = new JSONObject();
			obj.put("tunnel", dto.getName());
			
			Date st = new Date(startTime.getTime());
			Date et = new Date(endTime.getTime());
			// 今年某一时间段的能耗
			ConsumeDataVo vo = new ConsumeDataVo();
			vo.setTunnelId(dto.getId());
			vo.setStartTime(st);
			vo.setEndTime(et);
			Double d1 = getSumByCondition(vo);
			obj.put("now", d1 == null ? 0 : d1);
			// 去年同期能耗
			st.setYear(startTime.getYear() - 1);
			et.setYear(endTime.getYear() - 1);
			vo.setStartTime(st);
			vo.setEndTime(et);
			Double d2 = getSumByCondition(vo);
			obj.put("before", d2 == null ? 0 : d2);
			ls.add(obj);
		}
		return ls;
	}
	
	/**
	 * vo获取某一电表并统计该电表在某一时间段内的总能耗
	 * @param vo
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月15日
	 */
	@Override
	public Double getSumByCondition(ConsumeDataVo vo) {
		if(vo.getStoreId() == null) vo.setStoreId(0);
		if(vo.getAreaId() == null) vo.setAreaId(0);
		if(vo.getObjectType() == null) vo.setObjectType(0);
		if(vo.getEnergyType() == null) vo.setEnergyType(0);
		List<ConsumeDto> consumeDto = getConsumesByCondition(vo);
		if(consumeDto.size() == 0) return null;
		ConsumeDataVo dataVo = new ConsumeDataVo();
		dataVo.setId(consumeDto.get(0).getId());
		dataVo.setEndTime(vo.getEndTime());
		dataVo.setStartTime(vo.getStartTime());
		List<ConsumeData> list = consumeDataMapper.getListByIdAndTime(dataVo);
		Double sum = 0.0;
		for(ConsumeData data : list) {
			
			Double num = data.getDirect();
			Double count = data.getIndirect();
			Double cv = num == null ? (count == null ? 0 : count) : num;
			sum = MathUtil.add(sum, cv);
		}
		return sum;
	}
	
}