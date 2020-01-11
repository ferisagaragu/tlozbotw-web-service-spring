package org.neurobrain.tlozbotw.controller;

import java.util.Map;

import org.neurobrain.tlozbotw.exception.HttpExceptionResponse;
import org.neurobrain.tlozbotw.service.interfaces.IAuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
      
	private final IAuthService authService;
	private final HttpExceptionResponse httpExceptionResponse;


	public AuthController(IAuthService authService, HttpExceptionResponse httpExceptionResponse) {
		this.authService = authService;
		this.httpExceptionResponse = httpExceptionResponse;
	}


	/**
	 	@apiGroup Auth
		@apiVersion 0.0.1
		@apiDescription Servicio para registrar un nuevo usuario
		@api {post} auth/signUp/ signUp
		@apiPermission none

		@apiParamExample {json} Request-Body:
			{
				"name": "your user name",
				"lastName": "your last name",
				"phoneNumber": "your phone number",
				"imageUrl": "your account photo",
				"userName": "your user name",
				"email": "your email"
			}

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-03T04:05:37.126+0000",
				"status": 200,
				"message": "your success message"
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-03T16:42:13.727+0000",
				"status": 400,
				"error": "BAD_REQUEST",
				"message": "your problem message"
			}

		@apiErrorExample {json} HTTP/1.1 500 Internal Server Error
			{
				"timestamp": "2020-01-03T17:37:02.348+0000",
				"status": 500,
				"error": "Internal Server Error",
				"message": "your error message"
			}
	*/
	@PostMapping("/signUp")
	public ResponseEntity<Object> signup(
		@RequestBody Map<String, Object> req
	) {
		try {
			return authService.signUp(req);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}
    
	/**
	 	@apiGroup Auth
		@apiVersion 0.0.1
		@apiDescription Servicio para iniciar sesión
		@api {post} auth/signin signin
		@apiPermission none

		@apiParamExample {json} Request-Body:
			{
				"userName": "email | user name | phone number",
				"password": "your password"
			}

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-03T20:21:07.712+0000",
				"status": 200,
				"data": {
					"id": identify id,
					"type": "Bearer",
					"token": "your token",
					"name": "your name",
					"lastName": "your last name",
					"phoneNumber": "your phone number",
					"imageUrl": "your account photo",
					"userName": "your user name",
					"email": "your email",
					"firstSession": true | false,
					"roles": [
						"your roles"
					]
				}
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-03T16:42:13.727+0000",
				"status": 400,
				"error": "BAD_REQUEST",
				"message": "your problem message"
			}

		@apiErrorExample {json} HTTP/1.1 500 Internal Server Error
			{
				"timestamp": "2020-01-03T17:37:02.348+0000",
				"status": 500,
				"error": "Internal Server Error",
				"message": "your error message"
			}
	*/
	@PostMapping("/signin")
	public ResponseEntity<Object> signin(
		@RequestBody Map<String, Object> req
	) {
		try {
			return authService.signin(req);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
	 	@apiGroup Auth
		@apiVersion 0.0.1
		@apiDescription Servicio para recuperar contraseña del usuario
		@api {post} auth/recoverPassword recoverPassword
		@apiPermission none

		@apiParamExample {json} Request-Body:
			{
				"email": "your email"
			}

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-03T21:44:34.107+0000",
				"status": 200,
				"message": "your success message"
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-03T16:42:13.727+0000",
				"status": 400,
				"error": "BAD_REQUEST",
				"message": "your problem message"
			}

		@apiErrorExample {json} HTTP/1.1 500 Internal Server Error
			{
				"timestamp": "2020-01-03T17:37:02.348+0000",
				"status": 500,
				"error": "Internal Server Error",
				"message": "your error message"
			}
	*/
	@PostMapping("/recoverPassword")
	public ResponseEntity<Object> recoverPassword(
		@RequestBody Map<String, Object> req
	) {
		try {
			return authService.recoverPassword(req);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
	 	@apiGroup Auth
		@apiVersion 0.0.1
		@apiDescription Servicio para cambiar la contraseña recuperada
		@api {post} auth/changePassword changePassword
		@apiPermission none

		@apiParamExample {json} Request-Body:
			{
				"code": "your code",
				"password": "your new password"
			}

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-03T21:45:16.951+0000",
				"status": 200,
				"message": "your success message"
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-03T16:42:13.727+0000",
				"status": 400,
				"error": "BAD_REQUEST",
				"message": "your problem message"
			}

		@apiErrorExample {json} HTTP/1.1 500 Internal Server Error
			{
				"timestamp": "2020-01-03T17:37:02.348+0000",
				"status": 500,
				"error": "Internal Server Error",
				"message": "your error message"
			}
	*/
	@PostMapping("/changePassword")
	public ResponseEntity<Object> changePassword(
		@RequestBody Map<String, Object> req
	)	{
		try {
			return authService.changePassword(req);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

}
