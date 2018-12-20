package com.bandweaver.tunnel.common.biz.constant.em;


/**监测对象绑定对象类型枚举
 * @author shaosen
 * @date 2018年12月20日
 */
public enum ObjectBindTypeEnum {
	
	/**预案*/
	PLAN("预案",1),
	/**视频*/
	VIDEO("视频",2);
	
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

	ObjectBindTypeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static ObjectBindTypeEnum getEnum(int value) {
		for(ObjectBindTypeEnum actionEnum : ObjectBindTypeEnum.values()) {
			if(value == actionEnum.getValue())
				return actionEnum;
		}
		return null;
	}

}
