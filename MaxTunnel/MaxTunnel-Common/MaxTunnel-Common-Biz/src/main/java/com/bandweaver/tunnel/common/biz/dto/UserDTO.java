package com.bandweaver.tunnel.common.biz.dto;

import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.bandweaver.tunnel.common.biz.pojo.Position;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class UserDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6378443201621757910L;
	private int id;
    private String name;
    @JsonIgnore
    private String password;
    private Date crtTime;
    private Set<String> roles;
    private Set<String> permissions;
    
    
    
    /**最近一次入廊记录*/
//    private EntryHistory entryHistory;

    public UserDTO() {
    }

    public UserDTO(User user){
        if (user == null) return;

        this.id = user.getId();
        this.name = user.getName();
    }

    public UserDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
    
    

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	
}
