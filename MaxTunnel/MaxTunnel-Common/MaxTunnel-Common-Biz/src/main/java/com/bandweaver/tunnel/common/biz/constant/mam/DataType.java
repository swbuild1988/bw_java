package com.bandweaver.tunnel.common.biz.constant.mam;

/**
 * ClassName: DataType
 * @Description: 监测对象类型
 * @author shaosen
 * @date 2018年7月17日
 */
public enum DataType {
	
    /**模拟量输入AnologInput*/
    AI("模拟量输入", 1),
    /**开关量输入DigitalInput*/
    DI("开关量输入", 2),
    /**状态量输入*/
    SI("状态量输入", 3),
    /**分布式Distribute*/
    DISTRIBUTE("分布式数据", 5),
    /**视频*/
    VIDEO("视频", 7),
    /**StructureObject**/
    SO("结构化对象", 97),
    /**频谱数据*/
    DAS("频谱数据", 103);

    DataType() {
    }

    DataType(String name, int value) {
        this.name = name;
        this.value = value;
    }

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

    public static DataType getEnum(String name){
        for (DataType dl: DataType.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static DataType getEnum(int value){
        for (DataType dl: DataType.values()) {
            if (dl.getValue() == value) return dl;
        }
        return null;
    }
}
