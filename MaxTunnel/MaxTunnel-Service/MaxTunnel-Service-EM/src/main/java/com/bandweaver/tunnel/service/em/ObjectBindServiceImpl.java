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
	@Autowired
	private MeasObjService measObjService;
	@Autowired
	private VideoService videoService;

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
	public List<VideoDto> getVideosByObject(Integer id) {
		List<ObjectBind> list = getListByObjectAndType(id, ObjectBindTypeEnum.VIDEO.getValue());
		List<VideoDto> returnData = new ArrayList<>();
		if(list.isEmpty()) {
			//默认查询这个section的所有视频
			MeasObj measObj = measObjService.get(id);
			if(StringTools.isNullOrEmpty(measObj))
				throw new BandWeaverException("监测对象" + id + "不存在");
			Integer sectionId = measObj.getSectionId();
			
			MeasObjVo vo = new MeasObjVo();
			vo.setSectionId(sectionId);
			vo.setObjtypeId(ObjectType.VIDEO.getValue());
			List<MeasObjDto> objectList = measObjService.getMeasObjByCondition(vo);
			if(objectList.isEmpty()) {
				returnData.add(new VideoDto());
			}
			
			for (MeasObjDto measObjDto : objectList) {
				VideoDto videoDto = videoService.getVideoDto(measObjDto.getId());
				if(StringTools.isNullOrEmpty(videoDto))
					continue;
				returnData.add(videoDto);
			}
		}
		
		for (ObjectBind objectBind : list) {
			Integer videoId = objectBind.getBindId();
			VideoDto videoDto = videoService.getVideoDto(videoId);
			if(StringTools.isNullOrEmpty(videoDto))
				continue;
			returnData.add(videoDto);
		}
		
		return returnData;
	}
	
	
	public List<ObjectBind> getListByObjectAndType(Integer id, Integer type){
		List<ObjectBind> list = objectBindMapper.getListByObjectAndType(id,type);
		return list == null ? Collections.emptyList() : list ;
	}
	
}
