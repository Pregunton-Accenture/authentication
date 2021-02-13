package com.accenture.authentication.repository;

import com.accenture.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, String> {
  Optional<UserCredentials> findByUsername(String username);
}