package org.neurobrain.tlozbotw.service.interfaces;

import org.neurobrain.tlozbotw.util.Request;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
	ResponseEntity<Object> signUp(Request<String, Object> request);
	ResponseEntity<Object> signIn(Request<String, Object> request);
	ResponseEntity<Object> recoverPassword(Request<String, Object> request);
	ResponseEntity<Object> changePassword(Request<String, Object> request);
}
