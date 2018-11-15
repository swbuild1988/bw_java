package com.bandweaver.tunnel.common.biz.dto.mam.video;

import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendor;
import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendorVersion;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoPreset;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoScene;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;

import java.util.List;

public class VideoDto extends Video {

    private VideoServerDto videoServerDto;
    private boolean ptzOperationsSupported;
    private List<VideoPreset> videoPresets;

    public VideoServerDto getVideoServerDto() {
        return videoServerDto;
    }

    public void setVideoServerDto(VideoServerDto videoServerDto) {
        this.videoServerDto = videoServerDto;
    }

    public boolean isPtzOperationsSupported() {
        return ptzOperationsSupported;
    }

    public void setPtzOperationsSupported(boolean ptzOperationsSupported) {
        this.ptzOperationsSupported = ptzOperationsSupported;
    }

    public List<VideoPreset> getVideoPresets() {
        return videoPresets;
    }

    public void setVideoPresets(List<VideoPreset> videoPresets) {
        this.videoPresets = videoPresets;
    }

    public String getUrl() {
        String url = "";
        VideoVendor vendor = VideoVendor.getEnum(this.videoServerDto.getVendor());

        switch (vendor) {
            case DaKang:
                url = "rtsp://" + this.videoServerDto.getUsername() + ":" + this.videoServerDto.getPassword() +
                        "@" + this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort() +
                        "/Streaming/Channels/" + super.getChannelNo();
                break;

            case HoneyWell_HISD:
                url = "rtsp://" + this.videoServerDto.getUsername() + ":" + this.videoServerDto.getPassword() +
                        "@" + this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort() +
                        "/h264/ch" + super.getChannelNo() + "/main/av_stream";
                break;

            case HoneyWell_HICC:
                url = "rtsp://" + this.videoServerDto.getUsername() + ":" + this.videoServerDto.getPassword() +
                        "@" + this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort() +
                        "/media?stream=0";
                break;

            case H5STREAM:
                url = this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort();
                break;

            default:
                break;
        }
        return url;
    }

    @Override
    public String toString() {
        return "VideoDto{" +
                "videoServerDto=" + videoServerDto +
                ", ptzOperationsSupported=" + ptzOperationsSupported +
                ", videoPresets=" + videoPresets +
                "} " + super.toString();
    }
}
