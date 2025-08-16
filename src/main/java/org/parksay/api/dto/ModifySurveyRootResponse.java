package org.parksay.api.dto;

import lombok.Getter;
import org.parksay.core.entity.SurveyRoot;


@Getter
public class ModifySurveyRootResponse {

    private SurveyRoot surveyRoot;

    public ModifySurveyRootResponse(SurveyRoot surveyRoot) {
        this.surveyRoot = surveyRoot;
    }
}
