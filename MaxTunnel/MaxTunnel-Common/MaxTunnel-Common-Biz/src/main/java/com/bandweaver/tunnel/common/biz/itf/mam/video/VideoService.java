package com.bandweaver.tunnel.common.biz.itf.mam.video;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;

import java.util.List;

public interface VideoService {
    int addVideo(Video video);

    VideoDto getVideoDto(int id);

    List<Video> getAllVideos();

    List<VideoDto> getAllVideoDtos();

}
