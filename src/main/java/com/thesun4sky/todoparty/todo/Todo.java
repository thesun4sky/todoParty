package com.thesun4sky.todoparty.todo;

import java.time.LocalDateTime;

import com.thesun4sky.todoparty.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column
	private String content;

	@Column
	private LocalDateTime createDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Todo(TodoRequestDTO dto) {
		this.title = dto.getTitle();
		this.content = dto.getContent();
		this.createDate = LocalDateTime.now();
	}

	// 연관관계 메서드
	public void setUser(User user) {
		this.user = user;
	}

	// 서비스 메서드
	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
