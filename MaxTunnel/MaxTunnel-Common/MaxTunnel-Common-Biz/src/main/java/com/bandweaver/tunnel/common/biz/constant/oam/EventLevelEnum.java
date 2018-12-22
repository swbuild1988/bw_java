package com.bandweaver.tunnel.common.biz.constant.oam;

/**
 * ClassName: EventLevelEnum
 * @Description: 事件级别枚举
 * @author ya.liu
 * @date 2018年10月16日
 */
public enum EventLevelEnum {
	/** 低 */
	LOW("低" , 1),
	/** 中 */
	MIDDLE("中" , 2),
	/** 高 */
	HIGH("高" , 3);
	
	private String name;
    private int value;
    
    EventLevelEnum() {
    	
    }
    
    EventLevelEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

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
    
	public static EventLevelEnum getEnum(int value) {
		for(EventLevelEnum levelEnum : EventLevelEnum.values()) {
			if (value == levelEnum.getValue())
				return levelEnum;
		}
		return null;
	}
}
