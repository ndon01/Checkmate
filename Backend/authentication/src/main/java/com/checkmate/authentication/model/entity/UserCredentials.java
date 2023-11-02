package com.checkmate.authentication.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@Table(name = "credentials")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;

    @Column(name = "email_address")
    private String emailAddress;

    private String password;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    // Getters, setters, and other methods
}
