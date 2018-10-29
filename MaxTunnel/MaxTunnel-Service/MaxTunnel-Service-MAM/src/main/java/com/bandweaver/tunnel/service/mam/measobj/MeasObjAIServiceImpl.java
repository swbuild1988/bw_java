package com.bandweaver.tunnel.service.mam.measobj;

import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjAIService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.dao.mam.MeasObjAIMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
