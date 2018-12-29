package com.bandweaver.tunnel.dao.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.oam.CableSection;

public interface CableSectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CableSection record);

    int insertSelective(CableSection record);

    CableSection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CableSection record);

    int updateByPrimaryKey(CableSection record);

	void addBatch(List<CableSection> list);

	int getCountBySectionId(Integer id);

	List<Integer> getSectionIdsByCableId(String cableId);

	void deleteByCableId(String id);

}