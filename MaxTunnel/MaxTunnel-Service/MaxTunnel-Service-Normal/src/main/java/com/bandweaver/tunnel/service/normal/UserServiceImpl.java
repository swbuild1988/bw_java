package com.bandweaver.tunnel.service.normal;

import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.ref.UserService;
import com.bandweaver.tunnel.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Scope
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getList() {
        return userDao.queryAll();
    }

    @Override
    public int addUser(User user) {
        userDao.addUser(user);
        return 0;
    }

    @Override
    public User getUser(int id) {
        return userDao.queryByID(id);
    }
}
