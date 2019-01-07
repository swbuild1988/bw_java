package com.bandweaver.tunnel.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.common.RoleDto;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import com.bandweaver.tunnel.common.biz.vo.common.RoleVo;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	List<Role> getAllRoles();

	void deleteRoleBatch(List<Integer> list);

	List<RoleDto> getByCondition(RoleVo vo);

	List<Role> getRolesByUser(Integer id);

}