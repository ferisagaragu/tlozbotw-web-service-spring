package org.neurobrain.tlozbotw.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
  private MockMvc mockMvc;
	private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmZXJubnlwYXk5NSIsImlhdCI6MTU3ODMyOTk4NSwiZXhwIjoxNTc4MzQ3OTg1fQ.iDVA0H3tE3Iuchdbx47wt4_WRBZPxaEs89tJjsxsQSsgM-Q4nuL9O8MrABkMvYEb4eLzFlPe9ZZyO1P6s9aVpA";
	private Gson gson = new Gson();

	@Test
	public void firstSignin_ok() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("userName", "ferisagaragu@gmail.com");
		body.put("password", "fernny27");

		this.mockMvc.perform(
			post("/user/firstSignin/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
				.header("Authorization", "Bearer " + token)
		).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void firstSignin_bad_request() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("userName", "ferisagaragu@gmail.com");
		body.put("password", "fernny27");

		this.mockMvc.perform(
			post("/user/firstSignin/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
				.header("Authorization", "Bearer " + token)
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void firstSignin_bad_request2() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("userName", "ferisagaragu@gmail.com");
		body.put("passwor", "fernny27");

		this.mockMvc.perform(
			post("/user/firstSignin/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
				.header("Authorization", "Bearer " + token)
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}


	@Test
	public void update() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("name", "Fernando");
		body.put("lastName", "Aguirre");
		body.put("phoneNumber", "+52 (33) 23-81-47-52");
		body.put("imageUrl", "no linked");
		body.put("userName", "fernnypay95");

		this.mockMvc.perform(
			put("/user/update/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
				.header("Authorization", "Bearer " + token)
		).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void update_bad_request() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("name", "Fernando");
		body.put("lastName", "Aguirre");
		body.put("phoneNumber", "+52 (33) 23-81-47-52");
		body.put("imageUrl", "no linked");
		body.put("userName", "fernnypay95");

		this.mockMvc.perform(
			put("/user/update/10000000")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
				.header("Authorization", "Bearer " + token)
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void update_bad_request2() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("name", "Fernando");
		body.put("lastName", "Aguirre");
		body.put("phoneNumber", "+52 (33) 23-81-47-52");
		body.put("imageUrl", "no linked");
		body.put("userNam", "fernnypay95");

		this.mockMvc.perform(
			put("/user/update/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
				.header("Authorization", "Bearer " + token)
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
}
