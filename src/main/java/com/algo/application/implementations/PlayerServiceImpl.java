package com.algo.application.implementations;

import com.algo.application.mappers.PlayerMapper;
import com.algo.application.models.CreatePlayerRequest;
import com.algo.application.models.PlayerResponse;
import com.algo.application.models.UpdatePlayerRequest;
import com.algo.application.services.PlayerService;
import com.algo.domain.common.utils.PagedEntity;
import com.algo.domain.entities.Player;
import com.algo.domain.repositories.PlayerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.Optional;

@ApplicationScoped
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    @Inject
    PlayerRepository playerRepository;

    @Override
    public PlayerResponse createPlayer(CreatePlayerRequest createPlayerRequest) {
        Player player = PlayerMapper.INSTANCE.createPlayerRequestToPlayer(createPlayerRequest);
        playerRepository.createPlayer(player);
        return PlayerMapper.INSTANCE.playerToPlayerResponse(player);
    }

    @Override
    public PlayerResponse getPlayer(String id) {
        Optional<Player> optionalPlayer = playerRepository.findByIdOptional(new ObjectId(id));
        Player player = verifyPlayerExists(optionalPlayer);
        return PlayerMapper.INSTANCE.playerToPlayerResponse(player);
    }

    @Override
    public PlayerResponse getPlayerByName(String name) {
        Optional<Player> optionalPlayer = playerRepository.findPlayerByName(name);
        Player player = verifyPlayerExists(optionalPlayer);
        return PlayerMapper.INSTANCE.playerToPlayerResponse(player);
    }

    @Override
    public PlayerResponse updatePlayer(String id, UpdatePlayerRequest updatePlayerRequest) {
        Optional<Player> optionalPlayer = playerRepository.findByIdOptional(new ObjectId(id));
        Player player = verifyPlayerExists(optionalPlayer);
        if (updatePlayerRequest.getName() != null) {
            player.setName(updatePlayerRequest.getName());
        }
        if (updatePlayerRequest.getScore() != player.getScore()) {
            player.setScore(updatePlayerRequest.getScore());
        }
        playerRepository.updatePlayer(player);
        return PlayerMapper.INSTANCE.playerToPlayerResponse(player);
    }

    @Override
    public void deletePlayer(String id) {
        Optional<Player> optionalPlayer = playerRepository.findByIdOptional(new ObjectId(id));
        Player player = verifyPlayerExists(optionalPlayer);
        playerRepository.deletePlayer(player.getId());
    }

    @Override
    public PagedEntity<PlayerResponse> getPlayersSortedByScore(int page, int size) {
        PagedEntity<Player> pagedEntity = playerRepository.search(page, size);
        return PlayerMapper.INSTANCE.pagedEntityPlayerToPagedEntityPlayerResponse(pagedEntity);
    }

    private Player verifyPlayerExists(Optional<Player> player) {
        if (player.isEmpty()) {
            throw new BadRequestException("No player found with this id");
        }
        return player.get();
    }


}
