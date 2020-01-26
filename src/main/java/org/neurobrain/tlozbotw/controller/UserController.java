package org.neurobrain.tlozbotw.controller;

import java.util.Map;

import org.neurobrain.tlozbotw.exception.HttpExceptionResponse;
import org.neurobrain.tlozbotw.service.interfaces.IUserService;

import org.neurobrain.tlozbotw.util.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
		@apiDescription Servicio para obtener un usuario por su id
		@api {get} user/:id getUser
		@apiPermission {admin}

		@apiParam {number} Id Identificador único

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-13T21:39:25.437+0000",
				"status": 200,
				"data": {
					"id": 1,
					"name": "Fernando",
					"lastName": "Aguirre",
					"phoneNumber": "+(52) 33-23-81-47-52",
					"imageUrl": "no linked",
					"userName": "fernnypay95",
					"email": "ferisagaragu@gmail.com",
					"locked": false,
					"enabled": true,
					"roles": [
						"ROLE_USER",
						"ROLE_ADMIN"
					]
				}
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-13T17:54:01.902+0000",
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
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getUser(@PathVariable Long id) {
		try {
			return userService.getUser(id);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
	 	@apiGroup User
		@apiVersion 0.0.1
		@apiDescription Servicio para obtener todos los usuarios
		@api {get} user getAllUsers
		@apiPermission {admin}

		@apiSuccessExample {json} HTTP/1.1 200 OK
			{
				"timestamp": "2020-01-13T21:25:34.352+0000",
				"status": 200,
				"data": [
					{
						"id": user id,
						"name": "user name",
						"lastName": "user lastName",
						"phoneNumber": "user phoneNumber",
						"imageUrl": "user image",
						"userName": "user name",
						"email": "user email",
						"locked": true | false,
						"enabled": true | false,
						"roles": [
							"your roles"
						]
					}, ...
				]
			}

		@apiErrorExample {json} HTTP/1.1 400 Bad Request
			{
				"timestamp": "2020-01-13T17:54:01.902+0000",
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
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getAllUsers() {
		try {
			return userService.getAllUsers();
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
	 	@apiGroup User
		@apiVersion 0.0.1
		@apiDescription Servicio para iniciar sesión por primera vez
		@api {post} user/firstSignIn/:id firstSignIn
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
				"timestamp": "2020-01-13T17:54:01.902+0000",
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
	@PostMapping("/firstSignIn/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Object> firstSignIn(
		@PathVariable("id") Long id,
		@RequestBody Request<String, Object> request
	) {
		try {
			return userService.firstSignIn(id, request);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
		@apiGroup User
		@apiVersion 0.0.1
		@apiDescription Servicio para actualizar un usuario
		@api {put} user/:id update
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
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Object> update(
		@PathVariable("id") Long id,
		@RequestBody Request<String, Object> request
	) {
		try {
			return userService.update(id, request);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
		@apiGroup User
		@apiVersion 0.0.1
		@apiDescription Servicio para bloquear y desbloquear un usuario
		@api {put} user/lock/:id lock
		@apiPermission {admin}

		@apiParam {number} Id Identificador único

		@apiParamExample {json} Request-Body:
			{
				"locked": true | false,
				"reasons": "your reasons",
				"userId": user id
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
	@PutMapping("/lock/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> lock(
		@PathVariable("id") Long id,
		@RequestBody Request<String, Object> request
	) {
		try {
			return userService.lock(id, request);
		} catch (ResponseStatusException e) {
			return httpExceptionResponse.error(e);
		}
	}

	/**
		@apiGroup User
		@apiVersion 0.0.1
		@apiDescription Servicio para eliminar un usuario (eliminación lógica)
		@api {delete} user/:id delete
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
	@DeleteMapping("/{id}")
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
