package org.neurobrain.tlozbotw.response;

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


	public ResponseEntity<Object> firstSignIn(String message) {
		return response.ok(message);
	}

	public ResponseEntity<Object> updateUserResp(String message, User user) {
		Map resp = response.toMap(
			user,
			"password",
			"recoverCode",
			"blocked",
			"delete",
			"firstSession"
		);

		resp.put("roles", user.getFormatRoles());
		
		return response.ok(message, resp);
	}

	public ResponseEntity<Object> lock(String message) {
		return response.ok(message);
	}

	public ResponseEntity<Object> delete(String message) {
		return response.ok(message);
	}

}
