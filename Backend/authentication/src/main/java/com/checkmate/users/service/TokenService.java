package com.checkmate.users.service;

import com.checkmate.users.model.entity.UserCredentials;
import com.checkmate.users.model.entity.UserTokens;
import com.checkmate.users.repository.UserTokensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private UserTokensRepository userTokensRepository;

    public void registerUserToken(UserCredentials User) {
        System.out.println("Registering User Tokens");
        UserTokens newUserTokens = new UserTokens();
        newUserTokens.setUser(User);
        newUserTokens.setRefreshToken("ABCD");
        userTokensRepository.save(newUserTokens);
    }
}
