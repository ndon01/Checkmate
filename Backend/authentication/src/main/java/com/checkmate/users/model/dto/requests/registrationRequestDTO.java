package com.checkmate.users.model.dto.requests;

import lombok.*;

@Data
public class registrationRequestDTO {
        private String username;
        private String password;
        private String email;
        private String dateOfBirth; // You can also use LocalDate if you prefer
}
