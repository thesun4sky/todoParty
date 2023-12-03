package com.thesun4sky.todoparty.test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesun4sky.todoparty.user.User;
import com.thesun4sky.todoparty.user.UserDetailsImpl;

public class ControllerTest implements CommonTest {
	@Autowired
	private WebApplicationContext context;

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.build();

		// Mock 테스트 UserDetails 생성
		UserDetailsImpl testUserDetails = new UserDetailsImpl(TEST_USER);

		// SecurityContext 에 인증된 사용자 설정
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
			testUserDetails, testUserDetails.getPassword(), testUserDetails.getAuthorities()));
	}
}
