package com.algo.application.mappers;



import com.algo.application.models.ChallengeResponse;
import com.algo.application.models.CreateChallengeRequest;
import com.algo.domain.common.utils.PagedEntity;
import com.algo.domain.entities.Challenge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.bson.types.ObjectId;
import org.mapstruct.Named;

@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChallengeMapper {

    ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);


    @Mapping(target = "challengerScore", constant = "0")
    @Mapping(target = "challengedScore", constant = "0")
    @Mapping(target = "status", expression = "java(com.algo.domain.common.ChallengeStatus.PENDING)")
    @Mapping(target = "labyrinthId", expression = "java(null)")
    @Mapping(target = "winnerId", expression = "java(null)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(new ObjectId())")
    Challenge createChallengeRequestToChallenge(CreateChallengeRequest createChallengeRequest);

    @Mapping(source = "id", target = "id", qualifiedByName = "mapObjectIdToString")

    @Named("mapObjectIdToString")
    default String mapObjectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }
    @Mapping(source = "id", target = "id", qualifiedByName = "mapObjectIdToString")
    ChallengeResponse challengeToChallengeResponse(Challenge challenge);

    @Mapping(target = "of", ignore = true)
    PagedEntity<ChallengeResponse> pagedEntityChallengeToPagedEntityChallengeResponse(PagedEntity<Challenge> pagedEntity);
}
