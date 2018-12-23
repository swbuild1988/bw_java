package com.bandweaver.tunnel.common.biz.itf.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.oam.CableSection;

public interface CableSectionService {

	void addBatch(List<CableSection> list);

	int getCountBySectionId(Integer id);

}
