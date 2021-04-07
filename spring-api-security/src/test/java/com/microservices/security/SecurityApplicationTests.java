package com.microservices.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SecurityApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testPasswordEncoders_Bcrypt() {
		System.out.println(new BCryptPasswordEncoder().encode("kumar"));
	}

	@Test
	void testPasswordEncoders_Pbk() {
		System.out.println(new Pbkdf2PasswordEncoder().encode("kumar"));
	}

	@Test
	void testPasswordEncoders_Scrypt() {
		System.out.println(new SCryptPasswordEncoder().encode("kumar"));
	}

	@Test
	void testPasswordEncoders_Delegate() {
		Map<String, PasswordEncoder> map = new HashMap<>();
		map.put("pbk", new Pbkdf2PasswordEncoder());
		map.put("bcrypt", new BCryptPasswordEncoder());

		System.out.println(new DelegatingPasswordEncoder("pbk", map).encode("password"));

	}

}
