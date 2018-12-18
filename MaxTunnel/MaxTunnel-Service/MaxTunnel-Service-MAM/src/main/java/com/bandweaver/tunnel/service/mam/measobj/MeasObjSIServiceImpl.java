package com.bandweaver.tunnel.service.mam.measobj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDIDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSIDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjSIVo;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjSIMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class MeasObjSIServiceImpl implements MeasObjSIService {

	@Autowired
	private MeasObjSIMapper measObjSIMapper;
	@Autowired
	private MeasObjService measObjService;
	
	@Override
	public MeasObjSI getMeasObjSI(Integer id) {
		return measObjSIMapper.getMeasObjSI(id);
	}

	@Override
	public List<MeasObjSI> getAllMeasObjSIs() {
		return measObjSIMapper.getAllMeasObjSIs();
	}

	@Override
	public PageInfo<MeasObjSIDto> getByCondition(MeasObjVo vo) {
		List<MeasObjDto> moList = measObjService.getMeasObjByCondition(vo);
    	List<Integer> moIdList = new ArrayList<>();
    	for (MeasObjDto measObjDto : moList) {
    		moIdList.add(measObjDto.getId());
		}
    	
    	MeasObjSIVo siVo = new MeasObjSIVo();
    	siVo.setIds(moIdList);
    	siVo.setPageNum(vo.getPageNum());
    	siVo.setPageSize(vo.getPageSize());
    	siVo.setCv(vo.getCv().intValue());
    	
    	PageHelper.startPage(siVo.getPageNum(), siVo.getPageSize());
    	List<MeasObjSIDto> list = measObjSIMapper.dataGrid(siVo);
    	LogUtil.info("list:" + list);
    	PageInfo<MeasObjSIDto> pageInfo = new PageInfo<>(list);
    	LogUtil.info("pageInfo:" + pageInfo);
		return pageInfo;
	}

}
