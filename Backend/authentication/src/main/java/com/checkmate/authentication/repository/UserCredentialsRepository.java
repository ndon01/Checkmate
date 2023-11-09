package com.checkmate.authentication.repository;

import com.checkmate.authentication.model.entity.UserCredential;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredential, Long> {
    Optional<UserCredential> findByUsername(String username);
    Optional<UserCredential> findByEmailAddress(String email);
    Optional<UserCredential> findByUserId(Long userId);
}
