package com.bandweaver.tunnel.common.biz.constant.omm;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: EquipmentStatusEnum
 * @Description: 设备状态枚举
 * @author shaosen
 * @date 2018年5月30日
 */
public enum EquipmentStatusEnum {

	/**运行中*/
	RUNNING("运行中" , 1),
	/**故障*/
	BROKEN("故障" , 2),
	/**备品*/
	BACKUP("备品" , 3);
	
	private String name;
    private int value;
	
    
    EquipmentStatusEnum() {
    }

    EquipmentStatusEnum(String name, int value) {
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
    
	public static EquipmentStatusEnum getEnum(String value) {
        return enumMap.get(value);
    }

	
    static Map<String,EquipmentStatusEnum> enumMap = new HashMap<String, EquipmentStatusEnum>();
    static {
        for(EquipmentStatusEnum en : EquipmentStatusEnum.values()){
            enumMap.put(en.getValue()+"", en);
        }
    }
    
}
