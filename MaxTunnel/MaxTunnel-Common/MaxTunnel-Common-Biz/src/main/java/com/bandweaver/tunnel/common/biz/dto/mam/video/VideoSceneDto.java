package com.bandweaver.tunnel.common.biz.dto.mam.video;

import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoScene;

import java.util.List;

public class VideoSceneDto extends VideoScene {
    private List<VideoDto> videos;

    public List<VideoDto> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDto> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "VideoSceneDto{" +
                "videos=" + videos +
                "} " + super.toString();
    }
}
