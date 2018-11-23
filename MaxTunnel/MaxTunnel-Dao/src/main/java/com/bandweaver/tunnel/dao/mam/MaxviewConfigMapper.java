package com.bandweaver.tunnel.dao.mam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.mam.MaxviewConfig;
import com.bandweaver.tunnel.common.biz.vo.mam.MaxviewConfigVo;

public interface MaxviewConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MaxviewConfig record);

    int insertSelective(MaxviewConfig record);

    MaxviewConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MaxviewConfig record);

    int updateByPrimaryKey(MaxviewConfig record);

	void deleteBatch(List<Integer> list);

	List<MaxviewConfig> getByCondition(MaxviewConfigVo vo);
}