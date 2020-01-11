package org.neurobrain.tlozbotw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import java.util.Base64;
import java.util.UUID;


public class Text {

	private static final Logger logger = LoggerFactory.getLogger(Text.class);

	private Text() {
		throw new IllegalStateException("Problems in: " + Text.class);
	}

	public static String toJSONString(Object obj) {
		try {
			StringBuilder bld = new StringBuilder();
			Method[] methods = obj.getClass().getMethods();
			bld.append("{\n");

			for (int i = 0; i < methods.length; i++) {
				if (
					methods[i].getName().contains("get") &&
					(!methods[i].getName().equals("getClass"))
				) {
					bld.append(
						"  \"" + formatKey(methods[i].getName()) + "\": " +
						"\"" + methods[i].invoke(obj) + "\",\n"
					);
				}
			}
	
			return bld.substring(0, bld.length() - 2) + "\n}";
		} catch (Exception e) {
			logger.error(
				"Problem when converting the class to json string, "
				 + "it is necessary to generate the getter and setter "
				 + "methods in your entity"
			);
		}

		return "";
	}

	public static String uniqueString() {
		String originalInput = UUID.randomUUID().toString().substring(0, 12);
		return Base64.getEncoder().encodeToString(originalInput.getBytes());
	}

	private static Object formatKey(Object stg) {
		if (stg != null) {
			String out = stg.toString()
				.replace("get", "");
			String initialLyrics = String.valueOf(out.charAt(0))
				.toLowerCase();
			return initialLyrics + out.substring(1);
		}
		return "";
	}
}
