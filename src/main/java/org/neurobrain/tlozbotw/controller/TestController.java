package org.neurobrain.tlozbotw.controller;

import org.neurobrain.tlozbotw.dao.IBowDAO;
import org.neurobrain.tlozbotw.dao.IUserDAO;
import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.util.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/test")
public class TestController {

	private final Response response;
	private final IUserDAO userDAO;
	private final IBowDAO bowDAO;

	public TestController(Response response, IUserDAO userDAO, IBowDAO bowDAO) {
		this.response = response;
		this.userDAO = userDAO;
		this.bowDAO = bowDAO;
	}

	@GetMapping
	public ResponseEntity<Object> dao() {
		User user = userDAO.findById(1L).orElse(null);
		System.out.println(user);
		return response.ok(/*response.toMap(*/user/*, "password", "id", "recoverCode", "firstSession")*/);
	}

}
