package com.amc.spring.security.userregistration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amc.spring.security.userregistration.dto.RegistrationRequest;
import com.amc.spring.security.userregistration.service.RegistrationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

	private RegistrationService registrationService;

	@PostMapping
	public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest request) {
		return ResponseEntity.ok(registrationService.register(request));
	}

	@GetMapping("/confirmation")
	public ResponseEntity<String> confirm(@RequestParam("token") String token) {
		return ResponseEntity.ok(registrationService.confirmToken(token));
	}


}
