package com.bandweaver.tunnel.service.mam.measobj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjAIDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjAIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjAIVo;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjAIMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MeasObjAIServiceImpl implements MeasObjAIService{
    @Autowired
    private MeasObjAIMapper measObjAIMapper;
    @Autowired
    private MeasObjService measObjService;

    @Override
    public int add(MeasObjAI record) {
        return measObjAIMapper.insert(record);
    }

    @Override
    public int update(MeasObjAI record) {
        return measObjAIMapper.update(record);
    }

    @Override
    public MeasObjAI getMeasObjAI(Integer id) {
        return measObjAIMapper.getMeasObjAI(id);
    }

    @Override
    public List<MeasObjAI> getAllMeasObjAIs() {
        return measObjAIMapper.getAllMeasObjAIs();
    }

	@Override
	public PageInfo<MeasObjAIDto> getByCondition(MeasObjVo vo) {
		List<MeasObjDto> moList = measObjService.getMeasObjByCondition(vo);
    	List<Integer> moIdList = new ArrayList<>();
    	for (MeasObjDto measObjDto : moList) {
    		moIdList.add(measObjDto.getId());
		}
    	
    	if(moIdList.isEmpty())
    		return new PageInfo<>(new ArrayList<>());
    	
    	MeasObjAIVo aiVo = new MeasObjAIVo();
    	aiVo.setIds(moIdList.isEmpty() ? null : moIdList);
    	aiVo.setPageNum(vo.getPageNum());
    	aiVo.setPageSize(vo.getPageSize());
    	aiVo.setMaxCv(vo.getMaxVal());
    	aiVo.setMinCv(vo.getMinVal());
    	
    	PageHelper.startPage(aiVo.getPageNum(), aiVo.getPageSize());
    	List<MeasObjAIDto> list = measObjAIMapper.dataGrid(aiVo);
    	LogUtil.debug("list:" + list);
    	PageInfo<MeasObjAIDto> pageInfo = new PageInfo<>(list);
    	LogUtil.debug("pageInfo:" + pageInfo);
		return pageInfo;
	}
}
