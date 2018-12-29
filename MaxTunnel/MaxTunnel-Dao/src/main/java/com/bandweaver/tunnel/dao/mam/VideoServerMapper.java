package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoServerDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import com.bandweaver.tunnel.common.biz.vo.mam.video.VideoServerVo;

import java.util.List;

public interface VideoServerMapper {

    int insertVideoServer(VideoServer videoServer);
    
    int updateVideoServer(VideoServer videoServer);

    VideoServerDto getVideoServer(Integer id);

    List<VideoServerDto> getAllVideoServers();
    
    List<VideoServerDto> getVideoServersByCondition(VideoServerVo vo);
    
    int deleteVideoServer(Integer id);
}
