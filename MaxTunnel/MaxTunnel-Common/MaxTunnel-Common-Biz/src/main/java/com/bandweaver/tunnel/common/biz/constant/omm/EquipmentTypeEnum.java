package com.bandweaver.tunnel.common.biz.constant.omm;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: EquipmentStatusEnum
 * @Description: 设备类型枚举
 * @author shaosen
 * @date 2018年5月30日
 */
public enum EquipmentTypeEnum {

	/**安全防范*/
	SAFE("安全防范" , 1),
	/**视频监控*/
	VIDEO("视频监控" , 2),
	/**环境监测*/
	ENVIRONMENT("环境监测" , 3),
	/**电缆本体*/
	CABLE("电缆本体" , 4),
	/**火灾报警*/
	FIRE("火灾报警" , 5),
	/**隧道通信*/
	TUNNEL("隧道通信" , 6);
	
	private String name;
    private int value;
	
    
    EquipmentTypeEnum() {
    }

    EquipmentTypeEnum(String name, int value) {
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
    
	
	public static EquipmentTypeEnum getEnum(String value) {
        return enumMap.get(value);
    }

	
    static Map<String,EquipmentTypeEnum> enumMap = new HashMap<String, EquipmentTypeEnum>();
    static {
        for(EquipmentTypeEnum en : EquipmentTypeEnum.values()){
            enumMap.put(en.getValue()+"", en);
        }
    }

    
}
