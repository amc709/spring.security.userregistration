package com.amc.spring.security.userregistration.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amc.spring.security.userregistration.entity.AppUser;
import com.amc.spring.security.userregistration.entity.Token;
import com.amc.spring.security.userregistration.repository.AppUserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {


	private AppUserRepository appUserRepository;


	private TokenService tokenService;

	private final PasswordEncoder passwordEncoder;

	private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

	//@formatter:off

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		return this.appUserRepository
				.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	
	}
	
	@Transactional
	public String signUpUser(AppUser appUser) {
		
		// Check if user exists
		boolean userExists = appUserRepository.findByEmail((appUser.getEmail())).isPresent();
        if(userExists){
            throw new IllegalStateException("User already exists");
        }
        
        // Create an encrypted password.
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        Token confirmationToken = new Token(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10L),
                appUser
        );

        tokenService.saveConfirmationToken(confirmationToken);
        return token;
	}
	
	public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

}
