package com.thesun4sky.todoparty.todo;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.thesun4sky.todoparty.test.ControllerTest;
import com.thesun4sky.todoparty.test.TodoTest;
import com.thesun4sky.todoparty.test.TodoTestUtils;
import com.thesun4sky.todoparty.user.User;
import com.thesun4sky.todoparty.user.UserDTO;

@WebMvcTest(TodoController.class)
class TodoControllerTest extends ControllerTest implements TodoTest {

	@MockBean
	private TodoService todoService;

	@DisplayName("할일 생성 요청")
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
		verify(todoService, times(1)).createTodo(any(TodoRequestDTO.class), eq(TEST_USER));
	}


	@Nested
	@DisplayName("할일 조회 요청")
	class getTodo {
		@DisplayName("할일 조회 요청 성공")
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

		@DisplayName("할일 조회 요청 실패 - 존재하지 않는 할일ID")
		@Test
		void getTodo_fail_todoIdNotExist() throws Exception {
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

	@DisplayName("할일 목록 조회 요청")
	@Test
	void getTodoList() throws Exception {
		// given
		var testTodo1 = TodoTestUtils.get(TEST_TODO, 1L, LocalDateTime.now(), TEST_USER);
		var testTodo2 = TodoTestUtils.get(TEST_TODO, 2L, LocalDateTime.now().minusMinutes(1), TEST_USER);
		var testAnotherTodo = TodoTestUtils.get(TEST_TODO, 3L, LocalDateTime.now(), TEST_ANOTHER_USER);

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

	@Nested
	@DisplayName("할일 수정 요청")
	class putTodo {
		@DisplayName("할일 수정 요청 성공")
		@Test
		void putTodo_success() throws Exception {
			// given
			given(todoService.updateTodo(eq(TEST_TODO_ID), eq(TEST_TODO_REQUEST_DTO), any(User.class))).willReturn(TEST_TODO_RESPONSE_DTO);

			// when
			var action = mockMvc.perform(put("/api/todos/{todoId}", TEST_TODO_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TEST_TODO_REQUEST_DTO)));

			// then
			action
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(TEST_TODO_TITLE))
				.andExpect(jsonPath("$.content").value(TEST_TODO_CONTENT));
		}

		@DisplayName("할일 수정 요청 실패 - 권한 없음")
		@Test
		void putTodo_fail_rejected() throws Exception {
			// given
			given(todoService.updateTodo(eq(TEST_TODO_ID), eq(TEST_TODO_REQUEST_DTO), any(User.class))).willThrow(new RejectedExecutionException());

			// when
			var action = mockMvc.perform(put("/api/todos/{todoId}", TEST_TODO_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TEST_TODO_REQUEST_DTO)));

			// then
			action
				.andExpect(status().isBadRequest());
		}

		@DisplayName("할일 수정 요청 실패 - 존재하지 않는 할일ID")
		@Test
		void putTodo_fail_illegalArgument() throws Exception {
			// given
			given(todoService.updateTodo(eq(TEST_TODO_ID), eq(TEST_TODO_REQUEST_DTO), any(User.class))).willThrow(new IllegalArgumentException());

			// when
			var action = mockMvc.perform(put("/api/todos/{todoId}", TEST_TODO_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TEST_TODO_REQUEST_DTO)));

			// then
			action
				.andExpect(status().isBadRequest());
		}
	}

	@Nested
	@DisplayName("할일 완료 요청")
	class completeTodo {
		@DisplayName("할일 완료 요청 성공")
		@Test
		void completeTodo_success() throws Exception {
			// given
			TEST_TODO_RESPONSE_DTO.setIsCompleted(true);
			given(todoService.completeTodo(eq(TEST_TODO_ID), any(User.class))).willReturn(TEST_TODO_RESPONSE_DTO);

			// when
			var action = mockMvc.perform(patch("/api/todos/{todoId}/complete", TEST_TODO_ID)
				.accept(MediaType.APPLICATION_JSON));

			// then
			action
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(TEST_TODO_TITLE))
				.andExpect(jsonPath("$.content").value(TEST_TODO_CONTENT))
				.andExpect(jsonPath("$.isCompleted").value(true));
		}

		@DisplayName("할일 완료 요청 실패 - 권한 없음")
		@Test
		void completeTodo_fail_rejected() throws Exception {
			// given
			given(todoService.completeTodo(eq(TEST_TODO_ID), any(User.class))).willThrow(new RejectedExecutionException());

			// when
			var action = mockMvc.perform(patch("/api/todos/{todoId}/complete", TEST_TODO_ID)
				.accept(MediaType.APPLICATION_JSON));

			// then
			action
				.andExpect(status().isBadRequest());
		}

		@DisplayName("할일 완료 요청 실패 - 존재하지 않는 할일ID")
		@Test
		void completeTodo_fail_illegalArgument() throws Exception {
			// given
			given(todoService.completeTodo(eq(TEST_TODO_ID), any(User.class))).willThrow(new IllegalArgumentException());

			// when
			var action = mockMvc.perform(patch("/api/todos/{todoId}/complete", TEST_TODO_ID)
				.accept(MediaType.APPLICATION_JSON));

			// then
			action
				.andExpect(status().isBadRequest());
		}
	}


}