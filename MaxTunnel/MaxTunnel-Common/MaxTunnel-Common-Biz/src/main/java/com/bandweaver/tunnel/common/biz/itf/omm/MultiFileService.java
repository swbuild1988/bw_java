package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;
import com.bandweaver.tunnel.common.biz.pojo.omm.MultiFile;

public interface MultiFileService {

	int add(MultiFile file);
	
	int update(MultiFile file);
	
	int delete(Integer relationId, Integer type);
	
	MultiFile getMultiFileById(Integer id);
	
	List<MultiFile> selectByCondition(Integer id, Integer relationId, Integer type);
}
