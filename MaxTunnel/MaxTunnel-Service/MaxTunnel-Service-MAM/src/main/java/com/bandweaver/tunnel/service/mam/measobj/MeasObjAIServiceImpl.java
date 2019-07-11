package com.bandweaver.tunnel.service.mam.measobj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjAIDto;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjAIService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjAIMapper;

@Service
public class MeasObjAIServiceImpl implements MeasObjAIService{
    @Autowired
    private MeasObjAIMapper measObjAIMapper;

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
	public ListPageUtil<MeasObjAIDto> getMeasObjAIByCondition(MeasObjVo vo) {
    	List<MeasObjAIDto> list = measObjAIMapper.getMeasObjAIByCondition(vo);
    	LogUtil.debug("list:" + list);
    	ListPageUtil<MeasObjAIDto> pageInfo = new ListPageUtil<>(list, vo.getPageNum(), vo.getPageSize());
    	LogUtil.debug("pageInfo:" + pageInfo);
		return pageInfo;
	}
}
