package com.bandweaver.tunnel.common.platform.util;

public class RandomUtil {
    public static double getValue(double start, double end){
        return start + Math.random() * (end - start);
    }
}
