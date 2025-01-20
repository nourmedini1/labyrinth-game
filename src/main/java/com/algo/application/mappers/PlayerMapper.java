package com.algo.application.mappers;

import com.algo.application.models.CreatePlayerRequest;
import com.algo.application.models.PlayerResponse;
import com.algo.domain.common.utils.PagedEntity;
import com.algo.domain.entities.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.bson.types.ObjectId;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    @Mapping(target = "id", expression = "java(new ObjectId())")
    @Mapping(target = "score", constant = "0")
    Player createPlayerRequestToPlayer(CreatePlayerRequest createPlayerRequest);

    PlayerResponse playerToPlayerResponse(Player player);

    PagedEntity<PlayerResponse> pagedEntityPlayerToPagedEntityPlayerResponse(PagedEntity<Player> pagedEntity);
}
