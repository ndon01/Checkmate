package com.checkmate.users.repository;

import com.checkmate.users.model.entity.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {

}
