package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.itf.UserService;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.biz.vo.UserVo;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.dao.common.StaffMapper;
import com.bandweaver.tunnel.dao.common.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StaffMapper staffMapper;
    

    @Override
    public List<UserDTO> getList() {
        return userMapper.queryAll();
    }

    @Override
    public int addUser(User user) {
    	user.setCrtTime(new Date());
        return userMapper.insert(user);
    }

    @Override
    public int addUsers(List<User> users) {
        return userMapper.addUsers(users);
    }

    @Override
    public UserDTO getUser(Integer id) {
    	
    	return userMapper.getUserDtoById(id);
    }

    @Override
    public User getByUserName(String userName) {
    	return userMapper.queryByUserName(userName);
    }

    @Override
    public Set<String> getRoles(String userName) {
        return userMapper.getRoles(userName);
    }

    @Override
    public Set<String> getPermissions(String userName) {
        return userMapper.getPermissions(userName);
    }

	@Override
	public void deleteById(Integer id) {
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<User> getUserByRoleDesc(String roleDesc) {
		return userMapper.getUsersByRoleDesc(roleDesc);
	}



}
