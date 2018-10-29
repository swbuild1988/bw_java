package com.bandweaver.tunnel.common.biz.constant.oam;

import java.util.HashMap;
import java.util.Map;


/**
 * ClassName: CableStatusEnum
 * @Description: 管线状态枚举
 * @author shaosen
 * @date 2018年6月14日
 */
public enum CableStatusEnum {

	/**运行中*/
	RUNNING("运行中" , 1),
	/**故障*/
	BROKEN("故障" , 2);
	
	private String name;
    private int value;
	
    
    CableStatusEnum() {
    }

    CableStatusEnum(String name, int value) {
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
    
	public static CableStatusEnum getEnum(int value) {
		for (CableStatusEnum statusEnum : CableStatusEnum.values()) {
			if(value == statusEnum.getValue()) 
				return statusEnum;
		}
		return null;
	}
    
}
