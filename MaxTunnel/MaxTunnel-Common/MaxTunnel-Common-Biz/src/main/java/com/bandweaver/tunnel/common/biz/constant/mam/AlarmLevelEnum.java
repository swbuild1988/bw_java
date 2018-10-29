package com.bandweaver.tunnel.common.biz.constant.mam;

/**
 * 告警等级枚举
 * 
 * @author shaosen
 * @date 2018年8月14日
 */
public enum AlarmLevelEnum {
	PROMPT("提示", 1),
	NORMAL("一般", 2),
	SERIOUS("严重", 3), 
	DANGEROUS("致命", 4);

	AlarmLevelEnum() {
	}

	AlarmLevelEnum(String name, int value) {
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

	public static AlarmLevelEnum getEnum(String name) {
		for (AlarmLevelEnum dl : AlarmLevelEnum.values()) {
			if (dl.getName().equals(name))
				return dl;
		}
		return null;
	}

	public static AlarmLevelEnum getEnum(int value) {
		for (AlarmLevelEnum dl : AlarmLevelEnum.values()) {
			if (dl.getValue() == value)
				return dl;
		}
		return null;
	}
}
