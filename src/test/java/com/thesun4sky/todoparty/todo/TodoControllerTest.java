package com.thesun4sky.todoparty.todo;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.thesun4sky.todoparty.ControllerTest;

@ActiveProfiles("test")
@WebMvcTest(TodoController.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class TodoControllerTest extends ControllerTest {

	@MockBean
	private TodoService todoService;

	@DisplayName("할일 생성 - 성공")
	@Test
	void postTodo() throws Exception {
		// given

		// when
		var action = mockMvc.perform(post("/api/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TEST_TODO_REQUEST_DTO)));

		// then
		action.andExpect(status().isCreated());
		verify(todoService, times(1)).createTodo(any(TodoRequestDTO.class), eq(testUser));
	}

	@DisplayName("할일 조회 - 성공")
	@Test
	void getTodo() throws Exception {
		// given
		given(todoService.getTodoDto(eq(TEST_TODO_ID))).willReturn(TEST_TODO_RESPONSE_DTO);

		// when
		var action = mockMvc.perform(get("/api/todos/{todoId}", TEST_TODO_ID)
			.accept(MediaType.APPLICATION_JSON));

		// then
		action
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(TEST_TODO_TITLE))
				.andExpect(jsonPath("$.content").value(TEST_TODO_CONTENT));
	}

	@DisplayName("할일 조회 - 실패")
	@Test
	void getTodo_fail() throws Exception {
		// given
		given(todoService.getTodoDto(eq(TEST_TODO_ID))).willThrow(new IllegalArgumentException());

		// when
		var action = mockMvc.perform(get("/api/todos/{todoId}", TEST_TODO_ID)
			.accept(MediaType.APPLICATION_JSON));

		// then
		action
			.andExpect(status().isBadRequest());
	}


}