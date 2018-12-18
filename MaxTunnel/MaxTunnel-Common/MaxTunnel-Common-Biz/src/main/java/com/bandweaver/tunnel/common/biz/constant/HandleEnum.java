package com.bandweaver.tunnel.common.biz.constant;

public enum HandleEnum {

	ADD("add", "添加"), 
	DELETE("delete", "删除"), 
	UPDATE("update", "更新"),
	LIST("list", "查询");

	private String value;
	private String name;

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setName(String name) {
		this.name = name;
	}

	HandleEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static HandleEnum getEnum(String value) {
		for (HandleEnum e : HandleEnum.values()) {
			if (value.equals(e.getValue()))
				return e;
		}
		return null;
	}

}
