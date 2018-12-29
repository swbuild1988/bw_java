package com.bandweaver.tunnel.common.biz.constant.em;

/**应急目标对象枚举
 * @author shaosen
 * @date 2018年10月9日
 */
public enum TargetEnum {

	
	ASSIGN_TO("指定对象",1),
	TYPE("类型",2);
	
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

	TargetEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static TargetEnum getEnum(int value) {
		for(TargetEnum e : TargetEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
