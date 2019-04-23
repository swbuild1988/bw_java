package com.bandweaver.tunnel.common.biz.constant;

public enum OutsideEnum {

	/** 在职人员 */
	STAFF("员工", 1),
	/** 外来人员 */
	STRANGER("其他", 2);
	
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

	OutsideEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * 根据value值获取enum
	 * @param value
	 * @return
	 * @author ya.liu
	 * @Date 2019年2月28日
	 */
	public static OutsideEnum getEnum(int value) {
		for(OutsideEnum outsideEnum : OutsideEnum.values()) {
			if(value == outsideEnum.getValue())
				return outsideEnum;
		}
		return null;
	}
}
