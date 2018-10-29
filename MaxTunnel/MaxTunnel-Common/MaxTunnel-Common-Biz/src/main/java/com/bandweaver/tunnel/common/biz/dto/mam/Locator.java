package com.bandweaver.tunnel.common.biz.dto.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.Point3D;

public class Locator {
    // 3维地理信息
    private Point3D position;
    // 使用者
    private Object owner;

    public Locator(){
        this.owner = null;
        this.position = null;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Locator{" +
                "position=" + position +
                ", owner='" + owner + '\'' +
                '}';
    }
}
