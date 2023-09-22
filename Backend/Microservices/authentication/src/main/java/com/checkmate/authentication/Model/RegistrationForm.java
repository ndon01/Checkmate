package com.checkmate.authentication.Model;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import org.apache.catalina.User;
import org.apache.catalina.util.CharsetMapper;
import org.apache.coyote.ErrorState;
import org.apache.tomcat.util.codec.binary.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class RegistrationForm {
    /*
        {
            Username: String,
            FullName: List<String FirstName, String LastName>
            DateOfBirth: String,
            Password: String,
            EmailAddress: String,
        }
     */

    private String DisplayName;

    private String Username;
    private String EmailAddress;
    private String DateOfBirth;
    private String Password;

    private ArrayList<String> ErroredFields;

    public RegistrationForm(String DisplayName, String Username, String EmailAddress, String DateOfBirth, String Password) {
        this.DisplayName = DisplayName;
        this.Username = Username;
        this.EmailAddress = EmailAddress;
        this.DateOfBirth = DateOfBirth;
        this.Password = Password;

        this.ErroredFields = new ArrayList();
    }

    ////

    public boolean validate() {
        boolean validity = true;

        // Display Name 
        validity &= isDisplayNameValid();
        // Username
        validity &= isUsernameValid();
        // Email Address
        validity &= isEmailAddressValid();
        // Date Of Birth
        validity &= isDateOfBirthValid();
        // Password
        validity &= isPasswordValid();

        return validity;
    }

    //

    public boolean isDisplayNameValid() {
        // Case: Display Name is null
        if (DisplayName == null) {
            this.ErroredFields.add("DisplayName");
            return false;
        }

        // Case: Display Name length, (0 <= length <= 32)
        int length = DisplayName.length();
        if (length <= 0 || length > 32) {
            this.ErroredFields.add("DisplayName");
            return false;
        }

        // Case: Display Name Can only contain Aa-Zz, and Digits
        if(DisplayName.matches("[^A-Za-z0-9]")) {
            this.ErroredFields.add("DisplayName");
            return false;
        }

        return true;
    }

    public boolean isUsernameValid() {
        // Case:  Username is null
        if (Username == null) {
            this.ErroredFields.add("Username");
            return false;
        }

        // Case: Username length, (0 <= length <= 32)
        int length = Username.length();
        if (length <= 0 || length > 20) {
            this.ErroredFields.add("Username");
            return false;
        }

        // Case: Username Can only contain Aa-Zz, and Digits
        if(Username.matches("[^A-Za-z0-9]")) {
            this.ErroredFields.add("Username");
            return false;
        }

        return true;
    }

    public boolean isEmailAddressValid() {

        // Case: Email Address is Null
        if (EmailAddress == null) {
            this.ErroredFields.add("EmailAddress");
            return false;
        }

        // Case: 

        return true;
    }
    
    public boolean isDateOfBirthValid() {
        if (DateOfBirth == null) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        return true;
    }

    public boolean isPasswordValid() {
        if (Password == null) {
            this.ErroredFields.add("Password");
            return false;
        }

        return true;
    }
    ////

    public String[] getErrors() {

        String[] Errors = (String[]) this.ErroredFields.toArray();


        return Errors;
    }

    public String toString() {
        return "{" + 
            "\n\tDisplayName: " + DisplayName + 
            "\n\tUsername: " + Username + 
            "\n\tEmailAddress: " + EmailAddress + 
            "\n\tDateOfBirth: " + DateOfBirth + 
            "\n\tPassword: " + Password + 
        "\n}";
    }

}
