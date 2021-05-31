package org.apache.karaf.rest.api.model;

import java.io.Serializable;

public class RefreshToken implements Serializable {

	private static final long serialVersionUID = 1959860836878300110L;
	
	private String refresh_token;

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

}
