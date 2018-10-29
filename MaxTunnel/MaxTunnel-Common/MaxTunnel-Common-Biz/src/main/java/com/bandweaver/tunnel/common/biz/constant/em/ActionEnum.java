package com.bandweaver.tunnel.common.biz.constant.em;

/**应急行为枚举
 * @author shaosen
 * @date 2018年10月9日
 */
public enum ActionEnum {

	
	METION("提示信息类",1),
	NOTICE("通知类",2),
	SWITCH("联动输出类型",3);
	
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
	
	public static ActionEnum getEnum(int value) {
		for(ActionEnum actionEnum : ActionEnum.values()) {
			if(value == actionEnum.getValue())
				return actionEnum;
		}
		return null;
	}

}
