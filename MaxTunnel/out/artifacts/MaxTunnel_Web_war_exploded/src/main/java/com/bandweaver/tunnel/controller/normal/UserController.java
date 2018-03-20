package com.bandweaver.tunnel.controller.normal;


import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.ref.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private List<User> getList(){
        System.out.println(111);
        List<User> users = userService.getList();
        return users;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    private String addUser(User user){
        System.out.println(user.getId()+ " " +user.getName());
        int res = userService.addUser(user);
        System.out.println("res:" + res);

        return  "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private User getUser(@PathVariable("id") int id){
        User user = userService.getUser(id);
        System.out.println("out " + user.getId()+ " " +user.getName());
        return user;
    }
}
