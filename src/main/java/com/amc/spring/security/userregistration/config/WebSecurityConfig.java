package com.amc.spring.security.userregistration.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.amc.spring.security.userregistration.service.AppUserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

	private final AppUserService appUserService;
	private PasswordEncoder passwordEncoder;

	//@formatter:off


	// Determines how incoming requests are passed through security.
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/registration/**").permitAll();
					auth.anyRequest().authenticated();
				})
				.formLogin(form ->  
				
					form.successHandler((request, response, authentication ) ->{
						response.sendRedirect("/registration/home");
					}
				
//					form.defaultSuccessUrl("/home", true).permitAll())
//						.withDefaults()
				))
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		ProviderManager authenticationManager = new ProviderManager(Arrays.asList(daoAuthenticationProvider()));
		authenticationManager.setAuthenticationEventPublisher(authenticationEventPublisher());
		return authenticationManager;
	}

	// Define authentication methods.
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(this.appUserService);
		provider.setPasswordEncoder(this.passwordEncoder);
		return provider;
	}

	@Bean
	public AuthenticationEventPublisher authenticationEventPublisher() {
		return new DefaultAuthenticationEventPublisher();
	}
}
