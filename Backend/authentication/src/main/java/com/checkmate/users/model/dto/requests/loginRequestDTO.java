package com.checkmate.users.model.dto.requests;

import lombok.Data;

@Data
public class loginRequestDTO {
    private String identifier;  // This can be either username or email
    private String password;

    // Default constructor, getters, and setters...

}