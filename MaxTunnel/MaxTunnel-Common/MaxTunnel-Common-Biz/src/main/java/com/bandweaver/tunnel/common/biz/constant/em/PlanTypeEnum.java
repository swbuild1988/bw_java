package com.bandweaver.tunnel.common.biz.constant.em;



/**
 * 应急预案枚举
 * @author shaosen
 * @date 2018年7月30日
 */
public enum PlanTypeEnum {

	/**消防预案*/
	FIRE_PLAN("消防预案",1),
	/**通风预案*/
	WIND_PLAN("通风预案",2),
	/**排水预案*/
	WATER_PLAN("排水预案",3),
	/**电气火灾预案*/
	ELECTRIC("电气火灾预案",4),
	/**人员出入预案*/
	EVACUATE("人员出入预案",5);
	
	
	private String name;
    private int value;
	
    
    PlanTypeEnum() {
    }

    PlanTypeEnum(String name, int value) {
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
    
	public static PlanTypeEnum getEnum(int value) {
		for (PlanTypeEnum statusEnum : PlanTypeEnum.values()) {
			if(value == statusEnum.getValue()) 
				return statusEnum;
		}
		return null;
	}
    
}
