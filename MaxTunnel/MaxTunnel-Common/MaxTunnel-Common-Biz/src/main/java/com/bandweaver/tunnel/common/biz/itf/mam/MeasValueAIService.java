package com.bandweaver.tunnel.common.biz.itf.mam;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;

import java.util.Date;
import java.util.List;


public interface MeasValueAIService {
    int addMeasValueAI(MeasValueAI measValueAI);

    int addMeasValueAIBatch(List<MeasValueAI> measValueAIS);

    List<MeasValueAI> getListByObjectAndTime(Integer objectId, Date startTime, Date endTime);

	int getCountBymoIdsAndTime(List<Integer> moIdList, Date startTime, Date endTime);

	List<MeasValueAI> pageQuery(Integer start, Integer end, List<Integer> moIdList, Date startTime, Date endTime);

	List<MeasValueAI> getListByMoIdsAndTime(List<Integer> list,Date startTime,Date endTime);

	JSONObject getEchartsData(List<Integer> ids, Date startTime, Date endTime);

	List<MeasValueAI> getListByTime(Date dayBegin);
}
