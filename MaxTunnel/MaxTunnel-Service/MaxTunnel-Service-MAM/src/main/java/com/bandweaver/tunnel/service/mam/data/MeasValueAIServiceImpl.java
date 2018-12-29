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
import java.util.stream.Collectors;

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
		
		//获取x轴时间集合
		List<Date> dateList = measValueAIMapper.getX(startTime,endTime);
		//获取这个时间段内的所有数据
		List<MeasValueAI> ls = getListByMoIdsAndTime(ids, startTime, endTime);
		
		List<JSONObject> jsList = new ArrayList<>();
		for (Integer id : ids) {
			
			MeasObj measObj = measObjModuleCenter.getMeasObj(id);
			if(measObj == null)
				continue;
			JSONObject js = new JSONObject();
			js.put("key",measObj.getName() + measObj.getId());
			
			List<Double> valList = new ArrayList<>();
			for (Date dt : dateList) {
				List<MeasValueAI> li = ls.stream().filter(m -> m.getObjectId().intValue() == id.intValue() 
						&& m.getTime().getTime() == dt.getTime()).collect(Collectors.toList());
				if(li == null || li.isEmpty())
					valList.add(0.00);
				else
					valList.add(li.get(0).getCv());
			}
			js.put("val", valList);
			jsList.add(js);
		}
		
		JSONObject result = new JSONObject();
		result.put("key", dateList);
		result.put("val", jsList);
		return result;
	}
}
