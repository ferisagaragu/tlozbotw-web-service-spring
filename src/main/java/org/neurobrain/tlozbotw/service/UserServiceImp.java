package org.neurobrain.tlozbotw.service;

import java.util.ArrayList;
import java.util.Map;

import org.neurobrain.tlozbotw.dao.IRoleDAO;
import org.neurobrain.tlozbotw.dao.IUserDAO;
import org.neurobrain.tlozbotw.entity.Role;
import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.exception.BadRequestException;
import org.neurobrain.tlozbotw.response.UserResp;
import org.neurobrain.tlozbotw.service.interfaces.IUserService;
import org.neurobrain.tlozbotw.util.Request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImp implements IUserService {

	private final Request request;
	private final UserResp response;
	private final PasswordEncoder encoder;
	private final IUserDAO userDao;
	private final IRoleDAO roleDao;
	
	@Value("${app.auth.user-no-exist}")
	private String userNoExist;
	
	@Value("${app.auth.user-activated}")
	private String userActivated;
	
	@Value("${app.auth.user-update}")
	private String userUpdated;
	
	@Value("${app.auth.user-exist}")
	private String userExist;
	
	@Value("${app.auth.phone-number-exist}")
	private String phoneNumberExist;

	@Value("${app.auth.user-locked}")
	private String userLockedM;

	@Value("${app.auth.user-unlocked}")
	private String userUnlocked;

	@Value("${app.auth.user-deleted}")
	private String userDeleted;

	@Value("${app.auth.user-was-signin}")
	private String userWasSignin;


	public UserServiceImp(
		Request request,
		UserResp response,
		PasswordEncoder encoder,
		IUserDAO userDao,
		IRoleDAO roleDao
	) {
		this.request = request;
		this.response = response;
		this.encoder = encoder;
		this.userDao = userDao;
		this.roleDao = roleDao;
	}


	@Override
	@Transactional
	public ResponseEntity<Object> firstSignIn(Long id, Map<String, Object> req) {
		User user = userDao.findById(id).orElseThrow(() ->
			new BadRequestException(userNoExist)
		);

		if (user.getFirstSession()) {
			user.setFirstSession(false);
			user.setPassword(
				encoder.encode(
					request.getString(req, "password")
				)
			);
		} else {
			throw new BadRequestException(userWasSignin);
		}

		userDao.saveAndFlush(user);
		return response.firstSignIn(userActivated);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> update(Long id, Map<String, Object> req) {
		User userUpdate = userDao.findById(id).orElseThrow(() -> 
			new BadRequestException(userNoExist)
		); 
		
		userOrPhoneNumberExist(id, req);
		
		userUpdate.setUserName(request.getString(req, "userName"));
		userUpdate.setPhoneNumber(request.getString(req, "phoneNumber"));
		userUpdate.setLastName(request.getString(req, "lastName"));
		userUpdate.setImageUrl(request.getString(req, "imageUrl"));
		userUpdate.setName(request.getString(req, "name"));
		userDao.saveAndFlush(userUpdate);
		
		return response.updateUserResp(userUpdated, userUpdate);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> lock(Long id, Map<String, Object> req) {

		boolean locked = request.getBoolean(req, "locked");
		User userBlocked = userDao.findById(id).orElseThrow(() ->
			new BadRequestException(userNoExist)
		);
		userBlocked.setLocked(locked);
		userDao.saveAndFlush(userBlocked);

		if (locked) {
			return response.lock(userLockedM);
		}

		return response.lock(userUnlocked);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> delete(Long id) {
		User userDelete = userDao.findById(id).orElseThrow(() ->
			new BadRequestException(userNoExist)
		);
		userDelete.setEnabled(false);
		userDelete.getRoles().clear();
		userDao.saveAndFlush(userDelete);

		return response.delete(userDeleted);
	}


	private void userOrPhoneNumberExist(Long id, Map<String, Object> req) {
		User user = userDao.findByUserName(
			request.getString(req, "userName")
		).orElse(null);
		if (notExistUser(id, user)) {
			throw new BadRequestException(userExist);
		}
		
		User userPhoneNumber = userDao.findByPhoneNumber(
			request.getString(req, "phoneNumber")
		).orElse(null);
		if (notExistUser(id, userPhoneNumber)) {
			throw new BadRequestException(phoneNumberExist);
		}
	}

	private boolean notExistUser(Long id, User user) {
		if (user == null) {
			return false;
		}

		return !id.equals(user.getId());
	}

}
