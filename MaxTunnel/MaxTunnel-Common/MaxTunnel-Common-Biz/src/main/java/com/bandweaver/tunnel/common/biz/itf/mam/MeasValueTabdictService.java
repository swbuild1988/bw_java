package com.bandweaver.tunnel.common.biz.itf.mam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueTabdict;

public interface MeasValueTabdictService {

	/**
	 * @Description: 添加
	 * @param @param record   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月29日
	 */
	void addMeasValueTabdict(MeasValueTabdict record);

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
	 * @Description: 根据id查询
	 * @param @param id
	 * @param @return   
	 * @return MeasValueTabdict  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	MeasValueTabdict getMeasValueTabdictById(int id);

}
