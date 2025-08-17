package org.parksay.api.dto;

import lombok.Getter;
import org.parksay.core.entity.SurveyRoot;

@Getter
public class GetSurveyRootResponse {

    SurveyRoot surveyRoot;

    public GetSurveyRootResponse(SurveyRoot surveyRoot) {
        this.surveyRoot = surveyRoot;
    }
}
