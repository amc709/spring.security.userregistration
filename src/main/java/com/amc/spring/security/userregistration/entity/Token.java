package com.amc.spring.security.userregistration.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
public class Token {

	@Id
	@SequenceGenerator(name = "token_sequence", sequenceName = "token_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_sequence")
	private Long id;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime expiresAt;

	private LocalDateTime confirmedAt;

	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private AppUser appUser;

	public Token(String token, LocalDateTime createdAt, LocalDateTime expiresAt, AppUser appUser) {
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.appUser = appUser;
	}
}
