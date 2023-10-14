package com.checkmate.authentication.Model;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import org.apache.catalina.User;
import org.apache.catalina.util.CharsetMapper;
import org.apache.coyote.ErrorState;
import org.apache.tomcat.util.codec.binary.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cglib.core.Local;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.Period;

public class RegistrationForm {
    /*
        {
            Username: String,
            FullName: List<String FirstName, String LastName>
            DateOfBirth: String; "MM-DD-YYYY"
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

        // Case: Regex

        return true;
    }

    public boolean isDateOfBirthValid() {
        // Case: DOB is null
        if (DateOfBirth == null) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        // Case: DOB isnt %2d-%2d-%4d
        String[] splitDOB = DateOfBirth.split("-");
        if (splitDOB.length != 3) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        // Case: Month Out of Bounds
        String Month = splitDOB[0];

        // Month is Digits
        if (Month.matches("[^0-9]")) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        // Month is MM
        if (Month.length() != 1 && Month.length() != 2) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        // Month is between 1 and 12
        int MonthNum = Integer.parseInt(Month);
        if (MonthNum > 12 || MonthNum < 1) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        ////

        // Case: Day
        String Day = splitDOB[1];

        // Day is Digits
        if (Day.matches("[^0-9]")) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        // Day is DD
        if (Day.length() != 1 && Day.length() != 2) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        // Day is between 1 and 31
        int DayNum = Integer.parseInt(Day);
        if (DayNum > 31 || DayNum < 1) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        // Case: Year
        String Year = splitDOB[2];

        // Day is Digits
        if (Year.matches("[^0-9]")) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        // Day is DD
        if (Year.length() != 4) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }

        // Day is between 1 and 31
        int YearNum = Integer.parseInt(Year);
        if (YearNum > LocalDate.now().getYear()) {
            this.ErroredFields.add("DateOfBirth");
            return false;
        }


        // Case: Must be 13 Years Old or Older
        LocalDate now = LocalDate.now();

        String formattedDate = String.format("%04d-%02d-%02d", YearNum, MonthNum, DayNum);
        LocalDate DOBObject = LocalDate.parse(formattedDate);
        int age = Period.between(DOBObject, now).getYears();

        if (age < 13) {
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

        String[] Errors = new String[this.ErroredFields.size()];

        for (int sidx = 0; sidx < this.ErroredFields.size(); sidx++) {
            Errors[sidx] = this.ErroredFields.get(sidx);
        }

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