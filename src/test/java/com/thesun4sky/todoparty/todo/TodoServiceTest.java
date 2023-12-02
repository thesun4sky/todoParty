package com.thesun4sky.todoparty.todo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thesun4sky.todoparty.test.TodoTest;

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
		var testTodo = new Todo(TEST_TODO_REQUEST_DTO);
		testTodo.setUser(TEST_USER);

		// when
		var result = todoService.createTodo(TEST_TODO_REQUEST_DTO, TEST_USER);

		// then
		var expect = new TodoResponseDTO(testTodo);
		assertThat(result.getId()).isEqualTo(expect.getId());
	}

	@Test
	void getTodoDto() {
	}

	@Test
	void getUserTodoMap() {
	}

	@Test
	void updateTodo() {
	}

	@Test
	void competeTodo() {
	}

	@Test
	void getTodo() {
	}

	@Test
	void getUserTodo() {
	}
}