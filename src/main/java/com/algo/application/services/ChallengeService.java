package com.algo.application.services;

import com.algo.application.models.ChallengeResponse;
import com.algo.application.models.CreateChallengeRequest;
import com.algo.application.models.GetScoreRequest;
import com.algo.application.models.ScoreResponse;
import com.algo.domain.common.utils.PagedEntity;


public interface ChallengeService {

    ChallengeResponse createChallenge(CreateChallengeRequest createChallengeRequest) throws Exception;

    PagedEntity<ChallengeResponse> getChallenges(int page, int size, String challengerId, String challengedId, String status, int difficultyLevel, String winnerId, String theme);

    void deleteChallenge(String id);

    ChallengeResponse acceptChallenge(String id);

    void declineChallenge(String id);

    ScoreResponse scoreChallenge(String id , GetScoreRequest getScoreRequest);




}
