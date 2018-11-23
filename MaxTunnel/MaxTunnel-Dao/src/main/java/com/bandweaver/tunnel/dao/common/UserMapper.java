package com.bandweaver.tunnel.dao.common;

import java.util.List;
import java.util.Set;

import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.vo.UserVo;

public interface UserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	/**
	 * 根据用户名获取user
	 * 
	 * @param userName
	 * @return
	 */
	User queryByUserName(String userName);

	/**
	 * 获取所有的用户
	 * 
	 * @return
	 */
	List<UserDTO> queryAll();

	/**
	 * 根据用户名获取角色
	 * 
	 * @param userName
	 * @return
	 */
	Set<String> getRoles(String userName);

	/**
	 * 根据用户名获取权限
	 * 
	 * @param userName
	 * @return
	 */
	Set<String> getPermissions(String userName);
	
	int addUsers(List<User> users);

	/**
	 * @Description: 根据id集合查询用户列表
	 * @param @param userIdSet
	 * @param @return   
	 * @return List<UserDTO>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月23日
	 */
	List<UserDTO> getListByIds(Set<Integer> userIdSet);

	UserDTO getUserDtoById(Integer id);
	
	/**
	 * 通过用户角色获取用户列表
	 * @param roleDesc
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月17日
	 */
	List<User> getUsersByRoleDesc(String roleDesc);
}