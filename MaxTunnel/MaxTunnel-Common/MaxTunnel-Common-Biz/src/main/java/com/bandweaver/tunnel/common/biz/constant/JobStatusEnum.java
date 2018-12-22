package com.bandweaver.tunnel.common.biz.constant;


/**Job状态枚举
 * @author shaosen
 * @date 2018年8月22日
 */
public enum JobStatusEnum {
	
	DISABLED("禁用",0),
	ENABLED("启用",1);
	
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

	JobStatusEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static JobStatusEnum getEnum(int value) {
		for(JobStatusEnum e : JobStatusEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
