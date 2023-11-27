package com.checkmate.users.model.dto.responses;

import lombok.Data;

@Data
public class UserProfileDTO {
    private long userId;
    private String username;
    private String displayName;
    private String biography;
}
