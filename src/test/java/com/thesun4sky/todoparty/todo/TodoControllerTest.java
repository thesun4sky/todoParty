package com.thesun4sky.todoparty.todo;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.thesun4sky.todoparty.ControllerTest;
import com.thesun4sky.todoparty.TodoTest;
import com.thesun4sky.todoparty.TodoTestUtils;
import com.thesun4sky.todoparty.user.UserDTO;

@ActiveProfiles("test")
@WebMvcTest(TodoController.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class TodoControllerTest extends ControllerTest implements TodoTest {

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


	@Nested
	@DisplayName("할일 조회")
	class getTodo {
		@DisplayName("할일 조회 성공")
		@Test
		void getTodo_success() throws Exception {
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

		@DisplayName("할일 조회 실패(존재하지 않는 할일ID)")
		@Test
		void getTodo_fail_todoNotExist() throws Exception {
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

	@DisplayName("할일 목록 조회")
	@Test
	void getTodoList() throws Exception {
		// given
		var testTodo1 = TodoTestUtils.getTestTodo(TEST_TODO, 1L, LocalDateTime.now(), TEST_USER);
		var testTodo2 = TodoTestUtils.getTestTodo(TEST_TODO, 2L, LocalDateTime.now().minusMinutes(1), TEST_USER);
		var testAnotherTodo = TodoTestUtils.getTestTodo(TEST_TODO, 1L, LocalDateTime.now(), TEST_ANOTHER_USER);

		given(todoService.getUserTodoMap()).willReturn(
			Map.of(new UserDTO(TEST_USER), List.of(new TodoResponseDTO(testTodo1), new TodoResponseDTO(testTodo2)),
				new UserDTO(TEST_ANOTHER_USER), List.of(new TodoResponseDTO(testAnotherTodo))));

		// when
		var action = mockMvc.perform(get("/api/todos")
			.accept(MediaType.APPLICATION_JSON));

		// then
		action
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[?(@.user.username=='" + TEST_USER.getUsername() + "')].todoList[*].id")
					.value(Matchers.containsInAnyOrder(testTodo1.getId().intValue(), testTodo2.getId().intValue())))
				.andExpect(jsonPath("$[?(@.user.username=='" + TEST_ANOTHER_USER.getUsername() + "')].todoList[*].id")
					.value(Matchers.containsInAnyOrder(testAnotherTodo.getId().intValue())));
		verify(todoService, times(1)).getUserTodoMap();
	}


}