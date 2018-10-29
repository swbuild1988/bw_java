package com.bandweaver.tunnel.common.biz.itf.em;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.em.WindPlanNodeEnum;

public interface WindPlanService {
	
	void sendMsg(JSONObject json);
	
	void doBusiness(WindPlanNodeEnum nodeEnum);
}
