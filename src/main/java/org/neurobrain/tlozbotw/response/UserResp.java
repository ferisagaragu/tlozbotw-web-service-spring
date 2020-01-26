package org.neurobrain.tlozbotw.response;

import java.util.List;
import java.util.Map;

import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.util.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserResp {

	private final Response response;


	public UserResp(Response response) {
		this.response = response;
	}


	public ResponseEntity<Object> getUserResp(User user) {
		Map response = this.response.toMap(
			user,
			new String[]{"getFormatRoles as roles"},
			"password",
			"recoverCode",
			"firstSession"
		);
		return this.response.ok(response);
	}

	public ResponseEntity<Object> getAllUsersResp(List<User> users) {
		List response = this.response.toListMap(
			users,
			new String[]{"getFormatRoles as roles"},
			"password",
			"recoverCode",
			"firstSession"
		);
		return this.response.ok(response);
	}

	public ResponseEntity<Object> firstSignInResp(String message) {
		return response.ok(message);
	}

	public ResponseEntity<Object> updateUserResp(String message, User user) {
		Map response = this.response.toMap(
			user,
			new String[]{"getFormatRoles as roles"},
			"password",
			"recoverCode",
			"blocked",
			"delete",
			"firstSession"
		);
		return this.response.ok(message, response);
	}

	public ResponseEntity<Object> lockResp(String message) {
		return response.ok(message);
	}

	public ResponseEntity<Object> deleteResp(String message) {
		return response.ok(message);
	}

}
