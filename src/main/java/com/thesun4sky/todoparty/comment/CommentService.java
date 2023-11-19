package com.thesun4sky.todoparty.comment;

import java.util.concurrent.RejectedExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thesun4sky.todoparty.todo.Todo;
import com.thesun4sky.todoparty.todo.TodoService;
import com.thesun4sky.todoparty.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final TodoService todoService;

	public CommentResponseDTO createComment(CommentRequestDTO dto, User user) {
		Todo todo = todoService.getTodo(dto.getTodoId());

		Comment comment = new Comment(dto);
		comment.setUser(user);
		comment.setTodo(todo);

		commentRepository.save(comment);

		return new CommentResponseDTO(comment);
	}

	@Transactional
	public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO, User user) {
		Comment comment = getUserComment(commentId, user);

		comment.setText(commentRequestDTO.getText());

		return new CommentResponseDTO(comment);
	}

	public void deleteComment(Long commentId, User user) {
		Comment comment = getUserComment(commentId, user);

		commentRepository.delete(comment);
	}

	private Comment getUserComment(Long commentId, User user) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다."));

		if(!user.getId().equals(comment.getUser().getId())) {
			throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
		}
		return comment;
	}
}
