package com.bandweaver.tunnel.service.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.itf.common.PermissionService;
import com.bandweaver.tunnel.common.biz.itf.common.RoleService;
import com.bandweaver.tunnel.common.biz.pojo.common.Permission;
import com.bandweaver.tunnel.common.biz.pojo.common.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.Loginservice;
import com.bandweaver.tunnel.common.biz.itf.common.UserService;

@Service
public class LoginserviceImpl implements Loginservice {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public JSONObject getUserPermission(String name) {
        return userService.getPermissions(name);
    }

    @Override
    public JSONObject getPermissionListByUserName(String username) {

        JSONObject json = new JSONObject();

        //查询用户角色列表
        List<Role> roleList = roleService.getRoleByUseName(username);

        if (roleList == null || roleList.isEmpty()) {
            json.put("rList", new ArrayList<>());
            json.put("pList", new ArrayList<>());
            return json;

        } else {

            Set<String> roleNameList = roleList.stream().map(x -> x.getRoleName()).collect(Collectors.toSet());
            json.put("rList", roleNameList);

            Set<String> menuList = new HashSet<>();
            //判断是否是admin角色
            if (roleNameList.contains("admin")) {
                //获取所有权限列表
                menuList = permissionService.getAllMenuCode();
            } else {
                for (Role role : roleList) {
                    List<Permission> permissions = permissionService.getPermissionsByRole(role.getId());
                    if (!permissions.isEmpty()) {
                        menuList = permissions.stream().map(x -> x.getMenuCode()).collect(Collectors.toSet());
                    }
                }
            }


            List<JSONObject> pList = new ArrayList<>();

            if (!menuList.isEmpty()) {

                for (String menu : menuList) {

                    JSONObject j = new JSONObject();
                    j.put("menuCode", menu);

                    List<String> pCodeList = new ArrayList<>();
                    List<Permission> permissionList = permissionService.getPermissionsByMenuCode(menu);
                    if (!permissionList.isEmpty()) {
                        List<String> li = permissionList.stream().map(x -> x.getPermissionCode()).collect(Collectors.toList());
                        //对permissionCode进行字符串截取
                        for (String pcode : li) {
                            String substring = pcode.substring(pcode.lastIndexOf(":") + 1);
                            pCodeList.add(substring);
                        }


                        j.put("pCodeList", pCodeList);
                    }
                    j.put("pCodeList", pCodeList);
                    pList.add(j);
                }
            }

            json.put("pList", pList);
        }

        return json;
    }


}
