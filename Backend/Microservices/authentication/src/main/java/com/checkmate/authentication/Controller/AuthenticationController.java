package com.checkmate.authentication.Controller;

import com.checkmate.authentication.Model.LoginForm;
import com.checkmate.authentication.Model.RegistrationForm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    // Register -> (Refresh Token, Access Token)
    @PostMapping("/register")
    public String processRegistration(@RequestBody(required = true) RegistrationForm registrationForm) {
        /*
        {
            Username: String,
            FullName: List<String FirstName, String LastName>
            DateOfBirth: String,
            Password: String,
            EmailAddress: String,
        }
         */

        System.out.println(registrationForm);
        if ((registrationForm.validate() ? "Valid" : "Invalid") == "Invalid") {
            String[] e = registrationForm.getErrors();

            for (String s : e) {
                System.out.println(s);
            }
        }

        return "";
    }
    // Login -> (Refresh Token, Access Token)
    @PostMapping("/login")
    public String proccessLogin(@RequestBody(required = true) LoginForm loginForm) {
        return "";
    }
    // Refresh-Token -> (Refresh Token, Access Token)
    @PostMapping("/refresh-token")
    public String refreshToken() {
        return "";
    }
    // Request-Reset -> "Check your {recieveType}"
    @PostMapping("/request")
    public String requestReset() {
        return "";
    }
    // Reset-Password -> (Refresh Token, Access Token)
    @PostMapping("/reset")
    public String processResetRequest() {
        return "";
    }

}
