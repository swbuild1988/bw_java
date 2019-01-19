package com.bandweaver.tunnel.service.omm.spare;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareDto;
import com.bandweaver.tunnel.common.biz.itf.omm.SpareService;
import com.bandweaver.tunnel.common.biz.pojo.omm.Spare;
import com.bandweaver.tunnel.common.biz.vo.omm.SpareVo;
import com.bandweaver.tunnel.dao.omm.SpareMapper;
import com.bandweaver.tunnel.dao.omm.SpareOutMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SpareServiceImpl implements SpareService {
    @Autowired
    private SpareMapper spareMapper;
    @Autowired
    private SpareOutMapper spareOutMapper;

	@Override
	public int add(Spare spare) {
		return spareMapper.add(spare);
	}

	@Override
	public int update(Spare spare) {
		return spareMapper.update(spare);
	}

	@Override
	public int addBatch(List<Spare> list) {
		return spareMapper.addBatch(list);
	}

	@Override
	public List<SpareDto> getSpareDtoByStatus(Boolean status) {
		return spareMapper.getSpareDtoByStatus(status);
	}

	@Override
	public PageInfo<SpareDto> dataGrid(SpareVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<SpareDto> list = spareMapper.getSpareDtoByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public SpareDto getSpareDtoById(Integer id) {
		return spareMapper.getSpareDtoById(id);
	}

	@Override
	@Transactional
	public int deleteByIds(List<Integer> list) {
		//删除备品表记录
		int i = spareMapper.deleteByIds(list);
		//删除出库表记录
		spareOutMapper.deleteByIds(list);
		return i;
	}

	@Override
	public int getCountByTypeIdAndStatus(Boolean status, Integer typeId) {
		return spareMapper.getCountByTypeIdAndStatus(status,typeId);
	}

	@Override
	public int getCountBystatus(Boolean status) {
		return spareMapper.getCountBystatus(status);
	}

}
