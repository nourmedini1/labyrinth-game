package com.algo.domain.repositories;


import com.algo.domain.common.Theme;
import com.algo.domain.common.utils.PagedEntity;
import com.algo.domain.entities.Challenge;
import com.mongodb.client.model.Filters;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.Bulkhead;

import org.eclipse.microprofile.faulttolerance.Timeout;

import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
//@Transactional
@Slf4j
@Bulkhead
//@CircuitBreaker
@Timeout
public class ChallengeRepository implements PanacheMongoRepositoryBase<Challenge, ObjectId> {

    public Challenge findChallengeById(ObjectId id) {
        return findById(id);
    }

    public void createChallenge(Challenge challenge) {
        persist(challenge);
    }

    public void updateChallenge(Challenge challenge) {
        update(challenge);
    }

    public void deleteChallenge(ObjectId id) {
        deleteById(id);
    }

    public PagedEntity<Challenge> searchChallenges(int page, int size, String challengerId, String challengedId, String status, int difficultyLevel, String winnerId, String theme) {
        List<Bson> filters = new ArrayList<>();

        if (challengerId != null && !challengerId.isBlank()) {
            filters.add(Filters.eq("challenger_id", challengerId));
        }
        if (challengedId != null && !challengedId.isBlank()) {
            filters.add(Filters.eq("challenged_id", challengedId));
        }
        if (status != null && !status.isBlank()) {
            filters.add(Filters.eq("status", status));
        }
        if (difficultyLevel > 0) {
            filters.add(Filters.eq("difficulty_level", difficultyLevel));
        }
        if (winnerId != null && !winnerId.isBlank()) {
            filters.add(Filters.eq("winner_id", winnerId));
        }

        if (theme != null && !theme.isBlank()) {
            filters.add(Filters.eq("theme", Theme.valueOf(theme)));
        }

        Bson query = filters.isEmpty() ? new Document() : Filters.and(filters);

        PanacheQuery<Challenge> panacheQuery = find(query).page(Page.of(page, size));
        return new PagedEntity<Challenge>().of(panacheQuery);
    }
}
