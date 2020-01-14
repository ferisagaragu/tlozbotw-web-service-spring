package org.neurobrain.tlozbotw.response;

import org.neurobrain.tlozbotw.entity.Bow;
import org.neurobrain.tlozbotw.entity.User;
import org.neurobrain.tlozbotw.entity.UserBow;
import org.neurobrain.tlozbotw.util.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class BowResp {

	private final Response response;

	public BowResp(Response response) {
		this.response = response;
	}

	public ResponseEntity<Object> getAllBowRespAdmin(List<Bow> bows) {
		return response.ok(response.toListMap(bows));
	}

	public ResponseEntity<Object> getAllBowResp(User user, List<Bow> bows) {
		List<Object> bowsOut = new ArrayList<>();

		for (UserBow userBow : user.getUserBows()) {
			if (userBow.getBow().getAvailable()) {
				bowsOut.add(
					createBow(
						userBow.getBow(),
						userBow
					)
				);
			}
		}

		for (Bow bow : bows) {
			bowsOut.add(createBow(bow, null));
		}

		return response.ok(bowsOut);
	}

	public ResponseEntity<Object> createBowResp(String message, Bow bow) {
		return response.created(
			message,
			response.toMap(bow)
		);
	}

	public ResponseEntity<Object> updateBowResp(String message, Bow bow) {
		return response.ok(
			message,
			response.toMap(bow, "available")
		);
	}

	public ResponseEntity<Object> deleteBowResp(String message, Bow bow) {
		return response.ok(
			message,
			response.toMap(bow)
		);
	}


	private Map<String, Object> createBow(Bow bow, UserBow userBow) {
		Map bowOut = response.toMap(bow, "available");
		bowOut.put("see", userBow != null ? userBow.getSee() : false);
		return bowOut;
	}
}
