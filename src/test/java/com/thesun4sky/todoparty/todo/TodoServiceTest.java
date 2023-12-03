package com.thesun4sky.todoparty.todo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import com.thesun4sky.todoparty.test.TodoTest;
import com.thesun4sky.todoparty.test.TodoTestUtils;
import com.thesun4sky.todoparty.user.User;
import com.thesun4sky.todoparty.user.UserDTO;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest implements TodoTest {

	@InjectMocks
	TodoService todoService;

	@Mock
	TodoRepository todoRepository;

	@DisplayName("할일 생성")
	@Test
	void createTodo() {
		// given
		var testTodo = TodoTestUtils.get(TEST_TODO, TEST_USER);
		given(todoRepository.save(any(Todo.class))).willReturn(testTodo);

		// when
		var result = todoService.createTodo(TEST_TODO_REQUEST_DTO, TEST_USER);

		// then
		var expect = new TodoResponseDTO(testTodo);
		assertThat(result).isEqualTo(expect);
	}

	@DisplayName("할일 DTO 조회")
	@Test
	void getTodoDto() {
		// given
		var testTodo = TodoTestUtils.get(TEST_TODO, TEST_USER);
		given(todoRepository.findById(eq(TEST_TODO_ID))).willReturn(Optional.of(testTodo));

		// when
		var result = todoService.getTodoDto(TEST_TODO_ID);

		// then
		assertThat(result).isEqualTo(new TodoResponseDTO(testTodo));
	}

	@DisplayName("할일 리스트 맵 (최신순 정렬)")
	@Test
	void getUserTodoMap() {
		// given
		var testTodo1 = TodoTestUtils.get(TEST_TODO, 1L, LocalDateTime.now(), TEST_USER);
		var testTodo2 = TodoTestUtils.get(TEST_TODO, 2L, LocalDateTime.now().minusMinutes(1), TEST_USER);
		var testAnotherTodo = TodoTestUtils.get(TEST_TODO, 1L, LocalDateTime.now(), TEST_ANOTHER_USER);

		given(todoRepository.findAll(any(Sort.class))).willReturn(List.of(testTodo1, testTodo2, testAnotherTodo));

		// when
		var result = todoService.getUserTodoMap();

		// then
		assertThat(result.get(new UserDTO(TEST_USER)).get(0)).isEqualTo(new TodoResponseDTO(testTodo1));
		assertThat(result.get(new UserDTO(TEST_USER)).get(1)).isEqualTo(new TodoResponseDTO(testTodo2));
		assertThat(result.get(new UserDTO(TEST_ANOTHER_USER)).get(0)).isEqualTo(new TodoResponseDTO(testAnotherTodo));
	}

	@DisplayName("할일 수정")
	@Test
	void updateTodo() {
		// given
		ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
		var testTodo = TodoTestUtils.get(TEST_TODO, TEST_USER);
		given(todoRepository.findById(eq(TEST_TODO_ID))).willReturn(Optional.of(testTodo));

		// when
		var request = new TodoRequestDTO("updateTitle", "updateContent");
		var result = todoService.updateTodo(TEST_TODO_ID, request, TEST_USER);

		// then
		assertThat(result).isEqualTo(new TodoResponseDTO(testTodo));
	}

	@DisplayName("할일 완료 처리")
	@Test
	void competeTodo() {
		// given
		ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
		var testTodo = TodoTestUtils.get(TEST_TODO, TEST_USER);
		given(todoRepository.findById(eq(TEST_TODO_ID))).willReturn(Optional.of(testTodo));

		// when
		var result = todoService.completeTodo(TEST_TODO_ID, TEST_USER);

		// then
		assertThat(result.getIsCompleted()).isEqualTo(true);
	}

	@DisplayName("할일 조회")
	@Test
	void getTodo() {
		// given
		var testTodo = TodoTestUtils.get(TEST_TODO, TEST_USER);
		given(todoRepository.findById(eq(TEST_TODO_ID))).willReturn(Optional.of(testTodo));

		// when
		var result = todoService.getTodo(TEST_TODO_ID);

		// then
		assertThat(result).isEqualTo(testTodo);
	}

	@DisplayName("유저의 할일 조회")
	@Nested
	class getUserTodo {
		@DisplayName("유저의 할일 조회 성공")
		@Test
		void getUserTodo_success() {
			// given
			ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
			var testTodo = TodoTestUtils.get(TEST_TODO, TEST_USER);
			given(todoRepository.findById(eq(TEST_TODO_ID))).willReturn(Optional.of(testTodo));

			// when
			var result = todoService.getUserTodo(TEST_TODO_ID, TEST_USER);

			// then
			assertThat(result).isEqualTo(testTodo);
		}

		@DisplayName("유저의 할일 조회 실패 - 조회 권한 없음")
		@Test
		void getUserTodo_fail() {
			// given
			ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
			var testTodo = TodoTestUtils.get(TEST_TODO, TEST_USER);
			given(todoRepository.findById(eq(TEST_TODO_ID))).willReturn(Optional.of(testTodo));
			ReflectionTestUtils.setField(TEST_ANOTHER_USER, User.class, "id", TEST_ANOTHER_USER_ID, Long.class);

			// when & exception
			assertThrows(RejectedExecutionException.class, () -> todoService.getUserTodo(TEST_TODO_ID, TEST_ANOTHER_USER));
		}
	}
}