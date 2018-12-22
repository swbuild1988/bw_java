package com.bandweaver.tunnel.common.biz.itf.em;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.pojo.em.ObjectBind;

public interface ObjectBindService {

	void add(ObjectBind objectBind);

	List<JSONObject> getPlansByObject(Integer id);

	List<VideoDto> getVideosByObject(Integer id);

}
