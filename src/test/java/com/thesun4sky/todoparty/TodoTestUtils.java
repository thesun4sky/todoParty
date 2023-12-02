package com.thesun4sky.todoparty;

import java.time.LocalDateTime;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.SerializationUtils;

import com.thesun4sky.todoparty.todo.Todo;
import com.thesun4sky.todoparty.user.User;

public class TodoTestUtils {

	public static Todo getTestTodo(Todo todo, Long id, LocalDateTime createDate, User user) {
		Todo newTodo = SerializationUtils.clone(todo);
		ReflectionTestUtils.setField(newTodo, Todo.class, "id", id, Long.class);
		ReflectionTestUtils.setField(newTodo, Todo.class, "createDate", createDate, LocalDateTime.class);
		newTodo.setUser(user);
		return newTodo;
	}
}
