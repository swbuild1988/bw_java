package com.bandweaver.tunnel.common.biz.itf.oam;

import com.bandweaver.tunnel.common.biz.pojo.oam.CableType;

import java.util.List;

public interface CableTypeService {

	List<CableType> getAllTypes();

	CableType getType(int id);

	void insertRecord(CableType type);

	void deleteRecord(int id);

	void updateRecord(CableType type);

}
