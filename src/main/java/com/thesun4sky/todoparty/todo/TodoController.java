package com.thesun4sky.todoparty.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thesun4sky.todoparty.CommonResponseDTO;
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
		TodoResponseDTO responseDTO = todoService.createTodo(todoRequestDTO, userDetails.getUser());

		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/{todoId}")
	public ResponseEntity<CommonResponseDTO> getTodo(@PathVariable Long todoId) {
		try {
			TodoResponseDTO responseDTO = todoService.getTodoDto(todoId);
			return ResponseEntity.ok().body(responseDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
	}

	@GetMapping
	public ResponseEntity<List<TodoListResponseDTO>> getTodoList() {
		List<TodoListResponseDTO> response = new ArrayList<>();

		Map<UserDTO, List<TodoResponseDTO>> responseDTOMap = todoService.getUserTodoMap();

		responseDTOMap.forEach((key, value) -> response.add(new TodoListResponseDTO(key, value)));

		return ResponseEntity.ok().body(response);
	}


	@PutMapping("/{todoId}")
	public ResponseEntity<TodoResponseDTO> putTodo(@PathVariable Long todoId, @RequestBody TodoRequestDTO todoRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			TodoResponseDTO responseDTO = todoService.updateTodo(todoId, todoRequestDTO, userDetails.getUser());
			return ResponseEntity.ok().body(responseDTO);
		} catch (RejectedExecutionException | IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(new TodoResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
	}


	@PatchMapping("/{todoId}/complete")
	public ResponseEntity<TodoResponseDTO> completeTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			TodoResponseDTO responseDTO = todoService.completeTodo(todoId, userDetails.getUser());
			return ResponseEntity.ok().body(responseDTO);
		} catch (RejectedExecutionException | IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(new TodoResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
	}
}
