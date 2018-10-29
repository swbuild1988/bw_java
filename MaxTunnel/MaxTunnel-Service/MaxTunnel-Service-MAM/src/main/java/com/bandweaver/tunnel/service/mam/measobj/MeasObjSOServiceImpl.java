package com.bandweaver.tunnel.service.mam.measobj;

import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.itf.mam.locator.LocatorService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSOService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSO;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjSOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasObjSOServiceImpl implements MeasObjSOService {
    @Autowired
    private MeasObjSOMapper measObjSOMapper;
    @Autowired
    private LocatorService locatorService;


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
}
