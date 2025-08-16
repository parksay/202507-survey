package org.parksay.api.controller;


import org.parksay.api.dto.CreateSurveyRootRequest;
import org.parksay.api.dto.CreateSurveyRootResponse;
import org.parksay.api.dto.ModifySurveyRootRequest;
import org.parksay.api.dto.ModifySurveyRootResponse;
import org.parksay.core.entity.SurveyRoot;
import org.parksay.core.service.SurveyRootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    @Autowired
    SurveyRootService surveyRootService;

    @PostMapping
    public CreateSurveyRootResponse createSurvey(@RequestBody CreateSurveyRootRequest request) {
        SurveyRoot surveyRoot = request.convertToSurveyRoot();
        SurveyRoot saved = surveyRootService.save(surveyRoot);
        return new CreateSurveyRootResponse(saved);
    }
//    {
//        "title": "test title1",
//            "desc": "test desc1",
//            "surveyItemList": [
//            {"title": "test title3", "desc": "test title3", "type": "SHORT_TEXT", "isRequired": "Y"}
//            , {"title": "test title4", "desc": "test title4", "type": "LONG_TEXT", "isRequired": "N"}
//            , {"title": "test title5", "desc": "test title5", "type": "SHORT_TEXT", "isRequired": "N"}
//        ]
//    }

    @PatchMapping
    public ModifySurveyRootResponse modifySurveyRoot(@RequestBody ModifySurveyRootRequest request) {
        SurveyRoot surveyRoot = request.convertToSurveyRoot();
        SurveyRoot modified = surveyRootService.modifySurveyRoot(surveyRoot);
        return new ModifySurveyRootResponse(modified);
    }
//    {
//        "id": "1",
//            "title": "test title2",
//            "desc": "test desc2",
//            "surveyItemList": [
//            {"title": "test title6", "desc": "test title6", "type": "SHORT_TEXT", "isRequired": "N"}
//            , {"title": "test title7", "desc": "test title7", "type": "LONG_TEXT", "isRequired": "Y"}
//            , {"title": "test title8", "desc": "test title8", "type": "SHORT_TEXT", "isRequired": "Y"}
//        ]
//    }

}
