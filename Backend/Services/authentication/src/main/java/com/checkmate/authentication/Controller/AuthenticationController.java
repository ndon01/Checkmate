package com.checkmate.authentication.Controller;

import com.checkmate.authentication.Util.Requests.RegistrationForm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    @PostMapping("/register")
    public String processRegistration(@RequestBody(required = true) RegistrationForm registrationForm) {

        return "";
    }

}
