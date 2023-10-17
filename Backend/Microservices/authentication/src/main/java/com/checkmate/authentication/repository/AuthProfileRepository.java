package com.checkmate.authentication.repository;

import com.checkmate.authentication.model.entity.AuthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthProfileRepository extends JpaRepository<AuthProfile, Long> {

    Optional<AuthProfile> findByUsername(String username);

    Optional<AuthProfile> findByEmailAddress(String emailAddress);

    // Add any other query methods you need

}
