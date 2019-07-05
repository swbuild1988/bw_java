package com.bandweaver.tunnel.service.omm.inspection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.omm.MultiFileService;
import com.bandweaver.tunnel.common.biz.pojo.omm.MultiFile;
import com.bandweaver.tunnel.dao.omm.MultiFileMapper;

@Service
public class MultiFileServiceImpl implements MultiFileService {

	@Autowired
	private MultiFileMapper multiFileMapper;

	@Override
	public int add(MultiFile file) {
		return multiFileMapper.add(file);
	}

	@Override
	public int update(MultiFile file) {
		return multiFileMapper.update(file);
	}

	@Override
	public int delete(Integer relationId, Integer type) {
		return multiFileMapper.delete(relationId, type);
	}

	@Override
	public MultiFile getMultiFileById(Integer id) {
		return multiFileMapper.getMultiFileById(id);
	}

	@Override
	public List<MultiFile> selectByCondition(Integer id, Integer relationId, Integer type) {
		return multiFileMapper.selectByCondition(id, relationId, type);
	}
	
}
