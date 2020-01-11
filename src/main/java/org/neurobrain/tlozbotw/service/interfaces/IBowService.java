package org.neurobrain.tlozbotw.service.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IBowService {
	ResponseEntity<Object> getAllBow(Long id);
	ResponseEntity<Object> createBow(Map<String, Object> req);
}
