package com.algo.application.implementations;

import com.algo.application.models.ChallengeResponse;
import com.algo.application.models.CreateChallengeRequest;
import com.algo.application.models.GetScoreRequest;
import com.algo.application.models.ScoreResponse;
import com.algo.application.services.ChallengeService;
import com.algo.domain.common.utils.PagedEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;


@ApplicationScoped
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {
    @Override
    public ChallengeResponse createChallenge(CreateChallengeRequest createChallengeRequest) {
        return null;
    }

    @Override
    public PagedEntity<ChallengeResponse> getChallenges(int page, int size, String challengerId, String challengedId, String status, int difficultyLevel, String winnerId, String theme) {
        return null;
    }

    @Override
    public void deleteChallenge(String id) {

    }

    @Override
    public ChallengeResponse acceptChallenge(String id) {
        return null;
    }

    @Override
    public void declineChallenge(String id) {

    }

    @Override
    public ScoreResponse scoreChallenge(GetScoreRequest getScoreRequest) {
        return null;
    }
}
