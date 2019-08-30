package com.bandweaver.tunnel.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.DocTypeDto;
import com.bandweaver.tunnel.common.biz.itf.DocTypeService;
import com.bandweaver.tunnel.common.biz.pojo.DocType;
import com.bandweaver.tunnel.common.biz.vo.DocTypeVo;
import com.bandweaver.tunnel.dao.common.DocTypeMapper;

@Service
public class DocTypeServiceImpl implements DocTypeService {

	@Autowired
	private DocTypeMapper docTypeMapper;
	
	@Transactional
	@Override
	public int deleteByPrimaryKey(Integer id) {
		DocType type = selectByPrimaryKey(id);
		if(type.getParentId() == 0) {
			DocTypeVo vo = new DocTypeVo();
			vo.setParentId(type.getId());
			List<DocTypeDto> dtos = getDtoByCondition(vo);
			if(dtos != null && dtos.size() > 0) {
				List<Integer> list = new ArrayList<>();
				dtos.forEach(a -> list.add(a.getId()));
				deleteBatch(list);
			}
		}
		return docTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(DocType type) {
		type.setCrtTime(new Date());
		if(type.getParentId() == null) type.setParentId(0);
		return docTypeMapper.insertSelective(type);
	}

	@Override
	public DocType selectByPrimaryKey(Integer id) {
		return docTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DocType type) {
		if(type.getParentId() == null) type.setParentId(0);
		return docTypeMapper.updateByPrimaryKeySelective(type);
	}

	@Override
	public List<DocType> getList() {
		return docTypeMapper.getList();
	}

	@Override
	public List<DocTypeDto> getDtoByCondition(DocTypeVo vo) {
		return docTypeMapper.getDtoByCondition(vo);
	}

	@Override
	public DocTypeDto getDtoById(Integer id) {
		return docTypeMapper.getDtoById(id);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		docTypeMapper.deleteBatch(list);
	}

}
