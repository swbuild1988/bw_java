package com.bandweaver.tunnel.common.biz.constant;

public enum ResourceEnum {

	TUNNEL("tunnel", "管廊"), USER("user", "用户"), EQUIPMENT("equipment", "设备");

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

	ResourceEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static ResourceEnum getEnum(String value) {
		for (ResourceEnum e : ResourceEnum.values()) {
			if (value.equals(e.getValue()))
				return e;
		}
		return null;
	}

}
