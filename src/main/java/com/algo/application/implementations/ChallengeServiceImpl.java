package com.algo.application.implementations;

import com.algo.application.mappers.ChallengeMapper;
import com.algo.application.models.*;

import com.algo.application.services.ChallengeService;
import com.algo.application.services.LabyrinthService;
import com.algo.domain.common.ChallengeStatus;
import com.algo.domain.common.utils.PagedEntity;
import com.algo.domain.entities.Challenge;
import com.algo.domain.entities.Labyrinth;
import com.algo.domain.repositories.ChallengeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.Optional;


@ApplicationScoped
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {
    @Inject
    ChallengeRepository challengeRepository;

    @Inject
    LabyrinthService labyrinthService;

    @Override
    public ChallengeResponse createChallenge(CreateChallengeRequest createChallengeRequest) throws Exception {
        Challenge challenge = ChallengeMapper.INSTANCE.createChallengeRequestToChallenge(createChallengeRequest);
        Labyrinth labyrinth = labyrinthService.createLabyrinth(String.valueOf(challenge.getTheme()), challenge.getDifficultyLevel());
        labyrinthService.persistLabyrinth(labyrinth);
        challenge.setLabyrinthId(labyrinth.getId().toHexString());
        challengeRepository.createChallenge(challenge);
        return ChallengeMapper.INSTANCE.challengeToChallengeResponse(challenge);
    }

    @Override
    public PagedEntity<ChallengeResponse> getChallenges(int page, int size, String challengerId, String challengedId, String status, int difficultyLevel, String winnerId, String theme) {
        PagedEntity<Challenge> challenges = challengeRepository.searchChallenges(page, size, challengerId, challengedId, status, difficultyLevel, winnerId, theme);
        return ChallengeMapper.INSTANCE.pagedEntityChallengeToPagedEntityChallengeResponse(challenges);
    }

    @Override
    public void deleteChallenge(String id) {
        Challenge challenge = verifyChallengeExists(challengeRepository.findByIdOptional(new ObjectId(id)));
        labyrinthService.deleteLabyrinth(challenge.getLabyrinthId());
        challengeRepository.deleteChallenge(new ObjectId(id));
    }

    @Override
    public ChallengeResponse acceptChallenge(String id) {
        Optional<Challenge> optionalChallenge = challengeRepository.findByIdOptional(new ObjectId(id));
        Challenge challenge = verifyChallengeExists(optionalChallenge);
        if (!challenge.getStatus().equals(ChallengeStatus.PENDING)) {
            throw new BadRequestException("Challenge is not pending");
        }
        challenge.setStatus(ChallengeStatus.ACCEPTED);
        challengeRepository.updateChallenge(challenge);
        return ChallengeMapper.INSTANCE.challengeToChallengeResponse(challenge);
    }

    @Override
    public void declineChallenge(String id) {
        Optional<Challenge> optionalChallenge = challengeRepository.findByIdOptional(new ObjectId(id));
        Challenge challenge = verifyChallengeExists(optionalChallenge);
        if (!challenge.getStatus().equals(ChallengeStatus.PENDING)) {
            throw new BadRequestException("Challenge is not pending");
        }
        challenge.setStatus(ChallengeStatus.DECLINED);
        challengeRepository.updateChallenge(challenge);
    }

    @Override
    public UpdateChallengeResponse updateChallenge(String id, UpdateChallengeRequest updateChallengeRequest) {
        Optional<Challenge> optionalChallenge = challengeRepository.findByIdOptional(new ObjectId(id));
        Challenge challenge = verifyChallengeExists(optionalChallenge);
        UpdateChallengeResponse updateChallengeResponse = new UpdateChallengeResponse();
        if (updateChallengeRequest.getChallengerScore() != null) {
            challenge.setChallengerScore(Integer.parseInt(updateChallengeRequest.getChallengerScore()));
        }
        if (updateChallengeRequest.getChallengedScore() != null) {
            challenge.setChallengedScore(Integer.parseInt(updateChallengeRequest.getChallengedScore()));
        }
        boolean thereIsWinner = challenge.getChallengerScore() != 0 && challenge.getChallengedScore() != 0;
        if (thereIsWinner) {
            boolean challengerIsWinner = challenge.getChallengerScore() > challenge.getChallengedScore();
            if (challengerIsWinner) {
                challenge.setWinnerId(challenge.getChallengerId());
            } else {
                challenge.setWinnerId(challenge.getChallengedId());
            }
            updateChallengeResponse.setMessage("The winner is : " + challenge.getWinnerId());
            challenge.setStatus(ChallengeStatus.FINISHED);
        } else {
            updateChallengeResponse.setMessage("Waiting for the other player to finish the challenge");
        }
        challengeRepository.updateChallenge(challenge);
        return updateChallengeResponse;
    }

    private Challenge verifyChallengeExists(Optional<Challenge> optionalChallenge) {
        if (optionalChallenge.isEmpty()) {
            throw new BadRequestException("Challenge not found");
        }
        return optionalChallenge.get();
    }
}
