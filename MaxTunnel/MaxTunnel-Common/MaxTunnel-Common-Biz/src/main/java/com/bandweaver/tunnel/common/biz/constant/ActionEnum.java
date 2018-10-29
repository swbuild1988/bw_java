package com.bandweaver.tunnel.common.biz.constant;

public enum ActionEnum {

//	ENTER("进",1),
//	EXIT("出",2),
	
	
	VIEW("参观",1),
	MAINTAIN("维护",2),
	INSTALL("安装",3);
	
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

	ActionEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * @Description: 根据value值获取enum
	 * @param @param value
	 * @param @return   
	 * @return ActionEnum  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月23日
	 */
	public static ActionEnum getEnum(int value) {
		for(ActionEnum actionEnum : ActionEnum.values()) {
			if(value == actionEnum.getValue())
				return actionEnum;
		}
		return null;
	}

}
