package org.neurobrain.tlozbotw.response;

import org.neurobrain.tlozbotw.entity.Bow;
import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.entity.UserBow;
import org.neurobrain.tlozbotw.util.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Component
public class BowResp {

	private final Response response;

	public BowResp(Response response) {
		this.response = response;
	}

	public ResponseEntity<Object> getAllBowRespAdmin(List<Bow> bows) {
		Map<String, Object> resp = new LinkedHashMap<>();
		List<Object> bowsOut = new ArrayList<>();

		bows.forEach((Bow bow) ->
			bowsOut.add(
				createBow(
					bow,
					null,
					true
				)
			)
		);

		resp.put("bows", bowsOut);
		return response.ok(resp);
	}

	public ResponseEntity<Object> getAllBowResp(User user, List<Bow> bows) {
		Map<String, Object> resp = new LinkedHashMap<>();
		List<Object> bowsOut = new ArrayList<>();

			for (UserBow userBow : user.getUserBows()) {
				if (userBow.getBow().getAvailable()) {
					bowsOut.add(
						createBow(
							userBow.getBow(),
							userBow,
							false
						)
					);
				}
			}

			for (Bow bow : bows) {
				bowsOut.add(createBow(bow, null, false));
			}

		resp.put("bows", bowsOut);
		return response.ok(resp);
	}

	public ResponseEntity<Object> createBowResp(Bow bow) {
		return response.created(
			"El arco '" + bow.getName() + "' a sido creado",
			createBow(bow, null, false)
		);
	}

	private Map<String, Object> createBow(Bow bow, UserBow userBow, boolean isAdmin) {
		Map<String, Object> bowOut = new LinkedHashMap<>();
		bowOut.put("id", bow.getId());
		bowOut.put("name", bow.getName());
		bowOut.put("damage", bow.getDamage());
		bowOut.put("numberArrows", bow.getNumberArrows());
		bowOut.put("description", bow.getDescription());
		bowOut.put("img", bow.getImg());

		if (isAdmin) {
			bowOut.put("available", bow.getAvailable());
		} else {
			bowOut.put("see", userBow != null ? userBow.getSee() : false);
		}

		return bowOut;
	}
}
