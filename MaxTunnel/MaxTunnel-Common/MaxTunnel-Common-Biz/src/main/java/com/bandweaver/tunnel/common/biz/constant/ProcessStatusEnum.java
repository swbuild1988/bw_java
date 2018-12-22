package com.bandweaver.tunnel.common.biz.constant;


/**
 *  流程状态枚举
 * @author shaosen
 * @date 2018年6月14日
 */
public enum ProcessStatusEnum {

	/**审核中*/
	WAIT("审核中" , 1),
	/**通过*/
	PASS("通过" , 2),
	/**拒绝*/
	REFUSE("拒绝" , 3),
	/**结束*/
	END("结束",4);
	
	private String name;
    private int value;
	
    
    ProcessStatusEnum() {
    }

    ProcessStatusEnum(String name, int value) {
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
    
	public static ProcessStatusEnum getEnum(int value) {
		for (ProcessStatusEnum statusEnum : ProcessStatusEnum.values()) {
			if(value == statusEnum.getValue()) 
				return statusEnum;
		}
		return null;
	}
    
}
