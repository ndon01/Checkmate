package com.checkmate.matches.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;

    @Column(name = "white_user_id")
    private Long whiteUserId;

    @Column(name = "black_user_id")
    private Long blackUserId;

    @Column(name = "current_board")
    private String currentBoard;

    @Column(name = "moves")
    private String matchMoves;

}
