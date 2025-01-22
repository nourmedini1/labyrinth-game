package com.algo.application.services;

import com.algo.application.models.*;
import com.algo.domain.common.utils.PagedEntity;


public interface ChallengeService {

    ChallengeResponse createChallenge(CreateChallengeRequest createChallengeRequest) throws Exception;

    PagedEntity<ChallengeResponse> getChallenges(int page, int size, String challengerId, String challengedId, String status, int difficultyLevel, String winnerId, String theme);

    void deleteChallenge(String id);

    ChallengeResponse acceptChallenge(String id);

    void declineChallenge(String id);

    UpdateChallengeResponse updateChallenge(String id , UpdateChallengeRequest updateChallengeRequest);




}
