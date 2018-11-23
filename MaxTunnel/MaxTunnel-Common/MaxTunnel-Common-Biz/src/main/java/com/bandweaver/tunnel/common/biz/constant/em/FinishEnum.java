package com.bandweaver.tunnel.common.biz.constant.em;

/**应急结束类型枚举
 * @author shaosen
 * @date 2018年10月9日
 */
public enum FinishEnum {

	
	AUTO("自动",1),
	MANUAL("手动",2);
	
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

	FinishEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static FinishEnum getEnum(int value) {
		for(FinishEnum e : FinishEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
