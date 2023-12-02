package com.thesun4sky.todoparty.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public void signup(UserRequestDTO userRequestDto) {
		String username = userRequestDto.getUsername();
		String password = passwordEncoder.encode(userRequestDto.getPassword());

		if (userRepository.findByUsername(username).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 유저 입니다.");
		}

		User user = new User(username, password);
		userRepository.save(user);
	}

	public void login(UserRequestDTO userRequestDto) {
		String username = userRequestDto.getUsername();
		String password = userRequestDto.getPassword();

		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));

		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
	}
}
