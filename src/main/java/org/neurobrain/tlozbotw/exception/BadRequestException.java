package org.neurobrain.tlozbotw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {

	private String developMessage;

	public BadRequestException(String reason) {
		super(HttpStatus.BAD_REQUEST, reason);
	}

	public BadRequestException(String reason, String developMessage) {
		super(HttpStatus.BAD_REQUEST, reason);
		this.developMessage = developMessage;
	}

	public String getDevelopMessage() {
		return developMessage;
	}

}