package com.bandweaver.tunnel.common.biz.itf.mam.video;

import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoServerService {

    int addVideoServer(VideoServer videoServer);

    List<VideoServer> getAllVideoServers();

    List<VideoServer> getVideoServersByVendorAndVersion(Integer vendor, Integer version);

}
