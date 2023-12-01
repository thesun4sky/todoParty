package com.thesun4sky.todoparty;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesun4sky.todoparty.todo.Todo;
import com.thesun4sky.todoparty.todo.TodoRequestDTO;
import com.thesun4sky.todoparty.todo.TodoResponseDTO;
import com.thesun4sky.todoparty.user.User;
import com.thesun4sky.todoparty.user.UserDetailsImpl;

public class ControllerTest {
	protected static final Long TEST_TODO_ID = 1L;
	protected static final String TEST_TODO_TITLE = "title";
	protected static final String TEST_TODO_CONTENT = "content";
	protected static final TodoRequestDTO TEST_TODO_REQUEST_DTO = TodoRequestDTO.builder()
		.title(TEST_TODO_TITLE)
		.content(TEST_TODO_CONTENT)
		.build();
	protected static final TodoResponseDTO TEST_TODO_RESPONSE_DTO = TodoResponseDTO.builder()
		.title(TEST_TODO_TITLE)
		.content(TEST_TODO_CONTENT)
		.build();
	protected static final Todo TEST_TODO = Todo.builder()
		.id(TEST_TODO_ID)
		.title(TEST_TODO_TITLE)
		.content(TEST_TODO_CONTENT)
		.build();
	@Autowired
	private WebApplicationContext context;

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	protected User testUser = User.builder()
		.username("testUser")
		.password("testPassword")
		.build();

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.build();

		// Mock 테스트 UserDetails 생성
		UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);

		// SecurityContext 에 인증된 사용자 설정
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
			testUserDetails, testUserDetails.getPassword(), testUserDetails.getAuthorities()));
	}
}
