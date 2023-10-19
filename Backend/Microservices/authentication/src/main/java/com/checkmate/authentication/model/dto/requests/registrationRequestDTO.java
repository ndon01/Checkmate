package com.checkmate.authentication.model.dto.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class registrationRequestDTO {
        private String username;
        private String password;
        private String email;
        private String dateOfBirth; // You can also use LocalDate if you prefer
}
