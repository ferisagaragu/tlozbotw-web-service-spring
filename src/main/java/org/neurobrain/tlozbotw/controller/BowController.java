package org.neurobrain.tlozbotw.controller;

import org.neurobrain.tlozbotw.exception.HttpExceptionResponse;
import org.neurobrain.tlozbotw.service.interfaces.IBowService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bow")
public class BowController {

	private final IBowService bowService;
	private final HttpExceptionResponse httpExceptionResponse;


	public BowController(
		IBowService bowService,
		HttpExceptionResponse httpExceptionResponse
	) {
		this.bowService = bowService;
		this.httpExceptionResponse = httpExceptionResponse;
	}


	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Object> getAllBow(@PathVariable Long id) {
		try {
			return bowService.getAllBow(id);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> createBow(@RequestBody Map<String, Object> req) {
		try {
			return bowService.createBow(req);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

}
