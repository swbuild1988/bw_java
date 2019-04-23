package com.bandweaver.tunnel.common.biz.dto.mam;

public class Point3D {
    private Double lng;
    private Double lat;
    private Double height;

    public Point3D() {
        this.lat = 0.0;
        this.lng = 0.0;
        this.height = 0.0;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = (double) Math.round(lng * 100000000) / 100000000;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = (double) Math.round(lat * 100000000) / 100000000;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Locator{" +
                "lng=" + lng +
                ", lat=" + lat +
                ", height=" + height +
                '}';
    }
}
