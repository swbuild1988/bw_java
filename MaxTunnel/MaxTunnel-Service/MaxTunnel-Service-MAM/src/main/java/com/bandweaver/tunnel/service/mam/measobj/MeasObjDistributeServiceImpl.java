package com.bandweaver.tunnel.service.mam.measobj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjDistributeService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDistribute;
import com.bandweaver.tunnel.dao.mam.MeasObjDistributeMapper;

@Service
public class MeasObjDistributeServiceImpl implements MeasObjDistributeService {
    @Autowired
    private MeasObjDistributeMapper measObjDistributeMapper;

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
}
