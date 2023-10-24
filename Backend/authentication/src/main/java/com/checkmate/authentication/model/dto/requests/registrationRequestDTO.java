package com.checkmate.authentication.model.dto.requests;

import lombok.*;

@Data
public class registrationRequestDTO {
        private String username;
        private String password;
        private String email;
}
