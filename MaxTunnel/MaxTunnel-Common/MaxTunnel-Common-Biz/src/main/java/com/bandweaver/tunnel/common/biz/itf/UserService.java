package com.bandweaver.tunnel.common.biz.itf;

import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.vo.UserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

public interface UserService {
    /**
     *
     * @return
     */
    List<UserDTO> getList();

    /**
     *
     * @param user
     * @return
     */
    int addUser(User user);

    int addUsers(List<User> users);

    /**
     *
     * @param id
     * @return
     */
    UserDTO getUser(Integer id);

    /**
     * @param userName
     * @return
     */
    public User getByUserName(String userName);

    /**
     * @param userName
     * @return
     */
    public Set<String> getRoles(String userName);

    /**
     * @param userName
     * @return
     */
    public Set<String> getPermissions(String userName);

	/**
	 * @Description: 通过id删除用户
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月20日
	 */
	void deleteById(Integer id);

	List<User> getUserByRoleDesc(String roleDesc);
}
