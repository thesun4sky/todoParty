package com.thesun4sky.todoparty.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private String username;

	public UserDTO(User user) {
		this.username = user.getUsername();
	}
}
