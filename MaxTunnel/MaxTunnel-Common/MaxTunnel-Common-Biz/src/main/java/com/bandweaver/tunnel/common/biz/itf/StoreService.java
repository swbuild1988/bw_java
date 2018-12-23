package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.vo.StoreVo;
import com.github.pagehelper.PageInfo;

public interface StoreService {

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月14日
	 */
	void addBatch(List<Store> list);

	/*
	* 获得所有仓
	* */
	List<Store> getList();

	/**
	 * @Description: 条件查询
	 * @param @param vo
	 * @param @return   
	 * @return List<StoreDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月15日
	 */
	List<StoreDto> getStoresByCondition(StoreVo vo);

	/**
	 * @Description: 根据管廊id查询管仓列表
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	List<StoreDto> getStoresByTunnelId(Integer id);

	/**
	 * @Description: 根据id查询管仓
	 * @param @param id
	 * @param @return   
	 * @return StoreDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	StoreDto getStoreById(Integer id);

	void add(Store store);

	PageInfo<StoreDto> dataGrid(StoreVo vo);

	void update(Store store);

	void delete(Integer id);

	void deleteBatch(List<Integer> id_list);

	Store getByName(String name);

	Store getStoreByTunnelAndSn(Integer tunnelId, String sn);

}
