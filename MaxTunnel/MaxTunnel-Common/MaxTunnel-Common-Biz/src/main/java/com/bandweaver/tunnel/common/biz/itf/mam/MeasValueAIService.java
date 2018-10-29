package com.bandweaver.tunnel.common.biz.itf.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;

import java.util.Date;
import java.util.List;

public interface MeasValueAIService {
    int addMeasValueAI(MeasValueAI measValueAI);

    int addMeasValueAIBatch(List<MeasValueAI> measValueAIS);

    List<MeasValueAI> getListByObjectAndTime(Integer objectId, Date startTime, Date endTime);
}
