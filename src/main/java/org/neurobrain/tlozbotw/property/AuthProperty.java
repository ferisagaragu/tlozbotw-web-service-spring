package org.neurobrain.tlozbotw.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.auth")
public class AuthProperty {

	private String jwtSecret;
	private String jwtExpiration;
	
	private String mailUser;
	private String mailPassword;
	
	
	public String getJwtSecret() {
		return jwtSecret;
	}
	
	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}
	
	public String getJwtExpiration() {
		return jwtExpiration;
	}
	
	public void setJwtExpiration(String jwtExpiration) {
		this.jwtExpiration = jwtExpiration;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

}
