package com.bandweaver.tunnel.common.biz.itf;

import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.OperationLog;
import com.bandweaver.tunnel.common.biz.vo.OperationLogVo;
import com.github.pagehelper.PageInfo;

public interface OperationLogService {

	/**
	 * @Description: 保存日志
	 * @param @param optlog
	 * @param @return
	 * @return int
	 * @throws @author shaosen
	 * @date 2018年5月22日
	 */
	int save(OperationLog optlog);

	/**
	 * @Description: 更新操作日志结果
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月2日
	 */
	void updateById(String result,String id);

	/**
	 * @Description: 分页查询
	 * @param @param vo
	 * @param @return   
	 * @return PageInfo<OperationLog>  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月2日
	 */
	PageInfo<OperationLog> dataGrid(OperationLogVo vo);

	/**
	 * @Description: 删除
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月3日
	 */
	void delete(String id);
	
}
