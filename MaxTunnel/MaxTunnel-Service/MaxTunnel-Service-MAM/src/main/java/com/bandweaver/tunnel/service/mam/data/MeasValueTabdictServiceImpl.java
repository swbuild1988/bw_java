package com.bandweaver.tunnel.service.mam.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueTabdictService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueTabdict;
import com.bandweaver.tunnel.dao.mam.MeasValueTabdictMapper;

@Service
public class MeasValueTabdictServiceImpl implements MeasValueTabdictService {

	@Autowired
	private MeasValueTabdictMapper measValueTabdictMapper;

	@Override
	public void addMeasValueTabdict(MeasValueTabdict record) {
		measValueTabdictMapper.insert(record);
	}

	@Override
	public void addMeasValueTabdictBatch(List<MeasValueTabdict> list) {
		measValueTabdictMapper.addMeasValueTabdictBatch(list);
	}

	@Override
	public MeasValueTabdict getMeasValueTabdictById(int id) {
		return measValueTabdictMapper.selectByPrimaryKey(id);
	}
	
}
