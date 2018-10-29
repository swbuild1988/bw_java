package com.bandweaver.tunnel.service.mam.mapping;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authc.LogoutAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.itf.mam.mapping.MeasObjMapService;
import com.bandweaver.tunnel.common.biz.pojo.mam.mapping.MeasObjMap;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjMapMapper;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
@Service
public class MeasObjMapServiceImpl implements MeasObjMapService {

	@Autowired
	private MeasObjMapMapper measObjMapMapper;
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;

	@Override
	public List<MeasObjMap> getByObjectIdAndInputValue(Integer objectId, Integer inputValue) {
		return measObjMapMapper.getByObjectIdAndInputValue(objectId,inputValue);
	}

	@Override
	public MeasObjMap getById(Integer id) {
		return measObjMapMapper.selectByPrimaryKey(id);
	}

	@Override
	public void add(MeasObjMap measObjMap) {
		measObjMapMapper.insertSelective(measObjMap);
	}

	@Override
	public void delete(Integer id) {
		measObjMapMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		measObjMapMapper.deleteBatch(list);
	}

	@Override
	public void update(MeasObjMap pojo) {
		measObjMapMapper.updateByPrimaryKeySelective(pojo);
	}

	@Override
	public void doAction(Integer objectId, Integer inputValue) {
		//1.根据objectId获取obj对象并更新cv值
		MeasObj measObj = measObjModuleCenter.getMeasObj(objectId);
		if(measObj == null) { 
			return;
		}
		
		DataType dataType = DataType.getEnum(measObj.getDatatypeId());
		if(DataType.DI == dataType) {
			MeasObjDI measObjDI = measObjModuleCenter.getMeasObjDI(objectId);
			measObjDI.setCV(inputValue == 1 ? true : false);
			measObjDI.setRefreshTime(new Date());
		}else if(DataType.SI == dataType) {
			MeasObjSI measObjSI = measObjModuleCenter.getMeasObjSI(objectId);
			measObjSI.setCV(inputValue);
			measObjSI.setRefreshTime(new Date());
		}
		
		//2.根据objectId和inputValue去映射表里查找映射到的objectId2
		List<MeasObjMap> list = getByObjectIdAndInputValue(objectId,inputValue);
		
		//3.执行相应动作
		for (MeasObjMap measObjMap : list) {
			Integer objectId2 = measObjMap.getObjectId2();
			Integer outputValue = measObjMap.getOutputValue();
			LogUtil.info("objectId : " + objectId + "映射objectId2：" + objectId2 + ",动作为：" + outputValue);
		}
		
	}
	
	
	
} 
