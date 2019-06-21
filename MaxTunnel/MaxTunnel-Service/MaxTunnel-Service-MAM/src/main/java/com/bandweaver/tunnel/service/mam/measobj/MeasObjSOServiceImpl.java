package com.bandweaver.tunnel.service.mam.measobj;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSODto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSOService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjSOVo;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjSOMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasObjSOServiceImpl implements MeasObjSOService {
    @Autowired
    private MeasObjSOMapper measObjSOMapper;
    @Autowired
	private MeasObjService measObjService;


    @Override
    public int add(MeasObjSO record) {
        return measObjSOMapper.insert(record);
    }

    @Override
    public int update(MeasObjSO record) {
        return measObjSOMapper.update(record);
    }

    @Override
    public MeasObjSO getMeasObjSO(Integer id) {
        return measObjSOMapper.getMeasObjSO(id);
    }

    @Override
    public List<MeasObjSO> getAllMeasObjSOs() {
        return measObjSOMapper.getAllMeasObjSOs();
    }

	@Override
	public PageInfo<MeasObjSODto> dataGrid(MeasObjVo vo) {
		List<MeasObjDto> moList = measObjService.getMeasObjByCondition(vo);
    	List<Integer> moIdList = new ArrayList<>();
    	for (MeasObjDto measObjDto : moList) {
    		moIdList.add(measObjDto.getId());
		}
    	LogUtil.info("moIdList.size:" + moIdList.size());
    	LogUtil.info("moIdList:" + moIdList);
    	MeasObjSOVo soVo = new MeasObjSOVo();
    	soVo.setIds(moIdList.isEmpty() ? null : moIdList);
    	soVo.setPageNum(vo.getPageNum());
    	soVo.setPageSize(vo.getPageSize());
    	LogUtil.info("soVo:" + soVo);
    	PageHelper.startPage(soVo.getPageNum(), soVo.getPageSize());
    	List<MeasObjSODto> list = measObjSOMapper.getByCondition(soVo);
    	PageInfo<MeasObjSODto> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
}
