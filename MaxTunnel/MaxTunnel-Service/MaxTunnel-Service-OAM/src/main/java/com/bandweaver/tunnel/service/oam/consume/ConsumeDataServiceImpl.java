package com.bandweaver.tunnel.service.oam.consume;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeDataService;
import com.bandweaver.tunnel.common.biz.pojo.oam.ConsumeData;
import com.bandweaver.tunnel.common.biz.vo.oam.ConsumeDataVo;
import com.bandweaver.tunnel.dao.oam.ConsumeDataMapper;

@Service
public class ConsumeDataServiceImpl implements ConsumeDataService {

	@Autowired
	private ConsumeDataMapper consumeDataMapper;
	@Override
	public int insert(ConsumeData consumeData) {
		return consumeDataMapper.insert(consumeData);
	}

	@Override
	public int addBatch(List<ConsumeData> list) {
		return consumeDataMapper.addBatch(list);
	}

	@Override
	public int deleteBatch(Date date) {
		return consumeDataMapper.deleteBatch(date);
	}

	@Override
	public List<ConsumeData> getListByIdAndTime(ConsumeDataVo vo) {
		return consumeDataMapper.getListByIdAndTime(vo);
	}

}