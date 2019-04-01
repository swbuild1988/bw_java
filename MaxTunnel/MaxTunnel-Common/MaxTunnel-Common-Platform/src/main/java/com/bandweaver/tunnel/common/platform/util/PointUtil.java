package com.bandweaver.tunnel.common.platform.util;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.mam.Point3D;

import java.util.Arrays;
import java.util.List;

public class PointUtil {

    public static Point3D get3DPoint(String point) {

        Point3D res = new Point3D();

        if (point == null || point.length() == 0) return null;

        List<String> strList = Arrays.asList(point.split(","));
        if (strList.size() < 3) return null;

        res.setLng(DataTypeUtil.toDouble(strList.get(0)));
        res.setLat(DataTypeUtil.toDouble(strList.get(1)));
        res.setHeight(DataTypeUtil.toDouble(strList.get(2)));

        return res;
    }

    public static String get3DPointString(double longitude, double latitude, double height) {
        return DataTypeUtil.toString(longitude) + "," + DataTypeUtil.toString(latitude) + "," + DataTypeUtil.toString(height);
    }

}
