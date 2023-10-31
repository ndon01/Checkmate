package com.checkmate.users.model.rabbitmq;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerificationMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String token;

    // Getters, setters, and constructors
}
