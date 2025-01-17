package com.algo.application.services;

import com.algo.application.models.CreatePlayerRequest;
import com.algo.application.models.PlayerResponse;
import com.algo.application.models.UpdatePlayerRequest;
import com.algo.domain.common.utils.PagedEntity;


public interface PlayerService {

    PlayerResponse createPlayer(CreatePlayerRequest createPlayerRequest);

    PlayerResponse getPlayer(String id);

    PlayerResponse updatePlayer(UpdatePlayerRequest updatePlayerRequest);

    void deletePlayer(String id);

    PagedEntity<PlayerResponse> getPlayersSortedByScore(int page, int size);
}
