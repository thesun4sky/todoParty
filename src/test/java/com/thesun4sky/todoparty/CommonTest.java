package com.thesun4sky.todoparty;

import com.thesun4sky.todoparty.user.User;

interface CommonTest {
	String ANOTHER_PREFIX = "another-";
	String TEST_USER_NAME = "username";
	String TEST_USER_PASSWORD = "password";
	User TEST_USER = User.builder()
		.username(TEST_USER_NAME)
		.password(TEST_USER_PASSWORD)
		.build();
	User TEST_ANOTHER_USER = User.builder()
		.username(ANOTHER_PREFIX + TEST_USER_NAME)
		.password(ANOTHER_PREFIX + TEST_USER_PASSWORD)
		.build();
}
