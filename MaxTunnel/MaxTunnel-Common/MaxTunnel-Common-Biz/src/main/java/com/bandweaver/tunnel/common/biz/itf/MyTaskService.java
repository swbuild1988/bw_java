package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.MyTaskDto;
import com.bandweaver.tunnel.common.biz.vo.MyTaskVo;
import com.github.pagehelper.PageInfo;

public interface MyTaskService {

	/**
	 * @Description: 我的任务列表
	 * @param @param vo
	 * @param @return   
	 * @return PageInfo<MyTaskDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月18日
	 */
	PageInfo<MyTaskDto> taskDataGrid(MyTaskVo vo);
	
	/**
	 * @Description: 我的申请列表
	 * @param @param vo
	 * @param @return   
	 * @return PageInfo<MyTaskDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月18日
	 */
	PageInfo<MyTaskDto> applyDataGrid(MyTaskVo vo);
	
	List<MyTaskDto> getDtoListByCondition(MyTaskVo vo);

	/**
	 * @Description: 获取待处理任务总数
	 * @param @param staffId
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月24日
	 */
	int getMsgCount(Integer staffId,Integer status);


}
