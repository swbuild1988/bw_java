package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.biz.vo.StoreTypeVo;
import com.github.pagehelper.PageInfo;

public interface StoreTypeService {

	int insert(StoreType storeType);

	List<StoreType> getList();

	StoreType getById(Integer storeId);

	void update(StoreType storeType);

	void delete(Integer id);

	void deleteBatch(List<Integer> list);

	PageInfo<StoreType> dataGrid(StoreTypeVo vo);

	StoreType getByName(String name);

	StoreType getBySN(String sn);
}
