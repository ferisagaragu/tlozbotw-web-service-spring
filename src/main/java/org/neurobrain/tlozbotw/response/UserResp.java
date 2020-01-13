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
		Map resp = response.toMap(
			user,
			new String[]{"getFormatRoles as roles"},
			"password",
			"recoverCode",
			"firstSession"
		);
		return response.ok(resp);
	}

	public ResponseEntity<Object> getAllUsersResp(List<User> users) {
		List resp = response.toListMap(
			users,
			new String[]{"getFormatRoles as roles"},
			"password",
			"recoverCode",
			"firstSession"
		);
		return response.ok(resp);
	}

	public ResponseEntity<Object> firstSignInResp(String message) {
		return response.ok(message);
	}

	public ResponseEntity<Object> updateUserResp(String message, User user) {
		Map resp = response.toMap(
			user,
			new String[]{"getFormatRoles as roles"},
			"password",
			"recoverCode",
			"blocked",
			"delete",
			"firstSession"
		);
		return response.ok(message, resp);
	}

	public ResponseEntity<Object> lockResp(String message) {
		return response.ok(message);
	}

	public ResponseEntity<Object> deleteResp(String message) {
		return response.ok(message);
	}

}
