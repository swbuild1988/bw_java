package com.bandweaver.tunnel.common.biz.itf.mam.video;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoExtendSceneDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoExtendScene;

import java.util.List;

public interface VideoExtendSceneService {
    int addVideoExtendScene(VideoExtendScene videoExtendScene);
    List<VideoExtendScene> getVideoExtendScenes();
    List<VideoExtendScene> getVideoExtendScenesByTunnel(int tunnelId);
    List<VideoExtendSceneDto> getVideoExtendSceneDtosByTunnel(int tunnelId);
}
