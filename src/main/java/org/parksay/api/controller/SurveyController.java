package org.parksay.api.controller;


import org.parksay.api.dto.*;
import org.parksay.core.entity.SurveyRoot;
import org.parksay.core.service.SurveyRootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    @Autowired
    SurveyRootService surveyRootService;



    @GetMapping("/{id}")
    public ResponseEntity<GetSurveyRootResponse> getSurvey(@PathVariable Long id) {
        SurveyRoot surveyRoot = surveyRootService.findById(id);
        GetSurveyRootResponse response = new GetSurveyRootResponse(surveyRoot);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<CreateSurveyRootResponse> createSurvey(@RequestBody CreateSurveyRootRequest request) {
        SurveyRoot surveyRoot = request.convertToSurveyRoot();
        SurveyRoot saved = surveyRootService.save(surveyRoot);
        CreateSurveyRootResponse response = new CreateSurveyRootResponse(saved);
        return ResponseEntity.ok(response);
    }
//    {
//        "title": "test title1",
//            "desc": "test desc1",
//            "surveyItemList": [
//            {"title": "test title3", "desc": "test title3", "type": "SHORT_TEXT", "isRequired": "Y"}
//            , {"title": "test title4", "desc": "test title4", "type": "LONG_TEXT", "isRequired": "N"}
//            , {"title": "test title5", "desc": "test title5", "type": "SINGLE_CHOICE", "isRequired": "N"
//                , "itemOptionList": [ {"desc":"hello1"}, {"desc":"world1"} ]
//            }
//            , {"title": "test title6", "desc": "test title6", "type": "MULTIPLE_CHOICE", "isRequired": "N"
//                , "itemOptionList": [ {"desc":"hello2"}, {"desc":"world2"} ]
//            }
//        ]
//    }

    @PatchMapping
    public ResponseEntity<ModifySurveyRootResponse> modifySurveyRoot(@RequestBody ModifySurveyRootRequest request) {
        SurveyRoot surveyRoot = request.convertToSurveyRoot();
        SurveyRoot modified = surveyRootService.modifySurveyRoot(surveyRoot);
        ModifySurveyRootResponse response = new ModifySurveyRootResponse(modified);
        return ResponseEntity.ok(response);
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
