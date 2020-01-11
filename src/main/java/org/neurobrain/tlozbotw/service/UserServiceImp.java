package org.neurobrain.tlozbotw.service;

import java.util.Map;

import org.neurobrain.tlozbotw.dao.IUserDAO;
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

	@Value("${app.auth.user-blocked}")
	private String userBlockedM;

	@Value("${app.auth.user-unlocked}")
	private String userUnlocked;

	@Value("${app.auth.user-deleted}")
	private String userDeleted;

	@Value("${app.auth.user-was-signin}")
	private String userWasSignin;


	public UserServiceImp(Request request, UserResp response, PasswordEncoder encoder, IUserDAO userDao) {
		this.request = request;
		this.response = response;
		this.encoder = encoder;
		this.userDao = userDao;
	}


	@Override
	@Transactional
	public ResponseEntity<Object> firstSignin(Long id, Map<String, Object> req) {
		User user = userDao.findById(id).orElseThrow(() ->
			new BadRequestException(userNoExist)
		);

		if (user.isFirstSession()) {
			user.setFirstSession(false);
			user.setPassword(
				encoder.encode(
					request.getString(req, "password")
				)
			);
		} else {
			throw new BadRequestException(userWasSignin);
		}

		userDao.save(user);
		return response.firstSignin(userActivated);
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
		userDao.save(userUpdate);
		
		return response.updateUserResp(userUpdated, userUpdate);
	}


	@Override
	@Transactional
	public ResponseEntity<Object> blocked(Long id, Map<String, Object> req) {

		boolean blocked = request.getBoolean(req, "blocked");
		User userBlocked = userDao.findById(id).orElseThrow(() ->
			new BadRequestException(userNoExist)
		);
		userBlocked.setBlocked(blocked);
		userDao.save(userBlocked);

		if (blocked) {
			return response.blocked(userBlockedM);
		}

		return response.blocked(userUnlocked);
	}


	@Override
	@Transactional
	public ResponseEntity<Object> delete(Long id) {
		User userDelete = userDao.findById(id).orElseThrow(() ->
			new BadRequestException(userNoExist)
		);
		userDelete.setDelete(true);
		userDao.save(userDelete);

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
