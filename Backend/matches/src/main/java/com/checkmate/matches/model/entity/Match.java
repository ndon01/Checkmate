package com.checkmate.matches.model.entity;

import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    private String currentBoard = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    @Column(name = "moves")
    private String matchMoves;

    @Column(name = "is_white_move")
    private boolean isWhiteMove = true;

    @Column(name = "is_finished")
    private boolean isFinished = false;

    @Column(name = "winner_user_id")
    private Long winnerUserId;

}
