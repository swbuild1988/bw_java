package com.bandweaver.tunnel.service.mam.video;

import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoServerService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import com.bandweaver.tunnel.dao.mam.VideoServerMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServerServiceImpl implements VideoServerService {
    @Autowired
    private VideoServerMapper videoServerMapper;

    @Override
    public int addVideoServer(VideoServer videoServer) {
        return videoServerMapper.insertVideoServer(videoServer);
    }

    @Override
    public List<VideoServer> getAllVideoServers() {
        return videoServerMapper.getAllVideoServers();
    }

    @Override
    public List<VideoServer> getVideoServersByVendorAndVersion(Integer vendor,
                                                               Integer version) {
        return null;
    }
}
