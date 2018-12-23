package com.bandweaver.tunnel.common.biz.pojo.mam.video;

import java.io.Serializable;

public class VideoServer  implements Serializable {
    private Integer id;
    private String name;
    private Integer vendor;
    private Integer vendorVersion;
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String session;
    private Integer channelNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVendor() {
        return vendor;
    }

    public void setVendor(Integer vendor) {
        this.vendor = vendor;
    }

    public Integer getVendorVersion() {
        return vendorVersion;
    }

    public void setVendorVersion(Integer vendorVersion) {
        this.vendorVersion = vendorVersion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(Integer channelNum) {
        this.channelNum = channelNum;
    }

    @Override
    public String toString() {
        return "VideoServer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vendor=" + vendor +
                ", vendorVersion=" + vendorVersion +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", session='" + session + '\'' +
                ", channelNum=" + channelNum +
                '}';
    }
}
