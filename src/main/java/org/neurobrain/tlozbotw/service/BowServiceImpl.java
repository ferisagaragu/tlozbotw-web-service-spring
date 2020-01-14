package org.neurobrain.tlozbotw.service;

import org.neurobrain.tlozbotw.dao.IBowDAO;
import org.neurobrain.tlozbotw.dao.IUserDAO;
import org.neurobrain.tlozbotw.entity.Bow;
import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.exception.BadRequestException;
import org.neurobrain.tlozbotw.response.BowResp;
import org.neurobrain.tlozbotw.service.interfaces.IBowService;

import org.neurobrain.tlozbotw.util.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class BowServiceImpl implements IBowService {

	private final IUserDAO userDAO;
	private final IBowDAO bowDAO;
	private final BowResp bowResp;
	private final Request request;


	public BowServiceImpl(
		IUserDAO userDAO,
		IBowDAO bowDAO,
		BowResp bowResp,
		Request request
	) {
		this.userDAO = userDAO;
		this.bowDAO = bowDAO;
		this.bowResp = bowResp;
		this.request = request;
	}


	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Object> getAllBow(Long id) {
		User user = userDAO.findById(id).orElseThrow(() ->
			new BadRequestException("Usuario no encontrado " + id)
		);

		if (user.containsRole("ADMIN")) {
			return bowResp.getAllBowRespAdmin(
				bowDAO.findAll()
			);
		} else if (user.containsRole("USER")) {
			List<Bow> bows = bowDAO.findAllNotIn(id);
			return bowResp.getAllBowResp(user, bows);
		}

		return null;
	}

	@Override
	@Transactional
	public ResponseEntity<Object> createBow(Map<String, Object> req) {
		Bow bow = new Bow();
		bow.setName(request.getString(req, "name"));
		bow.setDamage(request.getLong(req, "damage"));
		bow.setNumberArrows(request.getLong(req, "numberArrows"));
		bow.setDescription(request.getString(req, "description"));
		bow.setImg(request.getString(req, "img"));
		bow.setAvailable(true);
		return bowResp.createBowResp(
			"El arco '" + bow.getName() + "' a sido creado",
			bowDAO.saveAndFlush(bow)
		);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> updateBow(Long id, Map<String, Object> req) {
		Bow bow = bowDAO.findById(id).orElseThrow(() ->
			new BadRequestException("Upps el usuario no existe")
		);

		bow.setName(request.getString(req, "name"));
		bow.setDamage(request.getLong(req, "damage"));
		bow.setDescription(request.getString(req, "description"));
		bow.setImg(request.getString(req, "img"));
		bow.setNumberArrows(request.getLong(req, "numberArrows"));

		return bowResp.updateBowResp(
			"Arco actualizado exitosamente",
			bowDAO.saveAndFlush(bow)
		);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> deleteBow(Long id) {
		Bow bow = bowDAO.findById(id).orElseThrow(() ->
			new BadRequestException("Upps el usuario no existe")
		);
		bow.setAvailable(false);

		return bowResp.deleteBowResp(
			"El arco '" + bow.getName() + "' fue eliminado exitosamente",
			bowDAO.saveAndFlush(bow)
		);
	}

}
