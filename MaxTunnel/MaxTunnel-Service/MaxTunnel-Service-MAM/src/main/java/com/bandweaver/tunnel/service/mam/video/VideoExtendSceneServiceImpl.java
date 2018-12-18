package com.bandweaver.tunnel.service.mam.video;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoExtendSceneDto;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoExtendSceneService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoExtendScene;
import com.bandweaver.tunnel.dao.mam.VideoExtendSceneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoExtendSceneServiceImpl implements VideoExtendSceneService {
    @Autowired
    private VideoExtendSceneMapper VideoExtendSceneMapper;

    @Override
    public int addVideoExtendScene(VideoExtendScene videoExtendScene) {
        return VideoExtendSceneMapper.insertVideoExtendScene(videoExtendScene);
    }

    @Override
    public List<VideoExtendScene> getVideoExtendScenesByTunnel(int tunnelId) {
        return VideoExtendSceneMapper.getVideoExtendScenesByTunnel(tunnelId);
    }

    @Override
    public List<VideoExtendSceneDto> getVideoExtendSceneDtosByTunnel(int tunnelId) {
        return VideoExtendSceneMapper.getVideoExtendSceneDtosByTunnel(tunnelId);
    }

    @Override
    public List<VideoExtendScene> getVideoExtendScenes() {
        return VideoExtendSceneMapper.getAllVideoExtendScenes();
    }
}
