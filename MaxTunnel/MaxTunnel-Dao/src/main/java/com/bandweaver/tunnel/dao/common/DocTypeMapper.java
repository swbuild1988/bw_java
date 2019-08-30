package com.bandweaver.tunnel.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.DocTypeDto;
import com.bandweaver.tunnel.common.biz.pojo.DocType;
import com.bandweaver.tunnel.common.biz.vo.DocTypeVo;

public interface DocTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(DocType type);
    
    DocType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DocType type);

	List<DocType> getList();

	List<DocTypeDto> getDtoByCondition(DocTypeVo vo);

	DocTypeDto getDtoById(@Param("id")Integer id);

	void deleteBatch(List<Integer> list);

}