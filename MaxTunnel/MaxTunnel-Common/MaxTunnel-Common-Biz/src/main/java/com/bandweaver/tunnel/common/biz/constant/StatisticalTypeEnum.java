package com.bandweaver.tunnel.common.biz.constant;



/**数据统计类型
 * @author shaosen
 * @date 2018年11月12日
 */
public enum StatisticalTypeEnum {
	
	MAX_VALUE("最大值",1),
	MIN_VALUE("最小值",2),
	AVG_VALUE("平均值",3);
	
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

	StatisticalTypeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static StatisticalTypeEnum getEnum(int value) {
		for(StatisticalTypeEnum e : StatisticalTypeEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
