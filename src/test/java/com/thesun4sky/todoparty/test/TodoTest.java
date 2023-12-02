package com.thesun4sky.todoparty.test;

import com.thesun4sky.todoparty.todo.Todo;
import com.thesun4sky.todoparty.todo.TodoRequestDTO;
import com.thesun4sky.todoparty.todo.TodoResponseDTO;

public interface TodoTest extends CommonTest {

	Long TEST_TODO_ID = 1L;
	String TEST_TODO_TITLE = "title";
	String TEST_TODO_CONTENT = "content";

	TodoRequestDTO TEST_TODO_REQUEST_DTO = TodoRequestDTO.builder()
		.title(TEST_TODO_TITLE)
		.content(TEST_TODO_CONTENT)
		.build();
	TodoResponseDTO TEST_TODO_RESPONSE_DTO = TodoResponseDTO.builder()
		.title(TEST_TODO_TITLE)
		.content(TEST_TODO_CONTENT)
		.build();
	Todo TEST_TODO = Todo.builder()
		.title(TEST_TODO_TITLE)
		.content(TEST_TODO_CONTENT)
		.build();
	Todo TEST_ANOTHER_TODO = Todo.builder()
		.title(ANOTHER_PREFIX + TEST_TODO_TITLE)
		.content(ANOTHER_PREFIX + TEST_TODO_CONTENT)
		.build();
}
