package com.bandweaver.tunnel.dao;

import com.bandweaver.tunnel.common.biz.pojo.User;

import java.util.List;

public interface UserDao {
    User queryByID(int id);
    List<User> queryAll();
    int addUser(User user);
}
