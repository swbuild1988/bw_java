package com.bandweaver.tunnel.service.mam.data;

import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueAIService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.dao.mam.MeasValueAIMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class MeasValueAIServiceImpl implements MeasValueAIService {
    @Autowired
    private MeasValueAIMapper measValueAIMapper;

    @Override
    public int addMeasValueAI(MeasValueAI measValueAI) {
        return measValueAIMapper.addMeasValueAI(measValueAI);
    }

    @Override
    public int addMeasValueAIBatch(List<MeasValueAI> list) {
        return measValueAIMapper.addMeasValueAIBatch(list);
    }

    @Override
    public List<MeasValueAI> getListByObjectAndTime(Integer objectId, Date startTime, Date endTime) {
        List<MeasValueAI> list = measValueAIMapper.getListByObjectIdAndTime(objectId, startTime, endTime);
        return list == null ? Collections.EMPTY_LIST : list;
    }
}
