package com.amc.spring.security.userregistration.service;

public interface EmailSender {

	void send(String to, String email);
}
