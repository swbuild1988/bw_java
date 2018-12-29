package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.vo.AreaVo;
import com.github.pagehelper.PageInfo;

public interface AreaService {

	/**
	 * @Description: 添加区域
	 * @param @param area   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	void addArea(Area area);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	void addAreaBatch(List<Area> list);

	/**
	 * @Description: 获取所有区域列表
	 * @param @return   
	 * @return List<AreaDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	List<AreaDto> getList();


	/**
	 * @Description: 条件查询列表
	 * @param @param areaVo
	 * @param @return   
	 * @return List<AreaDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	List<AreaDto> getAreasByCondition(AreaVo areaVo);

	/**
	 * @Description: 通过id查询区域
	 * @param @param id
	 * @param @return   
	 * @return AreaDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月14日
	 */
	AreaDto getAreasById(Integer id);

	/**
	 * @Description: 通过管廊id查询区域列表
	 * @param @param id
	 * @param @return   
	 * @return AreaDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	List<AreaDto> getAreasByTunnelId(Integer id);

	/**
	 * @Description: 分页
	 * @param @param areaVo
	 * @param @return   
	 * @return PageInfo<AreaDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月22日
	 */
	PageInfo<AreaDto> dataGrid(AreaVo areaVo);

	void update(Area area);

	void delete(Integer id);

	void deleteBatch(List<Integer> list);

	Area getByName(String name);

	Area getByTunnelAndSN(Integer tunnelId, String sn);

}
