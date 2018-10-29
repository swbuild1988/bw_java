package com.bandweaver.tunnel.common.biz.constant.oam;

import java.util.HashMap;
import java.util.Map;



/**
 * 合同状态枚举
 * @author shaosen
 * @date 2018年7月29日
 */
public enum ContractStatusEnum {

	/**正常*/
	NORMAL("正常" , 1),
	/**过期*/
	EXPIRED("过期" , 2);
	
	private String name;
    private int value;
	
    
    ContractStatusEnum() {
    }

    ContractStatusEnum(String name, int value) {
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
    
	public static ContractStatusEnum getEnum(int value) {
		for (ContractStatusEnum statusEnum : ContractStatusEnum.values()) {
			if(value == statusEnum.getValue()) 
				return statusEnum;
		}
		return null;
	}
    
}
