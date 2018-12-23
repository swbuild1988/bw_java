package com.bandweaver.tunnel.common.biz.pojo.mam;

import java.io.Serializable;
import java.util.Date;

/**maxview配置信息表
 * @author shaosen
 * @date 2018年11月15日
 */
public class MaxviewConfig implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private String ip;

    private String port;

    private String username;

    private String password;

    private Date crtTime;

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
        this.name = name == null ? null : name.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	@Override
	public String toString() {
		return "MaxviewConfig [id=" + id + ", name=" + name + ", ip=" + ip + ", port=" + port + ", username=" + username
				+ ", password=" + password + ", crtTime=" + crtTime + "]";
	}
    
    
}