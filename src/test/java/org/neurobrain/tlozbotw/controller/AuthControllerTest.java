package org.neurobrain.tlozbotw.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neurobrain.tlozbotw.util.Text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

	@Autowired
  private MockMvc mockMvc;
  private Text text = new Text();
	private Gson gson = new Gson();

	private String mockStg = text.uniqueString();

	@Test
	public void sign_in_ok() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("userName", "ferisagaragu@gmail.com");
		body.put("password", "OTEyN2MyYzgtNjZk");

		this.mockMvc.perform(
			post("/auth/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void sign_in_unauthorized() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("userName", "ferisagaragu@gmail.com");
		body.put("password", "fernny");

		this.mockMvc.perform(
			post("/auth/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isUnauthorized())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void sign_in_bad_request() throws Exception{
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("userNam", "ferisagaragu@gmail.com");
		body.put("password", "fernny");

		this.mockMvc.perform(
			post("/auth/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}


	@Test
	public void sign_up_ok() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("name", mockStg);
		body.put("lastName", mockStg);
		body.put("phoneNumber", mockStg);
		body.put("imageUrl", "");
		body.put("userName", mockStg);
		body.put("email", mockStg + "@gmail.com");

		this.mockMvc.perform(
			post("/auth/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void signup_bad_request() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("name", mockStg);
		body.put("lastName", mockStg);
		body.put("phoneNumber", mockStg);
		body.put("imageUrl", "");
		body.put("userName", mockStg);
		body.put("email", "ferisagaragu@gmail.com");

		this.mockMvc.perform(
			post("/auth/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void signup_bad_request2() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("name", mockStg);
		body.put("lastName", mockStg);
		body.put("phoneNumber", mockStg);
		body.put("imageUrl", "");
		body.put("userNam", mockStg);
		body.put("email", "ferisagaragu@gmail.com");

		this.mockMvc.perform(
			post("/auth/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}


	@Test
	public void recoverPassword_ok() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("email", "ferisagaragu@gmail.com");

		this.mockMvc.perform(
			post("/auth/recover-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void recoverPassword_bad_request() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("email", "ferisagaragu@gmail.co");

		this.mockMvc.perform(
			post("/auth/recover-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void recoverPassword_bad_request2() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("emai", "ferisagaragu@gmail.com");

		this.mockMvc.perform(
			post("/auth/recover-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}


	@Test
	public void changePassword_ok() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("code", "Y2E2NDU1ODAtOTEz");
		body.put("password", "fernny27");

		this.mockMvc.perform(
			post("/auth/change-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void changePassword_bad_request() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("code", "ZTQxYjdjZjktYjA1");
		body.put("password", "fernny27");

		this.mockMvc.perform(
			post("/auth/change-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void changePassword_bad_request2() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("code", "ZTQxYjdjZjktYjA1");
		body.put("passwor", "fernny27");

		this.mockMvc.perform(
			post("/auth/change-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

}
