package com.bandweaver.tunnel.dao;

import java.util.List;
import com.bandweaver.tunnel.common.biz.pojo.User;

public interface UserDao {
    User queryByID(int id);
    List<User> queryAll();
    int addUser(User user);
}
