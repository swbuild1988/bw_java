package com.bandweaver.tunnel.common.biz.itf.mam.video;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoSceneDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoScene;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoSceneService {
    int addVideoScene(VideoScene videoScene);
    List<VideoScene> getVideoScenes();
    List<VideoScene> getVideoScenesByTunnel(int tunnelId);
    List<VideoSceneDto> getVideoSceneDtosByTunnel(int tunnelId);
}
