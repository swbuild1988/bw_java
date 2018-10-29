package com.bandweaver.tunnel.dao.common;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.pojo.OperationLog;
import com.bandweaver.tunnel.common.biz.vo.OperationLogVo;

public interface OperationLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(OperationLog record);

    int insertSelective(OperationLog record);

    OperationLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OperationLog record);

    int updateByPrimaryKey(OperationLog record);


	/**
	 * @Description: 更新操作日志结果
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月2日
	 */
	void updateById(@Param("result")String result,@Param("id")String id);

	/**
	 * @Description: 分页查询
	 * @param @param vo
	 * @param @return   
	 * @return List<OperationLog>  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月2日
	 */
	List<OperationLog> dataGrid(OperationLogVo vo);
}