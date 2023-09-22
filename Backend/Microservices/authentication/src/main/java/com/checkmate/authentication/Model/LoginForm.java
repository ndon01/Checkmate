package com.checkmate.authentication.Model;

public class LoginForm {
    private String StringIdentifier;
    private String Password;

    // Username & Password
    public LoginForm(String stringIdentifier, String password) {
        this.StringIdentifier = stringIdentifier;
        this.Password = password;
    }

    public boolean validateForm() {
        return false; // no logging in
    }
}
