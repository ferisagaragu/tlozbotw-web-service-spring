package org.neurobrain.tlozbotw.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class HttpExceptionResponse {

	private static final Logger logger = LoggerFactory.getLogger(HttpExceptionResponse.class);

	public ResponseEntity<Object> error(ResponseStatusException e) {
		Map<String, Object> resp = new LinkedHashMap<>();
		resp.put("timestamp", new Date());
		resp.put("status", e.getStatus().value());
		resp.put("error", e.getStatus());
		resp.put("message", e.getReason());

		try {
			Object developMessage = e.getClass().getMethod(
				"getDevelopMessage"
			).invoke(e);

			if (developMessage != null) {
				resp.put("developMessage", developMessage);
			}
		} catch (Exception ex) {
			logger.info("No develop message send");
		}

		logger.error(e.getMessage());
		return new ResponseEntity<>(
			resp,
			e.getStatus()
		);
	}

}
