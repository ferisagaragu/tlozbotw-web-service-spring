package org.neurobrain.tlozbotw.service.interfaces;

import org.neurobrain.tlozbotw.util.Request;
import org.springframework.http.ResponseEntity;

public interface IUserService {
	ResponseEntity<Object> getUser(Long id);
	ResponseEntity<Object> getAllUsers();
	ResponseEntity<Object> firstSignIn(Long id, Request<String, Object> req);
	ResponseEntity<Object> update(Long id, Request<String, Object> req);
	ResponseEntity<Object> lock(Long id, Request<String, Object> req);
	ResponseEntity<Object> delete(Long id);
}
