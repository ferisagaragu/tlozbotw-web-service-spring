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
public class UserResp {

	@Autowired
	private Response response;
	
	
	public ResponseEntity<Object> firstSignin(String message) {
		return response.ok(message);
	}
	
	
	public ResponseEntity<Object> updateUserResp(String message, User user) {
		Map<String, Object> resp = new LinkedHashMap<>();
		
		resp.put("id", user.getId());
		resp.put("name", user.getName());
		resp.put("lastName", user.getLastName());
		resp.put("phoneNumber", user.getPhoneNumber());
		resp.put("imageUrl", user.getImageUrl());
		resp.put("userName", user.getUserName());
		resp.put("email", user.getEmail());
		List<String> roles = new ArrayList<>();
	    
		for (Role role : user.getRoles()) {
			roles.add(role.getName());
		}
	    
		resp.put("roles", roles);
		
		return response.ok(message, resp);
	}


	public ResponseEntity<Object> blocked(String message) {
		return response.ok(message);
	}


	public ResponseEntity<Object> delete(String message) {
		return response.ok(message);
	}

}
