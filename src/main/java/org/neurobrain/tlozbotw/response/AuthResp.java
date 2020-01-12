package org.neurobrain.tlozbotw.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neurobrain.tlozbotw.entity.Role;
import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthResp {

	private final Response response;


	public AuthResp(Response response) {
		this.response = response;
	}


	public ResponseEntity<Object> signUpResp(User user, String message) {
		Map out = response.toMap(
			user,
			"password",
			"recoverCode",
			"blocked",
			"delete",
			"firstSession"
		);

		out.put("roles", user.getFormatRoles());

		return response.created(message,out);
	}

	public ResponseEntity<Object> signInResp(String token, User user) {
		Map out = response.toMap(
			user,
			"password",
			"recoverCode",
			"blocked",
			"delete"
		);

		out.put("roles", user.getFormatRoles());
		out.put("type", "Bearer");
		out.put("token", token);

		return response.ok(out);
	}

	public ResponseEntity<Object> recoverPassword(String message) {
		return response.ok(message);
	}

	public ResponseEntity<Object> changePassword(String message) {
		return response.ok(message);
	}

}
