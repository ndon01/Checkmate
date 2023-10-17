package com.checkmate.authentication.model.dto.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    private String username;

    @NotBlank(message = "First name is required.")
    @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters.")
    private String lastName;

    @NotBlank(message = "Date of birth is required.")
    private String dateOfBirth;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters.")
    private String password;

    @NotBlank(message = "Email address is required.")
    @Email(message = "Invalid email format.")
    private String emailAddress;
}
