package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.omm.CheckingPointDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.CheckingPoint;
import com.bandweaver.tunnel.common.biz.vo.omm.CheckingPointVo;

public interface CheckingPointService {

	/**
	 * @Description: 添加巡检点
	 * @param @param checkingPoint   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月4日
	 */
	void addCheckingPoint(CheckingPoint checkingPoint);

	/**
	 * @Description: 条件查询
	 * @param @param dto
	 * @param @return   
	 * @return List<CheckingPointDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月4日
	 */
	List<CheckingPointDto> getCheckingPointListByConditon(CheckingPointVo vo);

	/**
	 * @Description: 根据id删除巡检点
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月4日
	 */
	void deleteCheckingPointById(Integer id);

	/**
	 * @Description: 获取所有巡检点列表
	 * @param @return   
	 * @return List<CheckingPointDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	List<CheckingPointDto> getAllCheckingPointList();

	/**
	 * @Description: 根据id查询巡检点
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	CheckingPointDto getCheckingPointById(Integer id);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月12日
	 */
	void addCheckingPointBatch(List<CheckingPoint> list);

}
