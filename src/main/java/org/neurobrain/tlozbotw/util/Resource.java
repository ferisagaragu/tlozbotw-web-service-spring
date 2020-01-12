package org.neurobrain.tlozbotw.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class Resource {

	private static final Logger logger = LoggerFactory.getLogger(Resource.class);

	public String load(String res) {
		try {
			ClassPathResource resource = new ClassPathResource(res);
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			return new String(bdata, StandardCharsets.UTF_8);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return "";
	}

	public String passwordTemplate(
		String user,
		String firstPart,
		String secondPart,
		String password,
		String createdBy,
		String description
	) {
		try {
			ClassPathResource resource = new ClassPathResource("password-template.html");
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
            
			data = data.replace("${user}", user)
				.replace("${firstPart}", firstPart)
				.replace("${secondPart}", secondPart)
				.replace("${password}", password)
				.replace("${createdBy}", createdBy)
				.replace("${description}", description);
            
			return data;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		return "";
	}

}
