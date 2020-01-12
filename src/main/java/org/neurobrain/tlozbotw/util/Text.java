package org.neurobrain.tlozbotw.util;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;


@Component
public class Text {

	public String uniqueString() {
		String originalInput = UUID.randomUUID().toString().substring(0, 12);
		return Base64.getEncoder().encodeToString(originalInput.getBytes());
	}

}
