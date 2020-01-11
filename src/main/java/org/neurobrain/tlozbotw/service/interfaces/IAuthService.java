package org.neurobrain.tlozbotw.service.interfaces;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public interface IAuthService {
	ResponseEntity<Object> signUp(Map<String, Object> req) throws ResponseStatusException;
	ResponseEntity<Object> signin(Map<String, Object> req);
	ResponseEntity<Object> recoverPassword(Map<String, Object> req) throws ResponseStatusException;
	ResponseEntity<Object> changePassword(Map<String, Object> req) throws ResponseStatusException;
}
