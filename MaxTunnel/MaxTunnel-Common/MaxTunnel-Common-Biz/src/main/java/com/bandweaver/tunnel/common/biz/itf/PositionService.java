package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.PositionDto;
import com.bandweaver.tunnel.common.biz.pojo.Position;
import com.bandweaver.tunnel.common.biz.vo.PositionVo;
import com.github.pagehelper.PageInfo;

public interface PositionService {

	/**
	 * @Description: 获取所有职位列表
	 * @param @return   
	 * @return List<Position>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月25日
	 */
	List<Position> getList();

	/**
	 * @Description: 通过id查询职位
	 * @param @param id
	 * @param @return   
	 * @return Position  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月25日
	 */
	Position getPositionById(Integer id);

	void add(Position pojo);

	void delete(Integer id);

	void update(Position position);

	PageInfo<PositionDto> dataGrid(PositionVo vo);

	void deleteBatch(List<Integer> list);

}
