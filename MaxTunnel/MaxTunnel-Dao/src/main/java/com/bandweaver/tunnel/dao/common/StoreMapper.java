package com.bandweaver.tunnel.dao.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.vo.StoreVo;

public interface StoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(Integer id);

    List<Store> getList();
    
    StoreDto getDtoById(Integer id);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月14日
	 */
	void addBatch(List<Store> list);

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

	void deleteBatch(List<Integer> list);

	Store getByName(String name);

	Store getStoreByTunnelAndSN(Integer tunnelId, String sn);

	List<Store> getStoresByTunnelId(Integer tunnelId);
}