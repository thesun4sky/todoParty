package com.thesun4sky.todoparty.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TodoRequestDTO {
	private String title;
	private String content;
}
