package com.checkmate.authentication.Controller;

import com.checkmate.authentication.Model.LoginForm;
import com.checkmate.authentication.Model.RegistrationForm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

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
        System.out.println(registrationForm.validate() ? "Valid" : "Invalid");

        return "";
    }

}
