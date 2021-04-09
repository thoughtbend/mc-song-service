package com.thoughtbend.mc.songsvc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.thoughtbend.mc.songsvc.repository.ArtistCollectionRepository;

@WebMvcTest(controllers = ArtistController.class)
public class ArtistControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean(name = "artistRepository")
	private ArtistCollectionRepository artistRepository;
	
	@Test
	public void test_getArtists_success() throws Exception {
		final String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlVWckw5SEVadXU3MHc1dUx6RDBKUiJ9.eyJpc3MiOiJodHRwczovL3Rob3VnaHRiZW5kLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJINXQ0elBuT2t6bk01enoyWlNRWEpSWmNVMlZXQnlQdEBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9tYy1hcnRpc3Qtc2VydmljZS50aG91Z2h0YmVuZC5jb20vIiwiaWF0IjoxNjE3ODg3NTA5LCJleHAiOjE2MTc5NzM5MDksImF6cCI6Ikg1dDR6UG5Pa3puTTV6ejJaU1FYSlJaY1UyVldCeVB0Iiwic2NvcGUiOiJyZWFkOmFydGlzdHMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMiLCJwZXJtaXNzaW9ucyI6WyJyZWFkOmFydGlzdHMiXX0.KhTKy4iYGS00ZMpabVbQEJchX_Raaz2gYPVULMRR9kxnP4UpW8_BkMNmGkGNCKIE6K54qIEO7LOLIvnHo-iHaCY0H3EZBxF3QF4jhLEFVwL3qpM_GBWmZzZAGlD8HTw-24-xubu4i2aZgQMehRGXXXZABw15xSjNN9jcModfSz9LEkyzkrUU_knuEvgtczjz0h7LvpgQj_9w01gBNdI9dF8SJ3swjXFTLjU7-IFBFFO4P_DqzqIhRkSWdtrNkE_Fr6Q5O09PGiuJFYTpfv0yavp_t078yspXbLMlWvoUwKVDzLXmpNqfItRtLkkfwY3q2o5LOz-7nJ9Xir-2ZvAlPw";
		mvc.perform(get("/artist").header("Authorization", "Bearer " + token))
			.andExpect(status().isOk());
		
	}
}
