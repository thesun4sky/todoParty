package com.thesun4sky.todoparty.todo;

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
}
