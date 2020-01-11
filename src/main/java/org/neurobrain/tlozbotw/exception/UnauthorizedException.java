package org.neurobrain.tlozbotw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {

	private String developMessage;

	public UnauthorizedException(String reason) {
		super(HttpStatus.UNAUTHORIZED, reason);
	}

	public UnauthorizedException(String reason, String developMessage) {
		super(HttpStatus.FORBIDDEN, reason);
		this.developMessage = developMessage;
	}

	public String getDevelopMessage() {
		return developMessage;
	}

}