package com.thesun4sky.todoparty.todo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoResponseDTO {
	private Long id;
	private String title;
	private String content;

	public TodoResponseDTO(Todo todo) {
		this.id = todo.getId();
		this.title = todo.getTitle();
		this.content = todo.getContent();
	}
}
