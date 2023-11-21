package com.checkmate.users.model.dto.responses;

import lombok.Data;

@Data
public class UserSearchResultDTO {
    private long userId;
    private String username;

    public UserSearchResultDTO(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
