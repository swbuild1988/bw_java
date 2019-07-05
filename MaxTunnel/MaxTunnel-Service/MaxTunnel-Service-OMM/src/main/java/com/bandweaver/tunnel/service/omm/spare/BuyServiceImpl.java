package com.bandweaver.tunnel.service.omm.spare;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.omm.BuyDto;
import com.bandweaver.tunnel.common.biz.itf.omm.BuyService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.Buy;
import com.bandweaver.tunnel.common.biz.vo.omm.BuyVo;
import com.bandweaver.tunnel.dao.omm.BuyMapper;

@Service
public class BuyServiceImpl implements BuyService {

	@Autowired
	private BuyMapper buyMapper;
	
	@Override
	public int add(Buy buy) {
		return buyMapper.add(buy);
	}

	@Override
	public int update(Buy buy) {
		return buyMapper.update(buy);
	}

	@Override
	public int delete(Integer id) {
		return buyMapper.delete(id);
	}

	@Override
	public BuyDto getDtoById(Integer id) {
		return buyMapper.getDtoById(id);
	}

	@Override
	public List<BuyDto> selectByCondition(BuyVo vo) {
		return buyMapper.selectByCondition(vo);
	}

	@Override
	public ListPageUtil<BuyDto> dataGrid(BuyVo vo) {
		List<BuyDto> list = selectByCondition(vo);
		ListPageUtil<BuyDto> page = new ListPageUtil<>(list, vo.getPageNum(), vo.getPageSize());
		return page;
	}
	
}
