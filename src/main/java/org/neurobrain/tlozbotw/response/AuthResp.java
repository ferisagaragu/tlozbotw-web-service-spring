package org.neurobrain.tlozbotw.response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.neurobrain.tlozbotw.entity.Role;
import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthResp {
	
	@Autowired
	private Response response;
	
	
	public ResponseEntity<Object> signUpResp(User user, String message) {
		
		/*Map<String, Object> resp = new LinkedHashMap<>();
		resp.put("id", user.getId());
		resp.put("name", user.getName());
		resp.put("lastName", user.getLastName());
		resp.put("phoneNumber", user.getPhoneNumber());
		resp.put("imageUrl", user.getImageUrl());
		resp.put("userName", user.getUserName());
		resp.put("email", user.getEmail());*/
				
		return response.created(
			message,
			response.toMap(
				user,
				"password",
				"recoverCode",
				"blocked",
				"delete"
			)
		);
	}
	
	
	public ResponseEntity<Object> signinResp(String token, User user) {
	
		Map<String, Object> resp = new LinkedHashMap<>();
		
		resp.put("id", user.getId());
		resp.put("type", "Bearer");
		resp.put("token", token);
		
		resp.put("name", user.getName());
		resp.put("lastName", user.getLastName());
		resp.put("phoneNumber", user.getPhoneNumber());
		resp.put("imageUrl", user.getImageUrl());
		resp.put("userName", user.getUserName());
		resp.put("email", user.getEmail());
		resp.put("firstSession", user.isFirstSession());
		List<String> roles = new ArrayList<>();
	    
		for (Role role : user.getRoles()) {
			roles.add(role.getName());
		}
	    
		resp.put("roles", roles);
	    
		return response.ok(resp);
	}


	public ResponseEntity<Object> recoverPassword(String message) {
		return response.ok(message);
	}


	public ResponseEntity<Object> changePassword(String message) {
		return response.ok(message);
	}

}
