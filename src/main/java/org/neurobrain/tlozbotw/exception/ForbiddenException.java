package org.neurobrain.tlozbotw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenException extends ResponseStatusException {

	private String developMessage;

	public ForbiddenException(String reason) {
		super(HttpStatus.FORBIDDEN, reason);
	}

	public ForbiddenException(String reason, String developMessage) {
		super(HttpStatus.FORBIDDEN, reason);
		this.developMessage = developMessage;
	}

	public String getDevelopMessage() {
		return developMessage;
	}

}