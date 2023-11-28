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

    // Variables with predefined values
    @Column(name = "match_status")
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus = MatchStatus.PENDING;

    @Column(name = "is_rated")
    private boolean isRated = true;

    @Column(name = "is_draw")
    private boolean isDraw = false;

    @Column(name = "draw_requested")
    private boolean drawRequested = false;

    @Column(name = "draw_requester_id")
    private Long drawRequesterId = null;

    @Column(name = "is_abandoned")
    private boolean isAbandoned = false;

    @Column(name = "is_forfeited")
    private boolean isForfeited = false;


    @Column(name ="match_moves")
    private String matchMoves = "";

    @Column(name = "current_board")
    private String currentBoard = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    @Column(name = "isWhiteTurn")
    private boolean isWhiteTurn = true;

    // Other variables
    @Column(name = "match_type")
    @Enumerated(EnumType.STRING)
    private MatchType matchType; // Assuming matchType is set externally and does not have a default

    @Column(name = "white_user_id")
    private Long whiteUserId;

    @Column(name = "black_user_id")
    private Long blackUserId;

    @Column(name = "last_white_ping")
    private Long lastWhitePing;

    @Column(name = "last_black_ping")
    private Long lastBlackPing;

    @Column(name = "winner_user_id")
    private Long winnerUserId;

    @Column(name = "last_move_time")

    private Long lastMoveTime;

    @Column(name = "white_time_left_seconds")
    private Long whiteTimeLeft;

    @Column(name = "black_time_left_seconds")
    private Long blackTimeLeft;

    public enum MatchStatus {
        PENDING,
        PROGRESS,
        FINISHED
    }

    public enum MatchType {
        STANDARD,
        BLITZ,
        BULLET
    }
}
