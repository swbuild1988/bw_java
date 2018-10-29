package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoPreset;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoPresetMapper {

    int insertVideoPreset(VideoPreset preset);

    int updateVideoPreset(VideoPreset preset);

    int deleteVideoPreset(@Param("videoId") int videoId, @Param("presetName") String presetName);

    List<VideoPreset> getVideoPresets(@Param("videoId") int videoId);
}
