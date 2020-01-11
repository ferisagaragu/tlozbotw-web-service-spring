package org.neurobrain.tlozbotw.service.interfaces;

import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
	ResponseEntity<Object> signUp(Map<String, Object> req);
	ResponseEntity<Object> signIn(Map<String, Object> req);
	ResponseEntity<Object> recoverPassword(Map<String, Object> req);
	ResponseEntity<Object> changePassword(Map<String, Object> req);
}
