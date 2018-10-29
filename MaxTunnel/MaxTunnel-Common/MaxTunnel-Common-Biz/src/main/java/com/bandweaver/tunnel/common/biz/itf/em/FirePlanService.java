package com.bandweaver.tunnel.common.biz.itf.em;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.em.FirePlanNodeEnum;

public interface FirePlanService {

	void sendMsg(JSONObject json);

	void doBusiness(FirePlanNodeEnum nodeEnum);


}
