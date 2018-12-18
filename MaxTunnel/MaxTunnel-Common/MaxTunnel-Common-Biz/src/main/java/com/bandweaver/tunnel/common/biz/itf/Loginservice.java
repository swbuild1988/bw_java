package com.bandweaver.tunnel.common.biz.itf;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;

public interface Loginservice {

	JSONObject getUserPermission(String name);

}
