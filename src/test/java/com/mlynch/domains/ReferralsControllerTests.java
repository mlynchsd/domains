package com.mlynch.domains;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ReferralsController.class)
public class ReferralsControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ReferralsService referralsService;

	@Test
	public void postValidUrlShouldReturnDomain() throws Exception {
		given(this.referralsService.record("127.0.0.1", "http://www.google.com"))
			.willReturn(ReferredDomain.referral("127.0.0.1", new URL("http://www.google.com")));

		this.mvc.perform(post("/referrals").contentType(MediaType.APPLICATION_JSON_VALUE)
			.param("url", "http://www.google.com"))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.clientId", not(isEmptyOrNullString())))
			.andExpect(jsonPath("$.domainName", is("google.com")));
			// TODO: .andExpect(jsonPath("$.timestamp", is(lessThanOrEqualTo(Instant.now().getEpochSecond()))));
	}

}
