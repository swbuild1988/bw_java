package com.bandweaver.tunnel.common.biz.constant;


/**时间枚举
 * @author shaosen
 * @date 2018年8月22日
 */
public enum TimeEnum {
	
	HOUR("时",1),
	DAY("日",2),
	WEEK("周",3),
	MONTH("月",4),
	YEAR("年",5);
	
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

	TimeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static TimeEnum getEnum(int value) {
		for(TimeEnum e : TimeEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
