package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ClassName: SectionDto
 * 
 * @Description: 仓节
 * @author shaosen
 * @date 2018年6月21日
 */
public class SectionDto extends Section implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5147808746450270349L;

	private StoreDto store;

	private Area area;

	public StoreDto getStore() {
		return store;
	}

	public void setStore(StoreDto store) {
		this.store = store;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "SectionDto{" +
				"store=" + store +
				", area=" + area +
				"} " + super.toString();
	}
}