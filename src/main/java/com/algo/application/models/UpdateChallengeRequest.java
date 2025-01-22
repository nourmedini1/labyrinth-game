package com.algo.application.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.RestForm;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(name = "UpdateChallengeRequest", description = "Request to update a challenge")
public class UpdateChallengeRequest {

    @RestForm
    String challengerScore;

    @RestForm
    String challengedScore;

}
