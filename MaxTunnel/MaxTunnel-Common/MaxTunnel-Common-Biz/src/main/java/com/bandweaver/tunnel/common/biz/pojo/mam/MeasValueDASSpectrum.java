package com.bandweaver.tunnel.common.biz.pojo.mam;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;

public class MeasValueDASSpectrum implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7229330393387108514L;
	private Date time;
    private int objectId;
    private int zoneId;
    private int channelCount;
    private int channelId;
    private int samplerate;
    private int fftSize;
    private Image data;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(int channelCount) {
        this.channelCount = channelCount;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getSamplerate() {
        return samplerate;
    }

    public void setSamplerate(int samplerate) {
        this.samplerate = samplerate;
    }

    public int getFftSize() {
        return fftSize;
    }

    public void setFftSize(int fftSize) {
        this.fftSize = fftSize;
    }

    public Image getData() {
        return data;
    }

    public void setData(Image data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MeasValueDASSpectrum{" +
                "time=" + time +
                ", objectId=" + objectId +
                ", zoneId=" + zoneId +
                ", channelCount=" + channelCount +
                ", channelId=" + channelId +
                ", samplerate=" + samplerate +
                ", fftSize=" + fftSize +
                ", data=" + data +
                '}';
    }
}
