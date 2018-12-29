package com.bandweaver.tunnel.service.mam.data;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueAIService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.mam.MeasValueAIMapper;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;

import org.apache.shiro.authc.LogoutAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class MeasValueAIServiceImpl implements MeasValueAIService {
    @Autowired
    private MeasValueAIMapper measValueAIMapper;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;

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
        return list == null ? Collections.emptyList() : list;
    }

	@Override
	public int getCountBymoIdsAndTime(List<Integer> moIdList, Date startTime, Date endTime) {
		return measValueAIMapper.getCountBymoIdsAndTime(moIdList,startTime,endTime);
	}

	@Override
	public List<MeasValueAI> pageQuery(Integer start, Integer end, List<Integer> moIdList, Date startTime, Date endTime) {
		List<MeasValueAI> list = measValueAIMapper.pageQuery(start,end,moIdList,startTime,endTime);
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	public List<MeasValueAI> getListByMoIdsAndTime(List<Integer> ids, Date startTime, Date endTime) {
		List<MeasValueAI> list = measValueAIMapper.getListByMoIdsAndTime(ids,startTime,endTime);
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	public JSONObject getEchartsData(List<Integer> ids, Date startTime, Date endTime) {
		
		long stime = System.currentTimeMillis();
		//get X
		long x_start = System.currentTimeMillis();
		List<Date> dateList = measValueAIMapper.getX(startTime,endTime);
		long x_end = System.currentTimeMillis();
		System.out.println("x:" + (x_end - x_start) + "ms");
		
		long y_start = System.currentTimeMillis();
		List<JSONObject> jsList = new ArrayList<>();
		for (Integer id : ids) {
			MeasObj measObj = measObjModuleCenter.getMeasObj(id);
			if(measObj == null)
				continue;
			JSONObject js = new JSONObject();
			js.put("key",measObj.getName());
			
			List<Double> valList = new ArrayList<>();
			for (Date dt : dateList) {
				List<MeasValueAI> ls = getListByObjectAndTime(id, dt, dt);
				if(ls == null || ls.isEmpty())
					valList.add(0.00);
				else
					valList.add(ls.get(0).getCv());
			}
			js.put("val", valList);
			jsList.add(js);
		}
		long y_end = System.currentTimeMillis();
		System.out.println("y:" + (y_end - y_start) + "ms");
		System.out.println("total:" + (y_end - stime) + "ms");
		JSONObject result = new JSONObject();
		result.put("key", dateList);
		result.put("val", jsList);
		return result;
	}
}
