package com.amc.spring.security.userregistration.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.amc.spring.security.userregistration.constant.AppUserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {

	private static final long serialVersionUID = 2613050805167515897L;

	@Id
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	private Long id;

	@Column(name = "first_name")
	private String firstname;

	@Column(name = "last_name")
	private String lastname;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private AppUserRole appUserRole;

	private Boolean locked = false;
	private Boolean enabled = false;

	@Column(name = "create_dttm")
	private LocalDateTime createDttm;

	@Column(name = "update_dttm")
	private LocalDateTime updateDttm;

	public AppUser(String firstname, String lastname, String email, String password, AppUserRole appUserRole) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.appUserRole = appUserRole;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
		return Collections.singletonList(authority);
	}

	@Override
	public @Nullable String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		if (this.createDttm == null) {
			this.createDttm = now;
		}
		if (this.updateDttm == null) {
			this.updateDttm = now;
		}
	}

	@PreUpdate
	protected void onUpdate() {
		if (this.updateDttm == null) {
			this.updateDttm = LocalDateTime.now();
		}
	}

}
