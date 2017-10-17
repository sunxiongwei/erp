package com.sun.security.entity;

import com.sun.framework.persistence.entity.AbstractLongEntity;

public class User extends AbstractLongEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1493905915171825307L;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
