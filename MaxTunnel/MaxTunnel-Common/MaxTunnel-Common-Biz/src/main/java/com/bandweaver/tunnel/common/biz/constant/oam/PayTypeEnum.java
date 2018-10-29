package com.bandweaver.tunnel.common.biz.constant.oam;


/**
 * ClassName: PayTypeEnum
 * @Description: 付款方式枚举
 * @author shaosen
 * @date 2018年6月14日
 */
public enum PayTypeEnum {

	/**月付*/
	MONTHLY("月付" , 1),
	/**季付*/
	QUARTER("季付" , 2),
	/**年付*/
	YEAR("年付" , 3);
	
	private String name;
    private int value;
	
    
    PayTypeEnum() {
    }

    PayTypeEnum(String name, int value) {
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
    
	public static PayTypeEnum getEnum(int value) {
		for (PayTypeEnum statusEnum : PayTypeEnum.values()) {
			if(value == statusEnum.getValue()) 
				return statusEnum;
		}
		return null;
	}
    
}
