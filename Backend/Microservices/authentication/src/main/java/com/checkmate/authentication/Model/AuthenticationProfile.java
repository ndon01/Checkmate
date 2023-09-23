package com.checkmate.authentication.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name="AuthenticationProfiles")
public class AuthenticationProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id
}
