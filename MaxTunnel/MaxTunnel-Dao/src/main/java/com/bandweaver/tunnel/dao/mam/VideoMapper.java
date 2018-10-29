package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface VideoMapper {

    int insertVideo(Video video);

    int updateVideo(Video video);

    VideoDto getVideoDto(@Param("id") Integer id);

    List<Video> getAllVideos();

    List<VideoDto> getAllVideoDtos();

    List<VideoDto> getVideoDtosByScene(@Param("sceneId") int sceneId);
}
