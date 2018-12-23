package com.bandweaver.tunnel.service.omm.inspection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.omm.CheckingPointDto;
import com.bandweaver.tunnel.common.biz.itf.omm.CheckingPointService;
import com.bandweaver.tunnel.common.biz.pojo.omm.CheckingPoint;
import com.bandweaver.tunnel.common.biz.vo.omm.CheckingPointVo;
import com.bandweaver.tunnel.dao.omm.CheckingPointMapper;

@Service
public class CheckingPointServiceImpl implements CheckingPointService {

	@Autowired
	private CheckingPointMapper checkingPointMapper;

	@Override
	public void addCheckingPoint(CheckingPoint checkingPoint) {
		checkingPointMapper.insert(checkingPoint);
	}

	@Override
	public List<CheckingPointDto> getCheckingPointListByConditon(CheckingPointVo vo) {
		return checkingPointMapper.getCheckingPointListByConditon(vo);
	}

	@Override
	public void deleteCheckingPointById(Integer id) {
		checkingPointMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<CheckingPointDto> getAllCheckingPointList() {
		return checkingPointMapper.getCheckingPointListByConditon(null);
	}

	@Override
	public CheckingPointDto getCheckingPointById(Integer id) {
		return checkingPointMapper.getCheckingPointById(id);
	}

	@Override
	public void addCheckingPointBatch(List<CheckingPoint> list) {
		checkingPointMapper.addCheckingPointBatch(list);
	}
}
