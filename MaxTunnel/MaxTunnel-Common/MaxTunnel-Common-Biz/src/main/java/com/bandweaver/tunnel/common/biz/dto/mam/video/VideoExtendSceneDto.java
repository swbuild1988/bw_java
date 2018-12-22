package com.bandweaver.tunnel.common.biz.dto.mam.video;

import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoExtendScene;

import java.util.List;

public class VideoExtendSceneDto extends VideoExtendScene {

    private List<VideoDto> videos;

    public List<VideoDto> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDto> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "VideoExtendSceneDto{" +
                "videos=" + videos +
                "} " + super.toString();
    }
}
