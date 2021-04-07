package com.microservices.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "Spring Security";
	}

	@GetMapping("/hellov1")
	public String hellov1() {
		return "Spring Security v1";
	}

}
