package org.parksay.api.dto;


import lombok.Getter;
import org.parksay.core.entity.AnswerRoot;

@Getter
public class GetAnswerRootResponse {

    AnswerRoot answerRoot;
    public GetAnswerRootResponse(AnswerRoot answerRoot) {
        this.answerRoot = answerRoot;
    }
}
