package org.neurobrain.tlozbotw.util;

import org.neurobrain.tlozbotw.exception.BadRequestException;

import java.util.LinkedHashMap;
import java.util.List;


public class Request<K, V> extends LinkedHashMap<String, Object> {

	private String badMessage;


	public Request() {
		this.badMessage = " field not found";
	}


	public Long getLong(String key) {
		Object reqOut = this.get(key);

		if (reqOut != null) {
			return Long.valueOf(reqOut.toString());
		}

		throw new BadRequestException(key + badMessage);
	}

	public String getString(String key) {
		Object reqOut = this.get(key);

		if (reqOut != null) {
			return reqOut.toString();
		}

		throw new BadRequestException(key + badMessage);
	}

	public boolean getBoolean(String key) {
		Object reqOut = this.get(key);

		if (reqOut != null) {
				return reqOut.toString().equals("true");
		}

		throw new BadRequestException(key + badMessage);
	}

	public List<Object> getList(String key) {
		Object reqOut = this.get(key);

		if (reqOut != null) {
			return (List<Object>) reqOut;
		}

		throw new BadRequestException(key  + badMessage);
	}

}
