package com.bandweaver.tunnel.common.biz.pojo.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EquipmentTypeFile")
public class EquipmentTypeFile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String sn;
	
	private String name;
	
	private Integer typeId;

	public EquipmentTypeFile() {}
	
	public EquipmentTypeFile(String sn, String name, Integer typeId) {
		super();
		this.sn = sn;
		this.name = name;
		this.typeId = typeId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
}
