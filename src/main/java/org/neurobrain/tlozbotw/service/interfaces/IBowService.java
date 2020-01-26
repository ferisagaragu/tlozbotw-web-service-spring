package org.neurobrain.tlozbotw.service.interfaces;

import org.neurobrain.tlozbotw.util.Request;
import org.springframework.http.ResponseEntity;


public interface IBowService {
	ResponseEntity<Object> getAllBow(Long id);
	ResponseEntity<Object> createBow(Request<String, Object> req);
	ResponseEntity<Object> updateBow(Long id, Request<String, Object> req);
	ResponseEntity<Object> deleteBow(Long id);
}
