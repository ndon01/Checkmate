package com.checkmate.authentication.model.entity;
import org.
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class AuthProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // Hot Tokens
    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "tfa_token")
    private String tfaToken;

    // Email
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "email_address_valid", nullable = false)
    private boolean isEmailValid;

    @Column(name = "email_address_validation_token", nullable = false)
    private String emailAddressValidationToken;

    // Date Of Birth
    @Column(name = "date_of_birth")
    private String dateOfBirth;

}
