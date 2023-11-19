package com.thesun4sky.todoparty.todo;

import java.time.LocalDateTime;

import com.thesun4sky.todoparty.CommonResponseDto;
import com.thesun4sky.todoparty.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoResponseDTO extends CommonResponseDto {
	private Long id;
	private String title;
	private String content;
	private Boolean isCompleted;
	private UserDTO user;
	private LocalDateTime createDate;

	public TodoResponseDTO(Todo todo) {
		this.id = todo.getId();
		this.title = todo.getTitle();
		this.content = todo.getContent();
		this.isCompleted = todo.getIsCompleted();
		this.user = new UserDTO(todo.getUser());
		this.createDate = todo.getCreateDate();
	}
}
