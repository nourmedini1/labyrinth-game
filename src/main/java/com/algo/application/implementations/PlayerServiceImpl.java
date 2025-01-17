package com.algo.application.implementations;

import com.algo.application.models.CreatePlayerRequest;
import com.algo.application.models.PlayerResponse;
import com.algo.application.models.UpdatePlayerRequest;
import com.algo.application.services.PlayerService;
import com.algo.domain.common.utils.PagedEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class PlayerServiceImpl implements PlayerService {
    @Override
    public PlayerResponse createPlayer(CreatePlayerRequest createPlayerRequest) {
        return null;
    }

    @Override
    public PlayerResponse getPlayer(String id) {
        return null;
    }

    @Override
    public PlayerResponse updatePlayer(UpdatePlayerRequest updatePlayerRequest) {
        return null;
    }

    @Override
    public void deletePlayer(String id) {

    }

    @Override
    public PagedEntity<PlayerResponse> getPlayersSortedByScore(int page, int size) {
        return null;
    }
}
