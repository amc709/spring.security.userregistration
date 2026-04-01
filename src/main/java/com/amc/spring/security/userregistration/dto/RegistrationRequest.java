package com.amc.spring.security.userregistration.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequest implements Serializable {

	private static final long serialVersionUID = 1931353487007721005L;

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;

	@NotNull
	@Size(min = 8)
	private String password;

	@NotNull
	@Email
	private String email;

}
