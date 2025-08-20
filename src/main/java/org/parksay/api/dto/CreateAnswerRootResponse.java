package org.parksay.api.dto;

import lombok.Getter;
import org.parksay.core.entity.AnswerRoot;

@Getter
public class CreateAnswerRootResponse {

    AnswerRoot answerRoot;

    public CreateAnswerRootResponse(AnswerRoot answerRoot) {
        this.answerRoot = answerRoot;
    }
}
