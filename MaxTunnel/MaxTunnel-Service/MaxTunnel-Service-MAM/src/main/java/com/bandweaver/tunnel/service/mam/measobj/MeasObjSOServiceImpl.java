package com.bandweaver.tunnel.service.mam.measobj;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjSODto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSOService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
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
	public ListPageUtil<MeasObjSODto> getMeasObjSOByCondition(MeasObjVo vo) {
    	List<MeasObjSODto> list = measObjSOMapper.getMeasObjSOByCondition(vo);
    	ListPageUtil<MeasObjSODto> pageInfo = new ListPageUtil<>(list, vo.getPageNum(), vo.getPageSize());
		return pageInfo;
	}
}
