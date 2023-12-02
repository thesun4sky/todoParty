package com.thesun4sky.todoparty.test;

import java.time.LocalDateTime;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.SerializationUtils;

import com.thesun4sky.todoparty.todo.Todo;
import com.thesun4sky.todoparty.user.User;

public class TodoTestUtils {

	public static Todo get(Todo todo, User user) {
		return get(todo, 1L, LocalDateTime.now(), user);
	}

	/**
	 * 테스트용 할일 객체를 만들어주는 메서드
	 * @param todo 복제할 할일 객체
	 * @param id 설정할 아이디
	 * @param createDate 설정할 생성일시
	 * @param user 연관관계 유저
	 * @return 테스트용으로 생성된 할일 객체
	 */
	public static Todo get(Todo todo, Long id, LocalDateTime createDate, User user) {
		var newTodo = SerializationUtils.clone(todo);
		ReflectionTestUtils.setField(newTodo, Todo.class, "id", id, Long.class);
		ReflectionTestUtils.setField(newTodo, Todo.class, "createDate", createDate, LocalDateTime.class);
		newTodo.setUser(user);
		return newTodo;
	}
}
