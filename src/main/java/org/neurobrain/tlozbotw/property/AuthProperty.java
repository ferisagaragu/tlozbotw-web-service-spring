package org.neurobrain.tlozbotw.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.auth")
public class AuthProperty {

	private String jwtSecret;
	private String jwtExpiration;
	
	private String mailUser;
	private String mailPassword;
	
	private String mailSubject;
	private String mailMessage;
	private String mailSubMessage;
	private String mailAppName;
	private String mailAppDescription;
	private String mailRecoverMessage;
	private String mailRecoverSubMessage;

	private String userNoExist;
	private String userNoPassword;
	private String userExist;
	private String mailExist;
	private String phoneNumberExist;
	private String userCreated;
	private String userActivated;
	private String userUpdate;
	private String userBlocked;
	private String userUnlocked;
	private String userDeleted;
	private String userRecoverPassword;
	private String userChangePassword;
	private String userWasSignin;

	
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

	public String getUserNoExist() {
		return userNoExist;
	}

	public void setUserNoExist(String userNoExist) {
		this.userNoExist = userNoExist;
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

	public String getUserNoPassword() {
		return userNoPassword;
	}

	public void setUserNoPassword(String userNoPassword) {
		this.userNoPassword = userNoPassword;
	}

	public String getUserExist() {
		return userExist;
	}

	public void setUserExist(String userExist) {
		this.userExist = userExist;
	}

	public String getMailExist() {
		return mailExist;
	}

	public void setMailExist(String emailExist) {
		this.mailExist = emailExist;
	}

	public String getPhoneNumberExist() {
		return phoneNumberExist;
	}

	public void setPhoneNumberExist(String phoneNumberExist) {
		this.phoneNumberExist = phoneNumberExist;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(String mailMessage) {
		this.mailMessage = mailMessage;
	}

	public String getMailSubMessage() {
		return mailSubMessage;
	}

	public void setMailSubMessage(String mailSubMessage) {
		this.mailSubMessage = mailSubMessage;
	}

	public String getMailAppName() {
		return mailAppName;
	}

	public void setMailAppName(String mailAppName) {
		this.mailAppName = mailAppName;
	}

	public String getMailAppDescription() {
		return mailAppDescription;
	}

	public void setMailAppDescription(String mailAppDescription) {
		this.mailAppDescription = mailAppDescription;
	}

	public String getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
	}

	public String getUserActivated() {
		return userActivated;
	}

	public void setUserActivated(String userActivated) {
		this.userActivated = userActivated;
	}

	public String getUserUpdate() {
		return userUpdate;
	}

	public void setUserUpdate(String userUpdate) {
		this.userUpdate = userUpdate;
	}

	public String getUserBlocked() {
		return userBlocked;
	}

	public void setUserBlocked(String userBlocked) {
		this.userBlocked = userBlocked;
	}

	public String getUserUnlocked() {
		return userUnlocked;
	}

	public void setUserUnlocked(String userUnlocked) {
		this.userUnlocked = userUnlocked;
	}

	public String getUserDeleted() {
		return userDeleted;
	}

	public void setUserDeleted(String userDeleted) {
		this.userDeleted = userDeleted;
	}

	public String getUserRecoverPassword() {
		return userRecoverPassword;
	}

	public void setUserRecoverPassword(String userRecoverPassword) {
		this.userRecoverPassword = userRecoverPassword;
	}

	public String getUserChangePassword() {
		return userChangePassword;
	}

	public void setUserChangePassword(String userChangePassword) {
		this.userChangePassword = userChangePassword;
	}

	public String getMailRecoverMessage() {
		return mailRecoverMessage;
	}

	public void setMailRecoverMessage(String mailRecoverMessage) {
		this.mailRecoverMessage = mailRecoverMessage;
	}

	public String getMailRecoverSubMessage() {
		return mailRecoverSubMessage;
	}

	public void setMailRecoverSubMessage(String mailRecoverSubMessage) {
		this.mailRecoverSubMessage = mailRecoverSubMessage;
	}

	public String getUserWasSignin() {
		return userWasSignin;
	}

	public void setUserWasSignin(String userWasSignin) {
		this.userWasSignin = userWasSignin;
	}

}
