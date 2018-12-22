package com.bandweaver.tunnel.service.mam.measobj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDIDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjDIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjDIVo;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjDIMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class MeasObjDIServiceImpl implements MeasObjDIService{

	@Autowired
	private MeasObjService measObjService;
	@Autowired
	private MeasObjDIMapper measObjDIMapper;
	
	@Override
	public PageInfo<MeasObjDIDto> getByCondition(MeasObjVo vo) {
		List<MeasObjDto> moList = measObjService.getMeasObjByCondition(vo);
    	List<Integer> moIdList = new ArrayList<>();
    	for (MeasObjDto measObjDto : moList) {
    		moIdList.add(measObjDto.getId());
		}
    	
    	MeasObjDIVo diVo = new MeasObjDIVo();
    	diVo.setIds(moIdList);
    	diVo.setPageNum(vo.getPageNum());
    	diVo.setPageSize(vo.getPageSize());
    	diVo.setCv(vo.getCv() == 1 ? true :false);
    	
    	PageHelper.startPage(diVo.getPageNum(), diVo.getPageSize());
    	List<MeasObjDIDto> list = measObjDIMapper.dataGrid(diVo);
    	LogUtil.info("list:" + list);
    	PageInfo<MeasObjDIDto> pageInfo = new PageInfo<>(list);
    	LogUtil.info("pageInfo:" + pageInfo);
		return pageInfo;
	}

}
