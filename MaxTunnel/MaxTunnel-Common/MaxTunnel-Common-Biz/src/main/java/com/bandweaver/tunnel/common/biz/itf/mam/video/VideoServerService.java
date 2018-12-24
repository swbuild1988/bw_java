package com.bandweaver.tunnel.common.biz.itf.mam.video;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoServerDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import com.bandweaver.tunnel.common.biz.vo.mam.video.VideoServerVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface VideoServerService {

    int addVideoServer(VideoServer videoServer);
    
    int updateVideoServer(VideoServer videoServer);
    
    VideoServerDto getVideoServer(int id);

    List<VideoServerDto> getAllVideoServers();

    List<VideoServerDto> getVideoServersByCondition(VideoServerVo vo);
    
    PageInfo<VideoServerDto> dataGrid(VideoServerVo vo);
    
    int deleteVideoServer(Integer id);
}
