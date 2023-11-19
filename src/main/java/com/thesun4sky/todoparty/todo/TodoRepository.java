package com.thesun4sky.todoparty.todo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesun4sky.todoparty.user.User;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
