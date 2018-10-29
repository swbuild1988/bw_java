package com.bandweaver.tunnel.service.mam.video;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import com.bandweaver.tunnel.dao.mam.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public int addVideo(Video video) {
        return videoMapper.insertVideo(video);
    }

    @Override
    public VideoDto getVideoDto(int id) {
        return videoMapper.getVideoDto(id);
    }

    @Override
    public List<Video> getAllVideos() {
        return videoMapper.getAllVideos();
    }

    @Override
    public List<VideoDto> getAllVideoDtos() {
        return videoMapper.getAllVideoDtos();
    }
}
