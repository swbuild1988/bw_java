package com.bandweaver.tunnel.common.biz.itf.em;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.em.ObjectBind;

public interface ObjectBindService {

	void add(ObjectBind objectBind);

	List<ObjectBind> getPlansByObject(Integer id);

	List<ObjectBind> getVideosByObject(Integer id);

}
