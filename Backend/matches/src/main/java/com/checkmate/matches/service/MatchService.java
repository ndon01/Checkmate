package com.checkmate.matches.service;


import com.checkmate.matches.model.entity.Match;
import com.checkmate.matches.repository.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    // Logger

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    private MatchRepository matchRepository;


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


}
