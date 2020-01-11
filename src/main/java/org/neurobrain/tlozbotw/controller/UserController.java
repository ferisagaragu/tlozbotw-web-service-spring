package org.neurobrain.tlozbotw.controller;

import java.util.Map;

import org.neurobrain.tlozbotw.exception.HttpExceptionResponse;
import org.neurobrain.tlozbotw.service.interfaces.IUserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

	private final IUserService userService;
	private final HttpExceptionResponse httpExceptionResponse;


	public UserController(
		IUserService userService,
		HttpExceptionResponse httpExceptionResponse
	) {
		this.userService = userService;
		this.httpExceptionResponse = httpExceptionResponse;
	}


	/**
	 	@apiGroup User
		@apiVersion 0.0.1
		@apiDescription Servicio para iniciar sesión por primera vez
		@api {post} user/firstSignin/:id firstSignin
		@apiPermission {user} {admin}

		@apiParam {number} Id Identificador único

		@apiParamExample {json} Request-Body:
			{
	 			"password": "your password"
			}

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-03T04:05:37.126+0000",
				"status": 200,
				"message": "your success message"
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-04T21:55:33.365+0000",
				"status": 400,
				"error": "BAD_REQUEST",
				"message": "User already had his first login"
			}

		@apiErrorExample {json} HTTP/1.1 401 Unauthorized
			{
				"timestamp": "2020-01-03T04:17:06.006+0000",
				"status": 401,
				"error": "Unauthorized",
				"message": "Full authentication is required to access this resource"
			}

		@apiErrorExample {json} HTTP/1.1 500 Internal Server Error
			{
				"timestamp": "2020-01-03T17:37:02.348+0000",
				"status": 500,
				"error": "Internal Server Error",
				"message": "your error message"
			}
	*/
	@PostMapping("/firstSignin/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Object> firstSignin(
		@PathVariable("id") Long id,
		@RequestBody Map<String, Object> req
	) {
		try {
			return userService.firstSignin(id, req);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
		@apiGroup User
		@apiVersion 0.0.1
		@apiDescription Servicio para actualizar un usuario
		@api {put} user/update/:id update
		@apiPermission {user} {admin}

		@apiParam {number} Id Identificador único

		@apiParamExample {json} Request-Body:
			{
				"name": "your name",
				"lastName": "your last name",
				"phoneNumber": "your phone number",
				"imageUrl": "your account photo",
				"userName": "your user name"
			}

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-03T18:03:54.033+0000",
				"status": 200,
				"message": "your success message",
				"data": {
					"id": identify id,
					"name": "your name",
					"lastName": "your last name",
					"phoneNumber": "your phone number",
					"imageUrl": "your account photo",
					"userName": "your user name",
					"email": "your email",
					"roles": [
						"your roles"
					]
				}
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-03T18:13:58.035+0000",
				"status": 400,
				"error": "BAD_REQUEST",
				"message": "your problem message"
			}

		@apiErrorExample {json} HTTP/1.1 401 Unauthorized
			{
				"timestamp": "2020-01-03T04:17:06.006+0000",
				"status": 401,
				"error": "Unauthorized",
				"message": "Full authentication is required to access this resource"
			}

		@apiErrorExample {json} HTTP/1.1 500 Internal Server Error
			{
				"timestamp": "2020-01-03T17:37:02.348+0000",
				"status": 500,
				"error": "Internal Server Error",
				"message": "your error message"
			}
	*/
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Object> update(
		@PathVariable("id") Long id,
		@RequestBody Map<String, Object> req
	) {
		try {
			return userService.update(id, req);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
		@apiGroup User
		@apiVersion 0.0.1
		@apiDescription Servicio para bloquear y desbloquear un usuario
		@api {put} user/blocked/:id blocked
		@apiPermission {admin}

		@apiParam {number} Id Identificador único

		@apiParamExample {json} Request-Body:
			{
				"blocked": true | false
			}

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-03T18:35:29.813+0000",
				"status": 200,
				"message": "your success message"
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-03T18:13:58.035+0000",
				"status": 400,
				"error": "BAD_REQUEST",
				"message": "your problem message",
				"trace": "your trace catch",
				"path": "/user/blocked/:id"
			}

		@apiErrorExample {json} HTTP/1.1 401 Unauthorized
			{
				"timestamp": "2020-01-03T04:17:06.006+0000",
				"status": 401,
				"error": "Unauthorized",
				"message": "Full authentication is required to access this resource"
			}

		@apiErrorExample {json} HTTP/1.1 500 Internal Server Error
			{
				"timestamp": "2020-01-03T17:37:02.348+0000",
				"status": 500,
				"error": "Internal Server Error",
				"message": "your error message"
			}
	*/
	@PutMapping("/blocked/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> blocked(
		@PathVariable("id") Long id,
		@RequestBody Map<String, Object> req
	) {
		try {
			return userService.blocked(id, req);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
		@apiGroup User
		@apiVersion 0.0.1
		@apiDescription Servicio para eliminar un usuario (eliminación lógica)
		@api {delete} user/delete/:id delete
		@apiPermission {admin}

		@apiParam {number} Id Identificador único

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-03T18:41:46.305+0000",
				"status": 200,
				"message": "your success message"
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-03T18:13:58.035+0000",
				"status": 400,
				"error": "BAD_REQUEST",
				"message": "your problem message",
				"trace": "your trace catch"
			}

		@apiErrorExample {json} HTTP/1.1 401 Unauthorized
			{
				"timestamp": "2020-01-03T04:17:06.006+0000",
				"status": 401,
				"error": "Unauthorized",
				"message": "Full authentication is required to access this resource"
			}

		@apiErrorExample {json} HTTP/1.1 500 Internal Server Error
			{
				"timestamp": "2020-01-03T17:37:02.348+0000",
				"status": 500,
				"error": "Internal Server Error",
				"message": "your error message"
			}
	*/
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(
		@PathVariable("id") Long id
	) {
		try {
			return userService.delete(id);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

}
