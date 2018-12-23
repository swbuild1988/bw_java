package com.bandweaver.tunnel.dao.common;

import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.vo.TunnelVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TunnelMapper {

	int updateByPrimaryKeySelective(Tunnel tunnel);

	List<TunnelSimpleDto> getList();

	TunnelSimpleDto getTunnelById(@Param("id") Integer id);

	String getNameById(@Param("id") Integer id);

	TunnelDto getDtoById(Integer id);

	List<TunnelDto> getDtoList();

	void insert(Tunnel tunnel);

	void insertSelective(Tunnel tunnel);

	void deleteByPrimaryKey(Integer id);

	List<TunnelDto> getByCondition(TunnelVo vo);

	void deleteBatch(List<Integer> list);

	Tunnel getByName(String name);

	Tunnel getBySN(String sn);
}
