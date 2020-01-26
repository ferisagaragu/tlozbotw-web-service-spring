package org.neurobrain.tlozbotw.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class Response {

	public ResponseEntity<Object> ok(String message, Object data) {
		return response(message, data, HttpStatus.OK);
	}

	public ResponseEntity<Object> ok(String message) {
		return response(message, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> ok(Object data) {
		return response(null, data, HttpStatus.OK);
	}

	public ResponseEntity<Object> created(String message, Object data) {
		return response(message, data, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> created(String message) {
		return response(message, null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> created(Object data) {
		return response(null, data, HttpStatus.CREATED);
	}

	public Map toMap(Object obj) {
		Map<String, Object> out = new LinkedHashMap<>();

		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (isValidType(field)) {
					out.put(field.getName(), field.get(obj));
				}
			}

			return out;
		} catch (Exception e) {
			response(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return null;
	}

	public Map toMap(Object obj, String... excludes) {
		Map<String, Object> out = new LinkedHashMap<>();

		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (isValidType(field) && isNotExclude(field.getName(), excludes)) {
					out.put(field.getName(), field.get(obj));
				}
			}

			return out;
		} catch (Exception e) {
			response(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return null;
	}

	public Map toMap(Object obj, String[] callMethods, String... excludes) {
		Map<String, Object> out = new LinkedHashMap<>();

		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (isValidType(field) && isNotExclude(field.getName(), excludes)) {
					out.put(field.getName(), field.get(obj));
				}
			}

			for (String call : callMethods) {
				out.put(asCall(call, 1), obj.getClass().getMethod(asCall(call, 0)).invoke(obj));
			}

			return out;
		} catch (Exception e) {
			response(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return null;
	}

	public List toListMap(List list) {
		List out = new ArrayList();
		for (Object object : list) {
			out.add(toMap(object));
		}
		return out;
	}

	public List toListMap(List list, String... excludes) {
		List out = new ArrayList();
		for (Object object : list) {
			out.add(toMap(object, excludes));
		}
		return out;
	}

	public List toListMap(List list, String[] callMethods, String... excludes) {
		List out = new ArrayList();
		for (Object object : list) {
			out.add(toMap(object, callMethods, excludes));
		}
		return out;
	}


	private ResponseEntity<Object> response(String message, Object data, HttpStatus status) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("timestamp", new Date());
		response.put("status", status.value());

		if (message != null) {
			response.put("message", message);
		}

		if (data != null) {
			response.put("data", data);
		}

		return new ResponseEntity<> (
			response,
			status
		);
	}

	private boolean isValidType(Field field) {

		if (field.getName().equals("serialVersionUID")) {
			return false;
		} else if (field.getType().equals(String.class)) {
			return true;
		} else if (field.getType().equals(char.class) || field.getType().equals(Character.class)) {
			return true;
		} else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
			return true;
		} else if (field.getType().equals(byte.class) || field.getType().equals(Byte.class)) {
			return true;
		} else if (field.getType().equals(short.class) || field.getType().equals(Short.class)) {
			return true;
		} else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
			return true;
		} else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
			return true;
		} else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
			return true;
		} else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
			return true;
		} else if (field.getType().equals(BigInteger.class)) {
			return true;
		} else if (field.getType().equals(BigDecimal.class)) {
			return true;
		} else if (field.getType().equals(Timestamp.class)) {
			return true;
		} else if (field.getType().equals(Time.class)) {
			return true;
		} else if (field.getType().equals(java.sql.Date.class)) {
			return true;
		} else if (field.getType().equals(Calendar.class)) {
			return true;
		} else if (field.getType().equals(GregorianCalendar.class)) {
			return true;
		} else if (field.getType().equals(Currency.class)) {
			return true;
		} else if (field.getType().equals(Locale.class)) {
			return true;
		} else if (field.getType().equals(TimeZone.class)) {
			return true;
		} else if (field.getType().equals(URL.class)) {
			return true;
		} else if (field.getType().equals(Class.class)) {
			return true;
		} else if (field.getType().equals(Blob.class)) {
			return true;
		} else if (field.getType().equals(Clob.class)) {
			return true;
		} else if (field.getType().equals(byte[].class) || field.getType().equals(Byte[].class)) {
			return true;
		} else if (field.getType().equals(char[].class) || field.getType().equals(Character[].class)) {
			return true;
		} else if (field.getType().equals(UUID.class)) {
			return true;
		} else if (field.getType().equals(List.class)) {
			return false;
		} else {
			return false;
		}
	}

	private boolean isNotExclude(String match, String... excludes) {
		for (String exclude : excludes) {
			if (exclude.equals(match)) {
				return false;
			}
		}
		return true;
	}

	private String asCall(String call, int part) {
		String[] callParts = call.split(" as ");
		if (callParts.length == 2) {
			if (part == 0) {
				return callParts[0];
			} else {
				return callParts[1];
			}
		}
		return callParts[0];
	}

}
