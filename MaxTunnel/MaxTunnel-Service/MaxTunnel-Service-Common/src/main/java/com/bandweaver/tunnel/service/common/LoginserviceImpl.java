package com.bandweaver.tunnel.service.common;

import java.util.*;
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

        final String KEY_ADMIN = "admin";
        final String KEY_MENU_LIST = "menuList";

        JSONObject result = new JSONObject();
        List<Role> roleList = roleService.getRoleByUseName(username);
        if (roleList != null && roleList.size() > 0) {
            // 如果角色为admin，具有所有权限
            List<String> roleNameList = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
            final String ADMIN = "admin";
            if (roleNameList.contains(ADMIN)) {
                result.put(KEY_ADMIN, true);
                // 如果是admin角色，前端进行判断然后显示所有菜单及按钮权限，为了减少网络传输开销
                // menuList返回一个空数组即可
                result.put(KEY_MENU_LIST, new ArrayList<>(0));
                return result;
            }

            // 如果角色不是admin，则查询其所具有的路由菜单及该菜单下所具有的按钮权限
            List<JSONObject> menuList = new ArrayList<>();
            for (Role role : roleList) {
                List<Permission> permissions = permissionService.getPermissionsByRole(role.getId());
                if (permissions != null && permissions.size() > 0) {
                    for (Permission permission : permissions) {
                        JSONObject js = new JSONObject();
                        js.put("menu", permission.getMenuCode());
                        List<String> accessList = getAccessList(permission.getPermissionCode());
                        js.put("access", accessList);
                        js.put("accessAll", accessList.contains("*") ? true : false);
                        menuList.add(js);
                    }

                }
            }
            result.put(KEY_ADMIN, false);
            result.put(KEY_MENU_LIST, menuList);
            return result;
        }
        return null;
    }


    private List<String> getAccessList(String permissionCode) {
        if (permissionCode == null || permissionCode.trim().length() == 0) {
            return new ArrayList<String>(0);
        }
        String[] arr = permissionCode.split(":");
        String accessStr = arr[1];
        String[] accessArr = accessStr.split(",");
        List<String> accessList = Arrays.asList(accessArr);
        return accessList;
    }


    @Override
    public List<Role> getUserRoles(String username) {
        List<Role> list = roleService.getRoleByUseName(username);
        return list;
    }



}
