package com.amc.spring.security.userregistration.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amc.spring.security.userregistration.entity.AppUser;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

	@GetMapping
	public String showHome(@AuthenticationPrincipal AppUser user) {
		return """
				<center>
				<h1>Hello, %s!</h1>
				<h3>Welcome to our app.</h3>
				</center>
				""".formatted(user.getFirstname());
	}

}
