package com.bandweaver.tunnel.dao.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.CableContractDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.CableContract;
import com.bandweaver.tunnel.common.biz.vo.oam.CableContractVo;

public interface CableContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(CableContract record);

    int insertSelective(CableContract record);

    CableContract selectByPrimaryKey(String id);
    
    CableContractDto getDtoById(String id);

    int updateByPrimaryKeySelective(CableContract record);

    int updateByPrimaryKey(CableContract record);

	List<CableContractDto> getByCondition(CableContractVo vo);
}