package com.bandweaver.tunnel.service.mam.video;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoSceneDto;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoSceneService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoScene;
import com.bandweaver.tunnel.dao.mam.VideoSceneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoSceneServiceImpl implements VideoSceneService {
    @Autowired
    private VideoSceneMapper videoSceneMapper;

    @Override
    public int addVideoScene(VideoScene videoScene) {
        return videoSceneMapper.insertVideoScene(videoScene);
    }

    @Override
    public List<VideoScene> getVideoScenesByTunnel(int tunnelId) {
        return videoSceneMapper.getVideoScenesByTunnel(tunnelId);
    }

    @Override
    public List<VideoSceneDto> getVideoSceneDtosByTunnel(int tunnelId) {
        return videoSceneMapper.getVideoSceneDtosByTunnel(tunnelId);
    }

    @Override
    public List<VideoScene> getVideoScenes() {
        return videoSceneMapper.getAllVideoScenes();
    }
}
