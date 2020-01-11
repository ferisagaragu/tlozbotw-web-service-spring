package org.neurobrain.tlozbotw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InternalServerErrorException extends ResponseStatusException {

	private String developMessage;


	public InternalServerErrorException(String reason) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
	}

	public InternalServerErrorException(String reason, String developMessage) {
		super(HttpStatus.FORBIDDEN, reason);
		this.developMessage = developMessage;
	}

	public String getDevelopMessage() {
		return developMessage;
	}

}
