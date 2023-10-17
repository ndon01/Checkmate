package com.checkmate.users.Controller;

import com.checkmate.users.Model.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
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
        boolean isFormValid = registrationForm.validate();
        if (!isFormValid) {
            // Form is not valid
            System.out.println("Invalid Form");

            for (String s : registrationForm.getErrors()) {
                System.out.println("INVALID FIELD: " + s);
            }

            return "";
        }

        // Form is valid

        System.out.println("Valid Form: creating User.");

        System.out.println(registrationForm);

        return "";
    }

}
