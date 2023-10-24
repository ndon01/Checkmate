package com.checkmate.authentication.repository;

import com.checkmate.authentication.model.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByUsername(String username);
    Optional<UserCredentials> findByEmailAddress(String email);
}
