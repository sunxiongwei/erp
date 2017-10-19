package com.sun.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sun.framework.persistence.entity.AbstractLongEntity;

@Entity
@Table(name="erp_user")
public class User extends AbstractLongEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1493905915171825307L;
	private String username;
	@Column(name="password")
	private String password;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
