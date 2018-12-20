package com.bandweaver.tunnel.service.em;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.constant.em.ObjectBindTypeEnum;
import com.bandweaver.tunnel.common.biz.itf.em.ObjectBindService;
import com.bandweaver.tunnel.common.biz.pojo.em.ObjectBind;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
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
	public List<ObjectBind> getPlansByObject(Integer id) {
		List<ObjectBind> list = objectBindMapper.getPlansByObject(id,ObjectBindTypeEnum.PLAN.getValue());
		return list == null ? Collections.emptyList() : list ;
	}

	@Override
	public List<ObjectBind> getVideosByObject(Integer id) {
		List<ObjectBind> list = objectBindMapper.getPlansByObject(id,ObjectBindTypeEnum.VIDEO.getValue());
		return list == null ? Collections.emptyList() : list ;
	}
	
	
}
