package com.checkmate.authentication.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tokens")
public class UserTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private Long tokenId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserCredentials user;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "tfa_token")
    private String tfaToken;

    // Getters, setters, and other methods
}
