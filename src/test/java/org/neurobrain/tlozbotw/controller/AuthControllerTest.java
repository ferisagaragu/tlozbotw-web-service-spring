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

import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

	@Autowired
  private MockMvc mockMvc;

  @Autowired
  private Text text;

	private String mockStg = text.uniqueString();
	private Gson gson = new Gson();

	@Test
	public void signin_ok() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("userName", "ferisagaragu@gmail.com");
		body.put("password", "fernny27");

		this.mockMvc.perform(
			post("/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void signin_unauthorized() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("userName", "ferisagaragu@gmail.com");
		body.put("password", "fernny");

		this.mockMvc.perform(
			post("/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isUnauthorized())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void signin_bad_request() throws Exception{
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("userNam", "ferisagaragu@gmail.com");
		body.put("password", "fernny");

		this.mockMvc.perform(
			post("/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}


	@Test
	public void signup_ok() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("name", mockStg);
		body.put("lastName", mockStg);
		body.put("phoneNumber", mockStg);
		body.put("imageUrl", "");
		body.put("userName", mockStg);
		body.put("email", mockStg + "@gmail.com");

		this.mockMvc.perform(
			post("/auth/signup")
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
			post("/auth/signup")
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
			post("/auth/signup")
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
			post("/auth/recoverPassword")
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
			post("/auth/recoverPassword")
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
			post("/auth/recoverPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}


	@Test
	public void changePassword_ok() throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("code", "ZTQxYjdjZjktYjA1");
		body.put("password", "fernny27");

		this.mockMvc.perform(
			post("/auth/changePassword")
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
			post("/auth/changePassword")
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
			post("/auth/changePassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(body))
		).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

}
