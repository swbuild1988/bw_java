package com.bandweaver.tunnel.common.biz.constant;



/**开关枚举
 * @author shaosen
 * @date 2018年11月22日
 */
public enum SwitchEnum {
	
	CLOSE("关",0),
	OPEN("开",1);
	
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

	SwitchEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static SwitchEnum getEnum(int value) {
		for(SwitchEnum e : SwitchEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
