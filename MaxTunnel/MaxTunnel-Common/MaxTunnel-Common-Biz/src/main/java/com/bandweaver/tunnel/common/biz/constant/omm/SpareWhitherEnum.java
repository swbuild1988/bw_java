package com.bandweaver.tunnel.common.biz.constant.omm;

/**
 * 备品去向枚举
 */
public enum SpareWhitherEnum {

	/** 管廊设备 */
	PIPE("管廊设备",1),
	/** 仪表工具 */
	INSTRUMENT("仪表工具",2);
	
	SpareWhitherEnum() {
	}
	
	SpareWhitherEnum(String name,int value) {
		this.name = name;
		this.value = value;
	}
	
	private String name;
	private int value;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static SpareWhitherEnum getEnum(String name) {
		for(SpareWhitherEnum spare : SpareWhitherEnum.values()) {
			if(spare.getName().equals(name)) return spare;
		}
		return null;
	}
	
	public static SpareWhitherEnum getEnum(int value) {
		for(SpareWhitherEnum spare : SpareWhitherEnum.values()) {
			if(spare.getValue() == value) return spare;
		}
		return null;
	}
}
