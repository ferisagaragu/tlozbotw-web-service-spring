package org.neurobrain.tlozbotw.util;

import java.util.List;
import java.util.Map;

import org.neurobrain.tlozbotw.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class Request {

	private String badMessage;

	public Request() {
		this.badMessage = " field not found";
	}

		public Long getLong(Object req, String key) {
		Object reqOut = ((Map<?, ?>) req).get(key);
		
		if (reqOut != null) {
			return Long.valueOf(reqOut.toString()); 
		}

		throw new BadRequestException(key + badMessage);
	}
	
	
	public String getString(Object req, String key) {
		Object reqOut = ((Map<?, ?>) req).get(key);
		
		if (reqOut != null) {
			return reqOut.toString();
		}

		throw new BadRequestException(key + badMessage);
	}
	
	
	public boolean getBoolean(Object req, String key) {
		Object reqOut = ((Map<?, ?>) req).get(key);
		
		if (reqOut != null) {
				return reqOut.toString().equals("true");
		}

		throw new BadRequestException(key + badMessage);
	}
	
	
	public List<Object> getList(Object req, String key) {
		Object reqOut = ((Map<?, ?>) req).get(key);
		
		if (reqOut != null) {
			return (List<Object>) reqOut;
		}

		throw new BadRequestException(key  + badMessage);
	}
	
}
