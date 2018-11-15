package com.bandweaver.tunnel.service.oam.consume;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.oam.ConsumeDto;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;
import com.bandweaver.tunnel.dao.oam.ConsumeMapper;

@Service
public class ConsumeServiceImpl implements ConsumeService {

	@Autowired
	private ConsumeMapper consumeMapper;

	@Override
	public int insert(Consume consume) {
		return consumeMapper.insert(consume);
	}

	@Override
	public int update(Consume consume) {
		return consumeMapper.update(consume);
	}

	@Override
	public int delete(Integer id) {
		return consumeMapper.delete(id);
	}

	@Override
	public int addBatch(List<Consume> list) {
		return consumeMapper.addBatch(list);
	}

	@Override
	public Consume getConsumeById(Integer id) {
		return consumeMapper.getConsumeById(id);
	}

	@Override
	public ConsumeDto getConsumeDtoById(Integer id) {
		return consumeMapper.getConsumeDtoById(id);
	}

	@Override
	public List<ConsumeDto> getConsumesByCondition(Consume consume) {
		return consumeMapper.getConsumesByCondition(consume);
	}

}