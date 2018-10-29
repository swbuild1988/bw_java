package com.bandweaver.tunnel.common.biz.constant;


public enum PtzDirectionEnum {

    /**
     * ptz控制，x+表示向左，x-表示向右，y+表示向上，y-表示向下，z+表示镜头拉近，z-表示镜头拉远
     * 使用3进制，0代表-，1代表不动，2代表+
     * 3位，第一位表示镜头zoom，第二位表示x，第三位表示y
     * 如 121：表示镜头zoom不动，x为+，y不动，结果为相机向左转
     * 如 100：表示镜头zoom不动，x为-，y为-，结果是相机右下转动
     * 这里的value值则是对应的3进制转化为10进制的值
     */


    // 111 -> 9+3+1=13
    STOP("停止", 13),
    // 112 -> 9+3+2=14
    UP("上", 14),
    // 102 -> 9+0+2=11
    RIGHT_UP("右上", 11),
    // 101 -> 9+0+1=10
    RIGHT("右", 10),
    // 100 -> 9
    RIGHT_DOWN("右下", 9),
    // 110 -> 9+3=12
    DOWN("下", 12),
    // 120 -> 9+6=15
    LEFT_DOWN("左下", 15),
    // 121 -> 9+6+1=16
    LEFT("左", 16),
    // 122 -> 9+6+2=17
    LEFT_UP("左上", 17),
    // 211 -> 18+3+1=22
    ZOOM_IN("镜头拉近", 22),
    // 011 -> 3+1=4
    ZOOM_OUT("镜头拉远", 4);

    private String name;
    private int value;

    PtzDirectionEnum(String name, int value){
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

    /**
     * 根据枚举值获得方向
     *
     * @param value
     * @return
     */
    public static PtzDirectionEnum getEnum(int value) {
        for (PtzDirectionEnum actionEnum : PtzDirectionEnum.values()) {
            if (value == actionEnum.getValue())
                return actionEnum;
        }
        return PtzDirectionEnum.STOP;
    }
}
