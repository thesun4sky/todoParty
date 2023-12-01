package com.thesun4sky.todoparty.todo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesun4sky.todoparty.ControllerTest;

@ActiveProfiles("test")
@WebMvcTest(TodoController.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class TodoControllerTest extends ControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TodoService todoService;

	@DisplayName("할일 생성 - 성공")
	@Test
	void postTodo() throws Exception {
		// given
		TodoRequestDTO todoRequestDto = TodoRequestDTO.builder()
			.title("title")
			.content("content")
			.build();

		// when
		var result = mockMvc.perform(post("/api/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoRequestDto)));

		// then
		result.andExpect(status().isCreated());
		verify(todoService, times(1)).createTodo(any(TodoRequestDTO.class), eq(testUser));
	}

}