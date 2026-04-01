package com.amc.spring.security.userregistration.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.amc.spring.security.userregistration.constant.AppUserRole;
import com.amc.spring.security.userregistration.dto.RegistrationRequest;
import com.amc.spring.security.userregistration.entity.AppUser;
import com.amc.spring.security.userregistration.entity.Token;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

	private final AppUserService appUserService;
	private final TokenService tokenService;
	private final EmailSender emailSender;

	public String register(RegistrationRequest request) {
		AppUser appUser = new AppUser(request.getFirstname(), request.getLastname(), request.getEmail(),
				request.getPassword(), AppUserRole.USER);
		String token = appUserService.signUpUser(appUser);
		String confirmationLink = "http://localhost:9090/registration/confirmation?token=" + token;

		// send the email.
		emailSender.send(request.getEmail(), buildEmail(request.getFirstname(), confirmationLink));

		return token;
	}

	@Transactional
	public String confirmToken(String token) {
		String loginLink = "http://localhost:9090/registration/login";
		Token confirmationToken = tokenService.getToken(token)
				.orElseThrow(() -> new IllegalStateException("token not found"));

		if (confirmationToken.getConfirmedAt() != null) {
			throw new IllegalStateException(buildConfirmedPage(loginLink, "Email already confirmed."));
		}

		LocalDateTime expiredAt = confirmationToken.getExpiresAt();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}

		tokenService.setConfirmedAt(token);
		appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
		return buildConfirmedPage(loginLink, "Email Confirmed");
	}

	private String buildEmail(String name, String link) {
		return "<p> Hi " + name + ",</p>"
				+ "<p> Thank you for registering. Please click on the below link to activate your account: </p>"
				+ "<blockquote><p> <a href=\"" + link
				+ "\">Activate Now</a> </p></blockquote>\n Link will expire in 10 minutes.";
	}

	private String buildConfirmedPage(String link, String message) {
		return "<p> " + message + ". Please click on the below link to Login: </p>" + "<blockquote><p> <a href=\""
				+ link + "\">Login</a> </p>";
	}
}
