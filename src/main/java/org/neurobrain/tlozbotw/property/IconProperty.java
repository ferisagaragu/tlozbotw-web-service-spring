package org.neurobrain.tlozbotw.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.icon")
public class IconProperty {

	private String password;
	private String success;
	private String warning;
	private String bad;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public String getBad() {
		return bad;
	}

	public void setBad(String bad) {
		this.bad = bad;
	}

}
