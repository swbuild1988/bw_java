package com.bandweaver.tunnel.service.mam.data;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.SwitchEnum;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueDIService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.dao.mam.MeasValueDIMapper;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MeasValueDIServiceImpl implements MeasValueDIService {
    @Autowired
    private MeasValueDIMapper measValueDIMapper;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;

    @Override
    public int addMeasValueDI(MeasValueDI measValueDI) {
        return measValueDIMapper.addMeasValueDI(measValueDI);
    }

    @Override
    public List<MeasValueDI> getListByObjectAndTime(int objectId, Date startTime, Date endTime) {
        return measValueDIMapper.getListByObjectIdAndTime(objectId, startTime, endTime);
    }

	@Override
	public void addMeasValueDIBatch(List<MeasValueDI> list) {
		measValueDIMapper.addMeasValueDIBatch(list);
	}

	@Override
	public JSONObject getEchartsData(List<Integer> ids, Date startTime, Date endTime) {
		JSONObject result = new JSONObject();
		result.put("key", "开关量图表");
		
		List<JSONObject> list = new ArrayList<>();
		for (Integer id : ids) {
			MeasObj measObj = measObjModuleCenter.getMeasObj(id);
			if(measObj == null)
				continue;
			
			JSONObject js = new JSONObject();
			js.put("key", measObj.getName());
			int openCt = measValueDIMapper.getCountByCondition(id,startTime,endTime,SwitchEnum.OPEN.getValue());
			int clsCt = measValueDIMapper.getCountByCondition(id,startTime,endTime,SwitchEnum.CLOSE.getValue());
			js.put(SwitchEnum.OPEN.getName(), openCt);
			js.put(SwitchEnum.CLOSE.getName(), clsCt);
			js.put("总数", openCt + clsCt);
			list.add(js);
		}
		result.put("val", list);
		return result;
	}
}
