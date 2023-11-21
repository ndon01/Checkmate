package com.checkmate.matchmaking.model.entity;

import jakarta.persistence.*;
import lombok.*;

enum matchRequeust {
    PENDING,
    ACCEPTED,
    DECLINED,
    NONE
}

@Entity
@Data
@Table(name = "queuers")
public class Queuer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queuer_id")
    private Long queuerId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "in_queue")
    private boolean inQueue = false;

    @Column(name = "elo")
    private int elo = 1800;

    @Column(name = "match_requested")
    private boolean matchRequested = false;

    @Column(name = "match_accepted")
    private boolean match_accepted = false;


}
