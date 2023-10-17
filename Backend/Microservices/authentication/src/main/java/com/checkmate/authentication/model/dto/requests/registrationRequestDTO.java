package com.checkmate.authentication.model.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class registrationRequestDTO {
    private String identifier;  // This can be either username or email
    private String password;

    // Default constructor, getters, and setters...

}
