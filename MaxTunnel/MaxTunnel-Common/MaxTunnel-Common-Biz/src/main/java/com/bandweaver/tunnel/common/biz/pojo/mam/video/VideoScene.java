package com.bandweaver.tunnel.common.biz.pojo.mam.video;

import java.io.Serializable;
import java.util.List;

public class VideoScene implements Serializable {
    private Integer id;
    private String name;
    private Integer tunnelId;
    private Boolean isLoop;
    private Integer loopIndex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Integer tunnelId) {
        this.tunnelId = tunnelId;
    }

    public Boolean getLoop() {
        return isLoop;
    }

    public void setLoop(Boolean loop) {
        isLoop = loop;
    }

    public Integer getLoopIndex() {
        return loopIndex;
    }

    public void setLoopIndex(Integer loopIndex) {
        this.loopIndex = loopIndex;
    }

    @Override
    public String toString() {
        return "VideoScene{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tunnelId=" + tunnelId +
                ", isLoop=" + isLoop +
                ", loopIndex=" + loopIndex +
                '}';
    }


}
