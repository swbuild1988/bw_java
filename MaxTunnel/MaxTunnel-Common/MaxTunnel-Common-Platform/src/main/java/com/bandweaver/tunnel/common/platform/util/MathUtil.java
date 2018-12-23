package com.bandweaver.tunnel.common.platform.util;

import java.math.BigDecimal;

import com.bandweaver.tunnel.common.platform.log.LogUtil;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

/**
 * 运算工具类
 *
 * @author shaosen
 * @date 2018年8月17日
 */
public class MathUtil {

    private static final int DEF_DIV_SCALE = 10;

    /**
     * * 两个Double数相加 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.add(b2).doubleValue());
    }

    /**
     * * 两个Double数相减 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.subtract(b2).doubleValue());
    }

    /**
     * * 两个Double数相乘 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double mul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.multiply(b2).doubleValue());
    }

    /**
     * * 两个Double数相除 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double div(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * * 两个Double数相除，并保留scale位小数 *
     *
     * @param v1    *
     * @param v2    *
     * @param scale *
     * @return Double
     */
    public static Double div(Double v1, Double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 求直线外一点在已知两点确定的直线上的投影坐标
     *
     * @param p1   线上一点
     * @param p2   线上另一点
     * @param pOut 线外某点
     * @return Point2D 投影点
     * @author shaosen
     * @Date 2018年8月24日
     */
    public static Point2D getProjectivePoint(Point2D p1, Point2D p2, Point2D pOut) {

        if (p1.getX() == p2.getX()) {
            //斜率不存在，直线垂直于x轴
            //投影点横坐标为p1.x,竖坐标为pOut.y
            LogUtil.info(" 直线垂直于x轴 ");
            return new Point2D(p1.getX(), pOut.getY());
        }

        double k = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        double b = (p2.getX() * p1.getY() - p1.getX() * p2.getY()) / (p2.getX() - p1.getX());
//		LogUtil.info(" 斜率为 ： " + k);
//		LogUtil.info(" 直线方程为 y = "+ k +"x + "+ b );

        return getProjectivePoint(p1, k, b, pOut);
    }


    /**
     * 求直线外一点到直线上的投影点
     *
     * @param p1   线上一点
     * @param k    斜率
     * @param pOut 线外一点
     * @return Point2D 投影点
     * @author shaosen
     * @Date 2018年8月24日
     */
    public static Point2D getProjectivePoint(Point2D p1, double k, double b, Point2D pOut) {
        if (k == 0) {
            // 两点连线平行于x轴
            LogUtil.info(" 连线平行于x轴 ");
            return new Point2D(pOut.getX(), p1.getY());
        } else {
            double x = (k * p1.getX() + pOut.getX() / k + pOut.getY() - p1.getY()) / (1 / k + k);
            double y = -1 / k * (x - pOut.getX()) + pOut.getY();
            return new Point2D(x, y);


        }
    }

    /**
     * 获取三个点组成的夹角
     *
     * @param p1 第一个点
     * @param p2 中间点
     * @param p3 最后一点
     * @return
     */
    public static double getAngleOf3Point(Point2D p1, Point2D p2, Point2D p3) {
        double t = (p2.getX() - p1.getX()) * (p3.getX() - p1.getX()) + (p2.getY() - p1.getY()) * (p3.getY() - p1.getY());

        //为了精确直接使用而不使用中间变量
        //包含了步骤：A=向量的点乘/向量的模相乘
        //          B=arccos(A)，用反余弦求出弧度
        //          result=180*B/π 弧度转角度制
        double result = 180 * Math.acos(t / Math.sqrt
                ((Math.abs((p2.getX() - p1.getX()) * (p2.getX() - p1.getX())) + Math.abs((p2.getY() - p1.getY()) * (p2.getY() - p1.getY())))
                        * (Math.abs((p3.getX() - p1.getX()) * (p3.getX() - p1.getX())) + Math.abs((p3.getY() - p1.getY()) * (p3.getY() - p1.getY())))
                ))
                / Math.PI;
        //      pi   = 180
        //      x    =  ？
        //====> ?=180*x/pi

        return result;

    }
    
    
    /**冒泡排序算法    
     * @param args
     * @return   
     * @author shaosen
     * @Date 2018年11月20日
     */
    public static int[] bubbleSort(int[] args){
        for(int i=0;i<args.length-1;i++){   
           for(int j=i+1;j<args.length;j++){   
               if (args[i]>args[j]){   
                   int temp=args[i];   
                   args[i]=args[j];   
                   args[j]=temp;   
                   }   
        }  
       }  
       return args;  
    }
    
    /**选择排序算法    
     * @param args
     * @return   
     * @author shaosen
     * @Date 2018年11月20日
     */
    public static int[] selectSort(int[] args){
        for (int i=0;i<args.length-1 ;i++ ){   
               int min=i;   
               for (int j=i+1;j<args.length ;j++ ){   
                 if (args[min]>args[j]){   
                      min=j;   
                    }   
               }   
             if (min!=i){  
             int temp=args[i];  
             args[i]=args[min];  
             args[min]=temp;          
             }  
           }  
            return args;  
   }
    
    
    

}
