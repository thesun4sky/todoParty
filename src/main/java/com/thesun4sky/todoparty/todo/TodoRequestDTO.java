package com.thesun4sky.todoparty.todo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class TodoRequestDTO {
	private String title;
	private String content;
}
