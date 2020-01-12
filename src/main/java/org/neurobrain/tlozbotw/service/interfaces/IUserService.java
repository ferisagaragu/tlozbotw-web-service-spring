package org.neurobrain.tlozbotw.service.interfaces;

import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface IUserService {
	ResponseEntity<Object> firstSignIn(Long id, Map<String, Object> req);
	ResponseEntity<Object> update(Long id, Map<String, Object> req);
	ResponseEntity<Object> lock(Long id, Map<String, Object> req);
	ResponseEntity<Object> delete(Long id);
}
