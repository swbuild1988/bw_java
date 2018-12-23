package com.bandweaver.tunnel.dao.mam;

import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueTabdict;

public interface MeasValueTabdictMapper {
    int deleteByPrimaryKey(int id);

    int insert(MeasValueTabdict record);

    int insertSelective(MeasValueTabdict record);

    MeasValueTabdict selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(MeasValueTabdict record);

    int updateByPrimaryKey(MeasValueTabdict record);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月29日
	 */
	void addMeasValueTabdictBatch(List<MeasValueTabdict> list);
	
	 /**
     * 批量删除
     */
    void deleteBatch(Date time);
}