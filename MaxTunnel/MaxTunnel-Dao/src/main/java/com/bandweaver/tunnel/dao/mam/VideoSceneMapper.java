package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoSceneDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoScene;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoSceneMapper {

    int insertVideoScene(VideoScene videoScene);

    List<VideoScene> getAllVideoScenes();

    List<VideoScene> getVideoScenesByTunnel(@Param("tunnelId") int tunnelId);

    List<VideoSceneDto> getVideoSceneDtosByTunnel(@Param("tunnelId") int tunnelId);
}
