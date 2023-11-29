package com.checkmate.matches.service;


import com.checkmate.matches.config.RabbitMqConfig;
import com.checkmate.matches.controller.MatchController;
import com.checkmate.matches.model.entity.Match;
import com.checkmate.matches.model.rabbitmq.MatchEventDTO;
import com.checkmate.matches.model.rabbitmq.MatchEventType;
import com.checkmate.matches.model.util.Game.Board;
import com.checkmate.matches.model.util.Game.Move;
import com.checkmate.matches.repository.MatchRepository;
import com.checkmate.matches.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.checkmate.matches.*;

import java.time.Instant;
import java.util.Optional;

@Service
public class MatchService {

    // Logger

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    /***
     * Creates a match between two users and currently assumes the match type
     * @param User1Id
     * @param User2Id
     */
    public void createMatch(Long User1Id, Long User2Id) {
        logger.info("Creating match between " + User1Id + " and " + User2Id);

        // randomly pick if user1 or user2 is white
        boolean user1IsWhite = Math.random() < 0.5;

        // create match object
        Match newMatch = new Match();
        newMatch.setWhiteUserId(user1IsWhite ? User1Id : User2Id);
        newMatch.setBlackUserId(user1IsWhite ? User2Id : User1Id);

        newMatch.setMatchType(Match.MatchType.BLITZ);

        newMatch.setWhiteTimeLeft(5 * 60L);
        newMatch.setBlackTimeLeft(5 * 60L);


        matchRepository.save(newMatch);
    }

    public Match pingMatch(long matchId, long userId) {
        Optional<Match> optionalMatch = matchRepository.findById(matchId);
        if (optionalMatch.isEmpty()) {
            return null;
        }

        Match thisMatch = optionalMatch.get();

        if (thisMatch.isFinished()) {
            return null;
        }

        if (thisMatch.getWhiteUserId() == userId) {
            thisMatch.setLastWhitePing(Instant.now().getEpochSecond());
            thisMatch = matchRepository.saveAndFlush(thisMatch);
        } else if (thisMatch.getBlackUserId() == userId) {
            thisMatch.setLastBlackPing(Instant.now().getEpochSecond());
            thisMatch = matchRepository.saveAndFlush(thisMatch);
        }

        if ((Instant.now().getEpochSecond() - thisMatch.getLastMoveTime()) > 30000 && (thisMatch.getMatchStatus() != Match.MatchStatus.PENDING || thisMatch.getMatchMoves().isEmpty()) || thisMatch.getMatchMoves().length() == 5) {
            thisMatch.setAbandoned(true);
            thisMatch.setMatchStatus(Match.MatchStatus.FINISHED);
            finishMatch(thisMatch);
            return matchRepository.saveAndFlush(thisMatch);
        }

        if (thisMatch.getBlackTimeLeft() <= 0 || thisMatch.getWhiteTimeLeft() <= 0) {
            thisMatch.setMatchStatus(Match.MatchStatus.FINISHED);
            finishMatch(thisMatch);
            return matchRepository.saveAndFlush(thisMatch);
        }

        if (thisMatch.getMatchStatus() == Match.MatchStatus.FINISHED) {
            thisMatch.setFinished(true);
            finishMatch(thisMatch);
            return matchRepository.saveAndFlush(thisMatch);
        }

        // if length is zero and match is progress and it has been 30 seconds, abandon the match
        if (thisMatch.getMatchStatus() != Match.MatchStatus.PENDING && thisMatch.getMatchMoves().isEmpty() && (Instant.now().getEpochSecond() - thisMatch.getLastMoveTime()) > 30000) {
            thisMatch.setAbandoned(true);
            thisMatch.setMatchStatus(Match.MatchStatus.FINISHED);
            finishMatch(thisMatch);
            return matchRepository.saveAndFlush(thisMatch);
        }

        if (thisMatch.getMatchStatus() != Match.MatchStatus.PENDING && thisMatch.getMatchMoves().length() == 5 && (Instant.now().getEpochSecond() - thisMatch.getLastMoveTime()) > 30000) {
            thisMatch.setAbandoned(true);
            thisMatch.setMatchStatus(Match.MatchStatus.FINISHED);
            finishMatch(thisMatch);
            return matchRepository.saveAndFlush(thisMatch);
        }

        // if both users have pinged and not started, start the match
        if (thisMatch.getMatchStatus() == Match.MatchStatus.PENDING && thisMatch.getLastWhitePing() != null && thisMatch.getLastBlackPing() != null) {
            thisMatch.setMatchStatus(Match.MatchStatus.PROGRESS);
            thisMatch.setLastMoveTime(Instant.now().getEpochSecond());
        }

        if (!thisMatch.getCurrentBoard().contains("k")) {
            thisMatch.setMatchStatus(Match.MatchStatus.FINISHED);
            thisMatch.setWinnerUserId(thisMatch.getBlackUserId());
            return matchRepository.saveAndFlush(thisMatch);
        }

        if (!thisMatch.getCurrentBoard().contains("K")) {
            thisMatch.setMatchStatus(Match.MatchStatus.FINISHED);
            thisMatch.setWinnerUserId(thisMatch.getBlackUserId());
            return matchRepository.saveAndFlush(thisMatch);
        }


        return matchRepository.saveAndFlush(thisMatch);
    }

    public void finishMatch(Match match) {
        // post to rabbitmq

        MatchEventDTO matchEventDTO = new MatchEventDTO();
        matchEventDTO.setEventType(MatchEventType.MATCH_FINISHED);
        matchEventDTO.addAdditionalDetails("matchId", Long.toString(match.getMatchId()));
        matchEventDTO.addAdditionalDetails("whiteUserId", String.valueOf(match.getWhiteUserId()));
        matchEventDTO.addAdditionalDetails("blackUserId", String.valueOf(match.getBlackUserId()));

        rabbitTemplate.convertAndSend(RabbitMqConfig.MATCHMAKING_MICROSERVICE_DIRECT_EXCHANGE, "matchmaking_microservice_direct_queue", matchEventDTO);
    }

    public void debugUserMatches(long userId) {
        // post to rabbitmq

        MatchEventDTO matchEventDTO = new MatchEventDTO();
        matchEventDTO.setEventType(MatchEventType.DEBUG_USER_MATCHES);
        matchEventDTO.addAdditionalDetails("userId", String.valueOf(userId));
        rabbitTemplate.convertAndSend(RabbitMqConfig.MATCHMAKING_MICROSERVICE_DIRECT_EXCHANGE, "matchmaking_microservice_direct_queue", matchEventDTO);

    }


    public boolean resignRequest(long userId) {
        Optional<Match> optionalMatch = matchRepository.findActiveMatchByUserId(userId);
        if (optionalMatch.isEmpty()) {
            return false;
        }

        Match thisMatch = optionalMatch.get();
        boolean isWhite;
        long otherUser;
        if (thisMatch.getBlackUserId() == userId) {
            isWhite = false;
            otherUser = thisMatch.getWhiteUserId();
        } else {
            isWhite = true;
            otherUser = thisMatch.getBlackUserId();
        }

        thisMatch.setForfeited(true);
        thisMatch.setWinnerUserId(otherUser);
        matchRepository.saveAndFlush(thisMatch);
        return true;
    }

    public boolean drawResponse(long userId, boolean response) {
        Optional<Match> optionalMatch = matchRepository.findActiveMatchByUserId(userId);
        if (optionalMatch.isEmpty()) {
            return false;
        }

        Match thisMatch = optionalMatch.get();


        if (!thisMatch.isDrawRequested()) {
            return false;
        }
        if (thisMatch.getDrawRequesterId() != userId) {
            return false;
        }
        if (!response) {
            thisMatch.setDrawRequested(false);
            thisMatch.setDrawRequesterId(null);
            matchRepository.saveAndFlush(thisMatch);
            return true;
        }

        thisMatch.setDraw(true);
        thisMatch.setMatchStatus(Match.MatchStatus.FINISHED);
        matchRepository.saveAndFlush(thisMatch);

        return true;

    }

    public boolean drawRequest(long userId) {
        Optional<Match> optionalMatch = matchRepository.findActiveMatchByUserId(userId);
        if (optionalMatch.isEmpty()) {
            return false;
        }

        Match myMatch = optionalMatch.get();


        if (myMatch.isDrawRequested() && myMatch.getDrawRequesterId() != userId) {

            myMatch.setDraw(true);
            myMatch.setMatchStatus(Match.MatchStatus.FINISHED);
            matchRepository.saveAndFlush(myMatch);
            return true;
        }

        myMatch.setDrawRequested(true);
        myMatch.setDrawRequesterId(userId);
        matchRepository.saveAndFlush(myMatch);
        return true;


    }


    public boolean makeMove(long userID, String move) {
        Board myBoard = new Board();
        long now = Instant.now().getEpochSecond();

        Optional<Match> optionalMatch = matchRepository.findActiveMatchByUserId(userID);
        if (optionalMatch.isEmpty()) {
            return false;
        }
        Match thisMatch = optionalMatch.get();
        boolean isTurn = true;

        if (thisMatch.getIsWhiteTurn() == true) {
            if (thisMatch.getWhiteUserId() == userID) {
                isTurn = true;
            }

        } else {
            if (thisMatch.getBlackUserId() == userID) {
                isTurn = true;
            }

        }

        if (!isTurn) {
            return false;
        }

        myBoard.boardMaker(thisMatch.getCurrentBoard());
        Move myMove = new Move(move);
        boolean moveWorked = myBoard.move(myMove);
        if (moveWorked == false) {
            return false;
        }

        String endState = myBoard.stringMaker();
        thisMatch.setCurrentBoard(endState);
        thisMatch.setMatchMoves(thisMatch.getMatchMoves() + move + ",");

        long timeTaken = now - thisMatch.getLastMoveTime();
        if (userID == thisMatch.getWhiteUserId()) {
            long newTime = thisMatch.getWhiteTimeLeft() - timeTaken;
            thisMatch.setWhiteTimeLeft(newTime);
        } else {
            long newTime = thisMatch.getBlackTimeLeft() - timeTaken;
            thisMatch.setWhiteTimeLeft(newTime);
        }

        thisMatch.setIsWhiteTurn(!thisMatch.getIsWhiteTurn());
        thisMatch.setLastMoveTime(now);

        matchRepository.saveAndFlush(thisMatch);
        return true;

        //get current match
        // check turn return null
        // all validation
        // create board
        //make move

    }


}
