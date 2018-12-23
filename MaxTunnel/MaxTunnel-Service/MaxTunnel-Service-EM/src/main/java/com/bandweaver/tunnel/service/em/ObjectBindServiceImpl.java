package com.bandweaver.tunnel.service.em;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.em.ObjectBindTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.itf.em.ObjectBindService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoService;
import com.bandweaver.tunnel.common.biz.pojo.em.ObjectBind;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.bandweaver.tunnel.dao.em.ObjectBindMapper;
@Service
public class ObjectBindServiceImpl implements ObjectBindService {

	@Autowired
	private ObjectBindMapper objectBindMapper;

	@Override
	public void add(ObjectBind objectBind) {
		objectBind.setCrtTime(DateUtil.getCurrentDate());
		objectBindMapper.insertSelective(objectBind);
	}

	@Override
	public List<JSONObject> getPlansByObject(Integer id) {
		List<ObjectBind> list = objectBindMapper.getListByObjectAndType(id,ObjectBindTypeEnum.PLAN.getValue());
		List<JSONObject> returnData = new ArrayList<>();
		for (ObjectBind objectBind : list) {
			JSONObject js = new JSONObject();
			js.put("id", objectBind.getBindId());
			ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(objectBind.getBindId());
			if(processTypeEnum == ProcessTypeEnum.NONE)
				continue;
			js.put("name",processTypeEnum.getName());
			returnData.add(js);
		}
		return returnData;
	}

	
	
	@Override
	public List<ObjectBind> getListByObjectAndType(Integer id, Integer type){
		List<ObjectBind> list = objectBindMapper.getListByObjectAndType(id,type);
		return list == null ? Collections.emptyList() : list ;
	}
	
}
