package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;

import java.util.List;

public interface VideoServerMapper {

    int insertVideoServer(VideoServer videoServer);

    VideoServer getVideoServer(int id);

    List<VideoServer> getAllVideoServers();
}
