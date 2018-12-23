package com.bandweaver.tunnel.common.biz.constant.em;


/**
 * ClassName: UnitTypeEnum
 * @Description: 单位类别
 * @author shaosen
 * @date 2018年6月14日
 */
public enum UnitTypeEnum {

	/**医院*/
	HOSPITAL("医院" , 1),
	/**学校*/
	SCHOOL("学校" , 2),
	/**机关单位*/
	AGENCY_UNIT("机关单位" , 3),
	/**商场*/
	MARKET("商场",4),
	/**银行*/
	BANK("银行",5),
	/**景点*/
	SCENIC_SPOT("景点",6),
	/**火车站、机场*/
	TRAINSTATION_AIRPORT("火车站/机场",7),
	/**其他*/
	OTHER("其他",8);
	
	private String name;
    private int value;
	
    
    UnitTypeEnum() {
    }

    UnitTypeEnum(String name, int value) {
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
    
	public static UnitTypeEnum getEnum(int value) {
		for (UnitTypeEnum statusEnum : UnitTypeEnum.values()) {
			if(value == statusEnum.getValue()) 
				return statusEnum;
		}
		return null;
	}
    
}
