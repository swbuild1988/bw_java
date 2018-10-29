package com.bandweaver.tunnel.dao.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.PositionDto;
import com.bandweaver.tunnel.common.biz.pojo.Position;
import com.bandweaver.tunnel.common.biz.vo.PositionVo;

public interface PositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

	/**
	 * @Description: 获取所有职位列表
	 * @param @return   
	 * @return List<Position>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月25日
	 */
	List<Position> getList();

	List<PositionDto> getDtoListByCondition(PositionVo vo);

	void deleteBatch(List<Integer> list);
}