package com.bandweaver.tunnel.service.mam.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.report.MeasObjReportDto;
import com.bandweaver.tunnel.common.biz.dto.oam.EnergySimpleDto;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.report.MeasObjReportService;
import com.bandweaver.tunnel.common.biz.itf.oam.EnergyService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.report.MeasObjReport;
import com.bandweaver.tunnel.common.biz.pojo.oam.Energy;
import com.bandweaver.tunnel.common.biz.vo.mam.report.MeasObjReportVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjReportMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class MeasObjReportServiceImpl implements MeasObjReportService {
	
	@Autowired
	private MeasObjReportMapper measObjReportMapper;
	@Autowired
	private TunnelService tunnelService;
	@Autowired
	private EnergyService energyService;

	@Override
	public void add(MeasObjReport report) {
		measObjReportMapper.insertSelective(report);
	}

	@Override
	public MeasObjReport getByObjectIdAndStartTime(Integer objectId, Date startTime) {
		return measObjReportMapper.getByObjectIdAndStartTime(objectId,startTime);
	}

	@Override
	public List<MeasObjReportDto> getListByObjectIdAndTimeAndTimeType(Integer objectId, Date startTime, Date endTime, Integer timeType) {
		MeasObjReportVo vo = new MeasObjReportVo();
		vo.setObjectId(objectId);
		vo.setStartTime(startTime);
		vo.setEndTime(endTime);
		vo.setTimeType(timeType);
		return getByCondition(vo);
	}
	
	public List<MeasObjReportDto> getByCondition(MeasObjReportVo vo){
		return measObjReportMapper.getByCondition(vo);
	}

	@Override
	public PageInfo<MeasObjReportDto> dataGrid(MeasObjReportVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<MeasObjReportDto> list = getByCondition(vo);
		return new PageInfo<>(list);
	}
	
	@Override
	public int deleteBatch(Date time) {
		return measObjReportMapper.deleteBatch(time);
	}

	@Override
	public void updateByPrimaryKeySelective(MeasObjReport report) {
		measObjReportMapper.updateByPrimaryKeySelective(report);
	}
	
	
	
}
