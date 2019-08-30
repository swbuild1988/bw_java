package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.DocTypeDto;
import com.bandweaver.tunnel.common.biz.pojo.DocType;
import com.bandweaver.tunnel.common.biz.vo.DocTypeVo;

public interface DocTypeService {

	int deleteByPrimaryKey(Integer id);

    int insertSelective(DocType type);
    
    DocType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DocType type);

	List<DocType> getList();

	List<DocTypeDto> getDtoByCondition(DocTypeVo vo);

	DocTypeDto getDtoById(Integer id);

	void deleteBatch(List<Integer> list);
}
