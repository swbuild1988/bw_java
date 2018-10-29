package com.bandweaver.tunnel.common.biz.pojo.mam.video;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;

public class Video extends MeasObj {

    public static Video fromMeasObj(MeasObj obj){
        Video tmp = new Video();
        String str = JSONObject.toJSONString(obj);
        tmp = (Video)JSONObject.parseObject(str, Video.class);
        return tmp;
    }

    private Integer serverId;
    private Integer videoSceneId;
    private int channelNo;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getVideoSceneId() {
        return videoSceneId;
    }

    public void setVideoSceneId(Integer videoSceneId) {
        this.videoSceneId = videoSceneId;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Override
    public String toString() {
        return "Video{" +
                "serverId=" + serverId +
                ", videoSceneId=" + videoSceneId +
                ", channelNo=" + channelNo +
                "} " + super.toString();
    }

}
