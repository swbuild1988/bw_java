package com.bandweaver.tunnel.service.mam.measobj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSIService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.dao.mam.MeasObjSIMapper;
@Service
public class MeasObjSIServiceImpl implements MeasObjSIService {

	@Autowired
	private MeasObjSIMapper measObjSIMapper;
	
	@Override
	public MeasObjSI getMeasObjSI(Integer id) {
		return measObjSIMapper.getMeasObjSI(id);
	}

	@Override
	public List<MeasObjSI> getAllMeasObjSIs() {
		return measObjSIMapper.getAllMeasObjSIs();
	}

}
