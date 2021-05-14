package org.apache.karaf.jpa.model;

import java.io.Serializable;

public class Credentials implements Serializable {

	private static final long serialVersionUID = 2873428399097431672L;

	private String username;
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
