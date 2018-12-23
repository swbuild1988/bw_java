package com.bandweaver.tunnel.common.biz.pojo.mam.video;

public class VideoPreset {

    private int id;
    private int videoId;
    private String presetName;
    private String longitude;
    private String latitude;
    private String height;
    private String pitch;
    private String roll;
    private String heading;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getPresetName() {
        return presetName;
    }

    public void setPresetName(String presetName) {
        this.presetName = presetName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "VideoPreset{" +
                "id=" + id +
                ", videoId=" + videoId +
                ", presetName=" + presetName +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", height='" + height + '\'' +
                ", pitch='" + pitch + '\'' +
                ", roll='" + roll + '\'' +
                ", heading='" + heading + '\'' +
                '}';
    }
}
