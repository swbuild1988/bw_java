package com.bandweaver.tunnel.dao.omm;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.bandweaver.tunnel.common.biz.pojo.omm.MultiFile;

public interface MultiFileMapper {

	int add(MultiFile file);
	
	int update(MultiFile file);
	
	int delete(@Param("relationId") Integer relationId, @Param("type") Integer type);
	
	MultiFile getMultiFileById(Integer id);
	
	List<MultiFile> selectByCondition(@Param("id") Integer id, @Param("relationId") Integer relationId,
			@Param("type") Integer type);
}
