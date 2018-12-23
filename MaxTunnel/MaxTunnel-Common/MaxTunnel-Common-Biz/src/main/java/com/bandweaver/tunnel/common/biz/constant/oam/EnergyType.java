package com.bandweaver.tunnel.common.biz.constant.oam;

/**
 * 能耗类型
 * @author ya.liu
 * @Date 2018年11月13日
 */
public enum EnergyType {
	
	/** 总计 */
	NONE("总计", 0),
    /** 普通 */
    COMMON("普通", 1),
    /** 应急 */
    EMERGENCY("应急", 2);
	
	private EnergyType() {
	}
	
	private EnergyType(String name, int value) {
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
    
    public static EnergyType getEnum(String name) {
    	for(EnergyType et : EnergyType.values()) {
    		if(et.getName().equals(name))
    			return et;
    	}
    	return null;
    }
    
    public static EnergyType getEnum(int value) {
    	for(EnergyType et : EnergyType.values()) {
    		if (et.getValue() == value)
    			return et;
    	}
    	return null;
    }
    
}
