package com.bandweaver.tunnel.common.platform.util;

import javafx.geometry.Point2D;


public class GPSUtil {

    private static final double EARTH_RADIUS = 6378137;// 赤道半径(单位m)
    
    // 
    private static Double[] MCBAND = {12890594.86, 8362377.87, 5591021d, 3481989.83, 1678043.12, 0d};
    private static Double[] LLBAND = {75d, 60d, 45d, 30d, 15d, 0d};
    private static Double[][] MC2LL = {{1.410526172116255e-8, 0.00000898305509648872, -1.9939833816331, 200.9824383106796, -187.2403703815547, 91.6087516669843, -23.38765649603339, 2.57121317296198, -0.03801003308653, 17337981.2}, {-7.435856389565537e-9, 0.000008983055097726239, -0.78625201886289, 96.32687599759846, -1.85204757529826, -59.36935905485877, 47.40033549296737, -16.50741931063887, 2.28786674699375, 10260144.86}, {-3.030883460898826e-8, 0.00000898305509983578, 0.30071316287616, 59.74293618442277, 7.357984074871, -25.38371002664745, 13.45380521110908, -3.29883767235584, 0.32710905363475, 6856817.37}, {-1.981981304930552e-8, 0.000008983055099779535, 0.03278182852591, 40.31678527705744, 0.65659298677277, -4.44255534477492, 0.85341911805263, 0.12923347998204, -0.04625736007561, 4482777.06}, {3.09191371068437e-9, 0.000008983055096812155, 0.00006995724062, 23.10934304144901, -0.00023663490511, -0.6321817810242, -0.00663494467273, 0.03430082397953, -0.00466043876332, 2555164.4}, {2.890871144776878e-9, 0.000008983055095805407, -3.068298e-8, 7.47137025468032, -0.00000353937994, -0.02145144861037, -0.00001234426596, 0.00010322952773, -0.00000323890364, 826088.5}};
    private static Double[][] LL2MC = {{-0.0015702102444, 111320.7020616939, 1704480524535203d, -10338987376042340d, 26112667856603880d, -35149669176653700d, 26595700718403920d, -10725012454188240d, 1800819912950474d, 82.5}, {0.0008277824516172526, 111320.7020463578, 647795574.6671607, -4082003173.641316, 10774905663.51142, -15171875531.51559, 12053065338.62167, -5124939663.577472, 913311935.9512032, 67.5}, {0.00337398766765, 111320.7020202162, 4481351.045890365, -23393751.19931662, 79682215.47186455, -115964993.2797253, 97236711.15602145, -43661946.33752821, 8477230.501135234, 52.5}, {0.00220636496208, 111320.7020209128, 51751.86112841131, 3796837.749470245, 992013.7397791013, -1221952.21711287, 1340652.697009075, -620943.6990984312, 144416.9293806241, 37.5}, {-0.0003441963504368392, 111320.7020576856, 278.2353980772752, 2485758.690035394, 6070.750963243378, 54821.18345352118, 9540.606633304236, -2710.55326746645, 1405.483844121726, 22.5}, {-0.0003218135878613132, 111320.7020701615, 0.00369383431289, 823725.6402795718, 0.46104986909093, 2351.343141331292, 1.58060784298199, 8.77738589078284, 0.37238884252424, 7.45}};


    /**
     * 转化为弧度(rad)
     */
    public static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 转化为角度(degree)
     */
    public static double degree(double r) {
        return r * 180.8 / Math.PI;
    }

    /**
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下（单位m）
     *
     * @param lon1 第一点的精度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的精度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位m
     */
    public static double GetDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        // s = Math.round(s * 10000) / 10000;
        return s;

    }


    /**
     * (米勒投影算法）将经纬度转化为平面坐标 （单位m）
     *
     * @param lon 经度 西经为负数
     * @param lat 维度 南纬是负数
     * @return
     * @author shaosen
     * @Date 2018年8月24日
     */
    public static Point2D MillierConvertion(double lon, double lat) {
        double L = 6381372 * Math.PI * 2;//地球周长
        double W = L;// 平面展开后，x轴等于周长
        double H = L / 2;// y轴约等于周长一半
        double mill = 2.3;// 米勒投影中的一个常数，范围大约在正负2.3之间
        double x = lon * Math.PI / 180;// 将经度从度数转换为弧度
        double y = lat * Math.PI / 180;// 将纬度从度数转换为弧度
        y = 1.25 * Math.log(Math.tan(0.25 * Math.PI + 0.4 * y));// 米勒投影的转换
        // 弧度转为实际距离
        x = (W / 2) + (W / (2 * Math.PI)) * x;
        y = (H / 2) - (H / (2 * mill)) * y;
//         double[] result=new double[2];  
//         result[0]=x;  
//         result[1]=y; 
        return new Point2D(x, y);
    }

    /**
     * 米勒投影法，将x，y转化成经纬度
     * @param x
     * @param y
     * @return
     */
    public static Point2D MillierConvertion2(double x, double y) {
        double L = 6381372 * Math.PI * 2;//地球周长
        double W = L;// 平面展开后，x轴等于周长
        double H = L / 2;// y轴约等于周长一半
        double mill = 2.3;// 米勒投影中的一个常数，范围大约在正负2.3之间
        double lat =0;
        lat = ((H / 2 - y) * 2 * mill) / (1.25 * H);
        lat = ((Math.atan(Math.exp(lat)) - 0.25 * Math.PI) * 180) / (0.4 * Math.PI);
        double lon = 0;
        lon = (x - W / 2) * 360 / W;
        return new Point2D(lon, lat);
    }

    /**
     * 墨卡托坐标转经纬度坐标
     * @param x
     * @param y
     * @return
     */
    public static Point2D convertMC2LL(Double x, Double y) {
        Double[] cF = null;
        x = Math.abs(x);
        y = Math.abs(y);
        for (int cE = 0; cE < MCBAND.length; cE++) {
            if (y >= MCBAND[cE]) {
                cF = MC2LL[cE];
                break;
            }
        }
        
        return converter(x, y, cF);
    }

    /**
     * 经纬度坐标转墨卡托坐标
     * @param lng
     * @param lat
     * @return
     */
    public static Point2D convertLL2MC(Double lng, Double lat) {
        Double[] cE = null;
        lng = getLoop(lng, -180, 180);
        lat = getRange(lat, -74, 74);
        for (int i = 0; i < LLBAND.length; i++) {
            if (lat >= LLBAND[i]) {
                cE = LL2MC[i];
                break;
            }
        }
        if (cE!=null) {
            for (int i = LLBAND.length - 1; i >= 0; i--) {
                if (lat <= -LLBAND[i]) {
                    cE = LL2MC[i];
                    break;
                }
            }
        }
        return converter(lng,lat, cE);
    }
    private static Point2D converter(Double x, Double y, Double[] cE) {
        Double xTemp = cE[0] + cE[1] * Math.abs(x);
        Double cC = Math.abs(y) / cE[9];
        Double yTemp = cE[2] + cE[3] * cC + cE[4] * cC * cC + cE[5] * cC * cC * cC + cE[6] * cC * cC * cC * cC + cE[7] * cC * cC * cC * cC * cC + cE[8] * cC * cC * cC * cC * cC * cC;
        xTemp *= (x < 0 ? -1 : 1);
        yTemp *= (y < 0 ? -1 : 1);
        return new Point2D(xTemp, yTemp);
    }
    private static Double getLoop(Double lng, Integer min, Integer max) {
        while (lng > max) {
            lng -= max - min;
        }
        while (lng < min) {
            lng += max - min;
        }
        return lng;
    }
    private static Double getRange(Double lat, Integer min, Integer max) {
        if (min != null) {
            lat = Math.max(lat, min);
        }
        if (max != null) {
            lat = Math.min(lat, max);
        }
        return lat;
    }
    
    private static double getMiddleX(double x1, double x2, double total, double index) {
        return (double) Math.round((x1 + (x2 - x1) / total * index) * 100000000) / 100000000;
    }
    
    public static void main(String[] args) {
//    	for(int i=0;i<4;i++) {
//    		
//    		Point2D p = convertLL2MC(112.4873583,37.7142528);
//    		
//    		Point2D p1 = convertMC2LL(p.getX() - i, p.getY() - i);
//    		System.out.println(p1);
//    	}
    	
    	int len = 0;
    	for(int i=1;i<21;i++) {
    		// 经纬度坐标转墨卡托坐标
        	Point2D sp = convertLL2MC(112.4873583,37.7142528);
        	Point2D ep = convertLL2MC(112.5247583,37.7033389);
        	
        	double tmp_x = getMiddleX(sp.getX(), ep.getX(), 40000, len);
        	double tmp_y = getMiddleX(sp.getY(), ep.getY(), 40000, len);
            //System.out.println(tmp_x,);
        	// 墨卡托坐标转经纬度坐标
        	Point2D tmp_sp = convertMC2LL(tmp_x, tmp_y);
        	System.out.print(tmp_sp);
        	len += 200 * i;
        	tmp_x = getMiddleX(sp.getX(), ep.getX(), 40000, len);
        	tmp_y = getMiddleX(sp.getY(), ep.getY(), 40000, len);
        	
        	Point2D tmp_ep = convertMC2LL(tmp_x, tmp_y);
        	System.out.println("---" + tmp_ep);
    	}
    }
}
