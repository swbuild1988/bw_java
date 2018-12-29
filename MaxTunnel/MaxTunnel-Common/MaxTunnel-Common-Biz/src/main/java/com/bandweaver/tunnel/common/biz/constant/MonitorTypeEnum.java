package com.bandweaver.tunnel.common.biz.constant;

/**管廊本地监测内容枚举
 * @author shaosen
 * @date 2018年8月16日
 */
public enum MonitorTypeEnum {
	/**环境监测*/
	Environmental("环境监测",1),
	/**结构监测*/
	structure("结构监测",2),
	/**安防监测*/
	Security("安防监测",3),
	/**其他监测*/
	Others("其他监测",4);
	
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

	MonitorTypeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public static MonitorTypeEnum getEnum(int value) {
		for(MonitorTypeEnum actionEnum : MonitorTypeEnum.values()) {
			if(value == actionEnum.getValue())
				return actionEnum;
		}
		return null;
	}

}
