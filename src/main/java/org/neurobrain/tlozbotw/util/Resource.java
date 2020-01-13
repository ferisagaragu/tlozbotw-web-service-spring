package org.neurobrain.tlozbotw.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import org.neurobrain.tlozbotw.enums.IconMail;

@Component
public class Resource {

	private static final Logger logger = LoggerFactory.getLogger(Resource.class);

	@Value("app.icon.password")
	private String passwordIcon;

	@Value("app.icon.success")
	private String successIcon;

	@Value("app.icon.warning")
	private String warningIcon;

	@Value("app.icon.bad")
	private String badIcon;


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

	public String mailTemplate(
		String title,
		String firstPart,
		String secondPart,
		String important,
		String createdBy,
		String description,
		IconMail iconMail
	) {
		try {
			ClassPathResource resource = new ClassPathResource("template-mail.html");
			InputStream inputStream = resource.getInputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
            
			data = data.replace("${user}", title)
				.replace("${firstPart}", firstPart)
				.replace("${secondPart}", secondPart)
				.replace("${important}", important)
				.replace("${createdBy}", createdBy)
				.replace("${icon}", getIconMail(iconMail))
				.replace("${description}", description);
            
			return data;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		return "";
	}


	private String getIconMail(IconMail iconMail) {
		switch (iconMail) {
			case PASSWORD: return passwordIcon;
			case SUCCESS: return successIcon;
			case WARNING: return warningIcon;
			case BAD: return badIcon;
			default: return "";
		}
	}

}
