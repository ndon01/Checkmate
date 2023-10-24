package com.checkmate.authentication.repository;

import com.checkmate.authentication.model.entity.UserTokens;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {
    Optional<UserTokens> findByUser_UserId(Long userId);
}
