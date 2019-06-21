package com.bandweaver.tunnel.common.biz.dto.mam;

public class Point3D {
    private Double lon;
    private Double lat;
    private Double height;

    public Point3D() {
        this.lat = 0.0;
        this.lon = 0.0;
        this.height = 0.0;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = (double) Math.round(lon * 100000000) / 100000000;
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
                "lon=" + lon +
                ", lat=" + lat +
                ", height=" + height +
                '}';
    }
}
