package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoExtendSceneDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoExtendScene;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoExtendSceneMapper {

    int insertVideoExtendScene(VideoExtendScene videoExtendScene);

    List<VideoExtendScene> getAllVideoExtendScenes();

    List<VideoExtendScene> getVideoExtendScenesByTunnel(@Param("tunnelId") int tunnelId);

    List<VideoExtendSceneDto> getVideoExtendSceneDtosByTunnel(@Param("tunnelId") int tunnelId);
}
