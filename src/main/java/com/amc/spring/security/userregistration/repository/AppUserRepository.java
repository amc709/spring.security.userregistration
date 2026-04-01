package com.amc.spring.security.userregistration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amc.spring.security.userregistration.entity.AppUser;

import jakarta.transaction.Transactional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	public Optional<AppUser> findByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE AppUser a " + "SET a.enabled = TRUE WHERE a.email = ?1")
	int enableAppUser(String email);

}
