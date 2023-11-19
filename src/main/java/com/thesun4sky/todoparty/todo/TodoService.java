package com.thesun4sky.todoparty.todo;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.thesun4sky.todoparty.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
	private final TodoRepository todoRepository;

	public TodoResponseDTO createPost(TodoRequestDTO dto, User user) {
		Todo todo = new Todo(dto);
		todo.setUser(user);

		todoRepository.save(todo);

		return new TodoResponseDTO(todo);
	}

	public TodoResponseDTO getTodo(Long todoId) {
		Todo todo = todoRepository.findById(todoId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다."));
		return new TodoResponseDTO(todo);
	}
}
