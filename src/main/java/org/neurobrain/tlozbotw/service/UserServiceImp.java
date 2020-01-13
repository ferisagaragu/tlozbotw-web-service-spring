package org.neurobrain.tlozbotw.service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.neurobrain.tlozbotw.dao.IRoleDAO;
import org.neurobrain.tlozbotw.dao.IUserDAO;
import org.neurobrain.tlozbotw.entity.LockReason;
import org.neurobrain.tlozbotw.entity.Role;
import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.enums.IconMail;
import org.neurobrain.tlozbotw.exception.BadRequestException;
import org.neurobrain.tlozbotw.response.UserResp;
import org.neurobrain.tlozbotw.service.interfaces.IUserService;
import org.neurobrain.tlozbotw.util.Mail;
import org.neurobrain.tlozbotw.util.Request;

import org.neurobrain.tlozbotw.util.Resource;
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
	private final Mail mail;
	private final Resource resource;


	public UserServiceImp(
		Request request,
		UserResp response,
		PasswordEncoder encoder,
		IUserDAO userDao,
		IRoleDAO roleDao,
		Mail mail,
		Resource resource
	) {
		this.request = request;
		this.response = response;
		this.encoder = encoder;
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.mail = mail;
		this.resource = resource;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Object> getUser(Long id) {
		User searchUser = userDao.findById(id).orElseThrow(() ->
			new BadRequestException("Upps usuario no encontrado")
		);
		return response.getUserResp(searchUser);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Object> getAllUsers() {
		List<User> users = userDao.findAll();
		return response.getAllUsersResp(users);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> firstSignIn(Long id, Map<String, Object> req) {
		User user = userDao.findById(id).orElseThrow(() ->
			new BadRequestException("Upps usuario no encontrado")
		);

		if (user.getFirstSession()) {
			user.setFirstSession(false);
			user.setPassword(
				encoder.encode(
					request.getString(req, "password")
				)
			);
			sendMailFirstSignIn(user);
		} else {
			throw new BadRequestException(
				"Upps ya has tenido tu primer sesión con anterioridad"
			);
		}

		userDao.saveAndFlush(user);
		return response.firstSignInResp("Usuario activado exitosamente");
	}

	@Override
	@Transactional
	public ResponseEntity<Object> update(Long id, Map<String, Object> req) {
		User userUpdate = userDao.findById(id).orElseThrow(() -> 
			new BadRequestException("Upps usuario no encontrado")
		); 
		
		userOrPhoneNumberExist(id, req);
		
		userUpdate.setUserName(request.getString(req, "userName"));
		userUpdate.setPhoneNumber(request.getString(req, "phoneNumber"));
		userUpdate.setLastName(request.getString(req, "lastName"));
		userUpdate.setImageUrl(request.getString(req, "imageUrl"));
		userUpdate.setName(request.getString(req, "name"));
		userDao.saveAndFlush(userUpdate);
		
		return response.updateUserResp(
			"Usuario actualizado satisfactoriamente",
			userUpdate
		);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> lock(Long id, Map<String, Object> req) {
		boolean locked = request.getBoolean(req, "locked");
		User userLocked = userDao.findById(request.getLong(req, "userId"))
			.orElseThrow(() -> new BadRequestException("Upps usuario no encontrado"));

		if (userLocked.containsRole("ADMIN")) {
			throw new BadRequestException(
				"El usuario seleccionado es administrador y no puede ser bloqueado"
			);
		}

		if (locked) {
			if (userLocked.getLockReasons().size() < 3) {
				User lockerUser = userDao.findById(id).orElseThrow(() ->
					new BadRequestException("Upps usuario no encontrado")
				);

				LockReason lockReason = new LockReason(
					request.getString(req, "reasons"),
					lockerUser.getEmail(),
					1L,
					new GregorianCalendar(),
					userLocked
				);
				userLocked.getLockReasons().add(lockReason);

				saveUserLocked(userLocked, 3L, true);
				sendMailLocked(userLocked, req);
			} else {
				delete(request.getLong(req, "userId"));
			}

			return response.lockResp("Usuario bloqueado exitosamente");
		} else {
			saveUserLocked(userLocked, 1L, false);
			return response.lockResp("Usuario desbloqueado exitosamente");
		}
	}

	@Override
	@Transactional
	public ResponseEntity<Object> delete(Long id) {
		User userDelete = userDao.findById(id).orElseThrow(() ->
			new BadRequestException("Upps usuario no encontrado")
		);

		userDelete.setEnabled(false);
		userDelete.getRoles().clear();
		userDao.saveAndFlush(userDelete);
		sendMailDelete(userDelete);

		return response.deleteResp("Usuario eliminado con exitosamente");
	}


	private void userOrPhoneNumberExist(Long id, Map<String, Object> req) {
		User user = userDao.findByUserName(
			request.getString(req, "userName")
		).orElse(null);
		if (notExistUser(id, user)) {
			throw new BadRequestException("Upps el usuario seleccionado ya existe");
		}
		
		User userPhoneNumber = userDao.findByPhoneNumber(
			request.getString(req, "phoneNumber")
		).orElse(null);
		if (notExistUser(id, userPhoneNumber)) {
			throw new BadRequestException(
				"Upps el número telefónico seleccionado ya existe"
			);
		}
	}

	private boolean notExistUser(Long id, User user) {
		if (user == null) {
			return false;
		}

		return !id.equals(user.getId());
	}

	private void saveUserLocked(User userLocked, Long roleId, boolean status) {
		userLocked.setLocked(status);
		userLocked.getRoles().clear();

		Role role = roleDao.findById(roleId).orElseThrow(() ->
			new BadRequestException("Rol no encontrado")
		);

		userLocked.getRoles().add(role);
		userDao.saveAndFlush(userLocked);
	}

	private void sendMailLocked(User userLocked, Map<String, Object> req) {
		mail.send(
			"No Reply",
			resource.mailTemplate(
				userLocked.getName(),
				"Has sido bloquead@",
				"Desafortunadamente te informamos que " +
				"tu cuenta a sido bloqueada por los administradores " +
				"debido a las siguientes razon(es):",
				request.getString(req, "reasons") +
				"<br><br> <span style=\"font-size: 14px;\">Has sido bloqueado: "
				+ userLocked.getLockReasons().size() + " vese(s)" +
				"<br>Te recordamos que al ser bloqueada mas de 3 veces tu cuenta" +
				 " sera definitivamente dada de baja.</span>",
				"TLOZ BOTW",
				"Zelda Guide",
				IconMail.SUCCESS
			),
			userLocked.getEmail()
		);
	}

	private void sendMailDelete(User userDelete) {
		mail.send(
			"No Reply",
			resource.mailTemplate(
				userDelete.getName(),
				"Lo sentimos",
				"Lamentamos informarte que tu cuenta a " +
					"sido eliminada.",
				"",
				"TLOZ BOTW",
				"Zelda Guide",
				IconMail.BAD
			),
			userDelete.getEmail()
		);
	}

	private void sendMailFirstSignIn(User user) {
		mail.send(
			"No Reply",
			resource.mailTemplate(
				user.getName(),
				"Bienvenid@ a Tlozbotw Guide",
				"Tu cuenta a sido verificada y activada " +
					"satisfactoriamente con el nombre de usuario:",
				user.getUserName(),
				"TLOZ BOTW",
				"Zelda Guide",
				IconMail.SUCCESS
			),
			user.getEmail()
		);
	}

}
