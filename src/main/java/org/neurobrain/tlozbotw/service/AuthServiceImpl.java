package org.neurobrain.tlozbotw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neurobrain.tlozbotw.dao.IRoleDAO;
import org.neurobrain.tlozbotw.dao.IUserDAO;
import org.neurobrain.tlozbotw.entity.Role;
import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.exception.BadRequestException;
import org.neurobrain.tlozbotw.exception.UnauthorizedException;
import org.neurobrain.tlozbotw.response.AuthResp;
import org.neurobrain.tlozbotw.security.JwtProvider;
import org.neurobrain.tlozbotw.security.UserPrinciple;
import org.neurobrain.tlozbotw.service.interfaces.IAuthService;
import org.neurobrain.tlozbotw.util.Mail;
import org.neurobrain.tlozbotw.util.Request;
import org.neurobrain.tlozbotw.util.Resource;
import org.neurobrain.tlozbotw.util.Text;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthServiceImpl implements IAuthService, UserDetailsService {

	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder encoder;
	private final Mail mail;
	private final Resource resource;
	private final AuthResp response;
	private final Request request;
	private final IUserDAO userDao;
	private final IRoleDAO roleDao;
	private final Text text;
 
	@Value("${app.auth.user-no-exist}")
	private String userNoExist;
	
	@Value("${app.auth.user-no-password}")
	private String userNoPassowrd;
	
	@Value("${app.auth.user-exist}")
	private String userExist;
	
	@Value("${app.auth.user-created}")
	private String userCreated;

	@Value("${app.auth.user-change-password}")
	private String userChangePassword;

	@Value("${app.auth.user-recover-password}")
	private String userRecoverPassword;

	@Value("${app.auth.mail-exist}")
	private String mailExist;
	
	@Value("${app.auth.phone-number-exist}")
	private String phoneNumberExist;
	
	@Value("${app.auth.mail-subject}")
	private String mailSubject;
	
	@Value("${app.auth.mail-message}")
	private String mailMessage;
	
	@Value("${app.auth.mail-sub-message}")
	private String mailSubMessage;
	
	@Value("${app.auth.mail-app-name}")
	private String mailAppName;
			
	@Value("${app.auth.mail-app-description}")
	private String mailAppDescription;

	@Value("${app.auth.mail-recover-message}")
	private String mailRecoverMessage;

	@Value("${app.auth.mail-recover-sub-message}")
	private String mailRecoverSubMessage;

	private String userNameRef;
	private String emailRef;


	public AuthServiceImpl(
		AuthenticationManager authenticationManager,
		JwtProvider jwtProvider,
		PasswordEncoder encoder,
		Mail mail,
		Resource resource,
		AuthResp response,
		Request request,
		IUserDAO userDao,
		IRoleDAO roleDao,
		Text text
	) {
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.encoder = encoder;
		this.mail = mail;
		this.resource = resource;
		this.response = response;
		this.request = request;
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.text = text;
		this.userNameRef = "userName";
		this.emailRef = "email";
	}


	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userName) {
		User user = userDao.findByUserName(userName).orElseThrow(() ->
			new UsernameNotFoundException(userNoExist)
		);
			
		return UserPrinciple.build(user);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> signUp(Map<String, Object> req) {
		existUser(req);
		
		String password = text.uniqueString();
		User user = new User();
		user.setName(request.getString(req, "name"));
		user.setLastName(request.getString(req, "lastName"));
		user.setPhoneNumber(request.getString(req, "phoneNumber"));
		user.setImageUrl(request.getString(req, "imageUrl"));
		user.setUserName(request.getString(req, this.userNameRef));
		user.setEmail(request.getString(req, this.emailRef));
		user.setPassword(encoder.encode(password));
		user.setFirstSession(true);

		List<Role> roles = new ArrayList<>();
		roles.add(roleDao.findById(1l).orElse(null));
		user.setRoles(roles);
		User userOut = userDao.saveAndFlush(user);
		
		mail.send(
			mailSubject,
			resource.passwordTemplate(
				userOut.getName(),
				mailMessage,
				mailSubMessage,
				password,
				mailAppName,
				mailAppDescription
			),
			userOut.getEmail()
		);
		
		return response.signUpResp(userOut, userCreated);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> signIn(Map<String, Object> req) {
		String userName = request.getString(req, this.userNameRef);
		User user = searchUserName(userName);
		String jwt;
		
		try {
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					user.getUserName(),
					request.getString(req, "password")
				)
			);
	 
			SecurityContextHolder.getContext().setAuthentication(authentication);
			jwt = jwtProvider.generateJwtToken(authentication);
			user.setRecoverCode("");
			userDao.saveAndFlush(user);
		} catch (Exception e) {
			throw new UnauthorizedException(e.getMessage());
		}
		
		return response.signInResp(jwt, user);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> recoverPassword(Map<String, Object> req) {
		User userEmail = userDao.findByEmail(request.getString(req, "email"))
			.orElseThrow(() -> new BadRequestException(userNoExist)
		);

		String password = text.uniqueString();

		mail.send(
			mailSubject,
			resource.passwordTemplate(
				userEmail.getName(),
				mailRecoverMessage,
				mailRecoverSubMessage,
				password,
				mailAppName,
				mailAppDescription
			),
			userEmail.getEmail()
		);

		userEmail.setRecoverCode(password);
		userDao.saveAndFlush(userEmail);
		return response.recoverPassword(userRecoverPassword);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> changePassword(Map<String, Object> req) {
		User userChange = userDao.findByRecoverCode(request.getString(req, "code"))
			.orElseThrow(() -> new BadRequestException(userNoExist)
		);

		userChange.setPassword(
			encoder.encode(
				request.getString(req, "password")
			)
		);

		userChange.setRecoverCode("");
		userDao.saveAndFlush(userChange);
		return response.changePassword(userChangePassword);
	}


	private void existUser(Map<String, Object> req) {
		User userName = userDao.findByUserName(
			request.getString(req, this.userNameRef)
		).orElse(null);
		if (userName != null) {
			throw new BadRequestException(userExist);
		}
		
		User userEmail = userDao.findByEmail(
			request.getString(req, this.emailRef)
		).orElse(null);
		if (userEmail != null) {
			throw new BadRequestException(mailExist);
		}
		
		User userPhone = userDao.findByPhoneNumber(
			request.getString(req, "phoneNumber")
		).orElse(null);
		if (userPhone != null) {
			throw new BadRequestException(phoneNumberExist);
		}
	}

	private User searchUserName(String userName) {
		User user = userDao.findByUserName(userName).orElse(null);
		
		if (user == null) {
			user = userDao.findByEmail(userName).orElse(null);
			if (user == null) {
				user = userDao.findByPhoneNumber(userName).orElseThrow(() ->
					new UnauthorizedException(userNoExist)
				);
			}
		}
		
		return user;
	}

}
