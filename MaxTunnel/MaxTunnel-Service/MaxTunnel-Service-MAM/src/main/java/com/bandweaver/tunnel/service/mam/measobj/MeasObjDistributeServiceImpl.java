package com.bandweaver.tunnel.service.mam.measobj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDistributeDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSIDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjDistributeService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDistribute;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjDistributeVo;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjSIVo;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjDistributeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MeasObjDistributeServiceImpl implements MeasObjDistributeService {
    @Autowired
    private MeasObjDistributeMapper measObjDistributeMapper;
    @Autowired
    private MeasObjService measObjService;

    @Override
    public int add(MeasObjDistribute record) {
        return measObjDistributeMapper.insert(record);
    }

    @Override
    public int update(MeasObjDistribute record) {
        return measObjDistributeMapper.update(record);
    }

    @Override
    public MeasObjDistribute getMeasObjDistribute(Integer id) {
        return measObjDistributeMapper.getMeasObjDistribute(id);
    }

    @Override
    public List<MeasObjDistribute> getAllMeasObjDistributes() {
        return measObjDistributeMapper.getAllMeasObjDistributes();
    }

	@Override
	public PageInfo<MeasObjDistributeDto> getByCondition(MeasObjVo vo) {
		List<MeasObjDto> moList = measObjService.getMeasObjByCondition(vo);
    	List<Integer> moIdList = new ArrayList<>();
    	for (MeasObjDto measObjDto : moList) {
    		moIdList.add(measObjDto.getId());
		}
    	
    	MeasObjDistributeVo disVo = new MeasObjDistributeVo();
    	disVo.setIds(moIdList);
    	disVo.setPageNum(vo.getPageNum());
    	disVo.setPageSize(vo.getPageSize());
    	disVo.setDcv(vo.getDcv());
    	disVo.setSpacePrecision(vo.getSpacePrecision());
    	
    	PageHelper.startPage(disVo.getPageNum(), disVo.getPageSize());
    	List<MeasObjDistributeDto> list = measObjDistributeMapper.dataGrid(disVo);
    	LogUtil.info("list:" + list);
    	PageInfo<MeasObjDistributeDto> pageInfo = new PageInfo<>(list);
    	LogUtil.info("pageInfo:" + pageInfo);
		return pageInfo;
	}
}
