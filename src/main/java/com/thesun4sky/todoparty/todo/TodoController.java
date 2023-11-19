package com.thesun4sky.todoparty.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thesun4sky.todoparty.CommonResponseDto;
import com.thesun4sky.todoparty.user.User;
import com.thesun4sky.todoparty.user.UserDTO;
import com.thesun4sky.todoparty.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/todos")
@RestController
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;

	@PostMapping
	public ResponseEntity<TodoResponseDTO> postTodo(@RequestBody TodoRequestDTO todoRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		TodoResponseDTO responseDTO = todoService.createPost(todoRequestDTO, userDetails.getUser());

		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/{todoId}")
	public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId) {
		try {
			TodoResponseDTO responseDTO = todoService.getTodo(todoId);
			return ResponseEntity.ok().body(responseDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
	}

	@GetMapping
	public ResponseEntity<List<TodoListResponseDTO>> getTodoList() {
		List<TodoListResponseDTO> response = new ArrayList<>();

		Map<UserDTO, List<TodoResponseDTO>> responseDTOMap = todoService.getUserTodoMap();

		responseDTOMap.forEach((key, value) -> response.add(new TodoListResponseDTO(key, value)));

		return ResponseEntity.ok().body(response);
	}
}
