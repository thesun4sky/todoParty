package com.thesun4sky.todoparty.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesun4sky.todoparty.todo.Todo;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
