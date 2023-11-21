package com.checkmate.matchmaking.service;

import com.checkmate.matchmaking.model.entity.Queuer;
import com.checkmate.matchmaking.repository.QueuerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QueueService {

    private static final Logger logger = LoggerFactory.getLogger(QueueService.class);


    @Autowired
    private QueuerRepository queuerRepository;

    public void queueUserId(long userId) {

        Optional<Queuer> queuerOptional = queuerRepository.getQueuerByUserId(userId);
        Queuer q;

        // create queuer profile if non exists
        if (queuerOptional.isEmpty()) {
            q = new Queuer();
            q.setUserId(userId);
            q.setInQueue(true);
            queuerRepository.save(q);
        } else {
            q = queuerOptional.get();
        }

        Queuer opponent = queuerRepository.findOpponent(userId, q.getElo() - 500, q.getElo() + 500);

        if (opponent == null) {
            logger.info("No Opponent Found");
        } else {
            logger.info("Opponent Found");
        }

    }

    public void dequeueUserId(long userId) {

        Optional<Queuer> queuerOptional = queuerRepository.getQueuerByUserId(userId);
        Queuer q;

        // set queuer
        if (queuerOptional.isEmpty()) {
            return;
        } else {
            q = queuerOptional.get();
        }

        q.setInQueue(false);

        queuerRepository.save(q);

    }

}
