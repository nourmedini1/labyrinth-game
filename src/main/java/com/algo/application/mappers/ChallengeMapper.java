package com.algo.application.mappers;



import com.algo.application.models.ChallengeResponse;
import com.algo.application.models.CreateChallengeRequest;
import com.algo.domain.common.utils.PagedEntity;
import com.algo.domain.entities.Challenge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChallengeMapper {

    ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);


    @Mapping(target = "challengerScore", constant = "0")
    @Mapping(target = "challengedScore", constant = "0")
    @Mapping(target = "status", expression = "java(com.algo.domain.common.ChallengeStatus.PENDING)")
    @Mapping(target = "initialLabyrinth", expression = "java(null)")
    @Mapping(target = "winnerId", expression = "java(null)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    Challenge createChallengeRequestToChallenge(CreateChallengeRequest createChallengeRequest);


    ChallengeResponse challengeToChallengeResponse(Challenge challenge);

    PagedEntity<ChallengeResponse> pagedEntityChallengeToPagedEntityChallengeResponse(PagedEntity<Challenge> pagedEntity);
}
