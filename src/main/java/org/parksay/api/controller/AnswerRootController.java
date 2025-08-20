package org.parksay.api.controller;

import org.parksay.api.dto.CreateAnswerRootRequest;
import org.parksay.api.dto.CreateAnswerRootResponse;
import org.parksay.api.dto.GetAnswerRootResponse;
import org.parksay.core.entity.AnswerRoot;
import org.parksay.core.service.AnswerRootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
public class AnswerRootController {

    @Autowired
    AnswerRootService answerRootService;

    @PostMapping
    public ResponseEntity<CreateAnswerRootResponse> createAnswerRoot(@RequestBody CreateAnswerRootRequest request) {
        AnswerRoot converted = request.convertToAnswerRoot();
        AnswerRoot saved = answerRootService.save(converted);
        CreateAnswerRootResponse response = new CreateAnswerRootResponse(saved);
        return ResponseEntity.ok(response);
    }

//    {
//        "surveyRootId": "1",
//            "answerItemList": [
//            {
//                "surveyItemId": "1",
//                    "type": "SHORT_TEXT",
//                    "txtVal": "hello world answer1"
//            },
//            {
//                "surveyItemId": "2",
//                    "type": "LONG_TEXT",
//                    "txtVal": "hello world answer2"
//
//            },
//            {
//                "surveyItemId": "3",
//                    "type": "SINGLE_CHOICE",
//                    "itemOptionId": "2"
//
//            },
//            {
//                "surveyItemId": "4",
//                    "type": "MULTIPLE_CHOICE",
//                    "itemOptionId": "3"
//            }
//        ]
//    }


    @GetMapping("/{id}")
    public ResponseEntity<GetAnswerRootResponse> getAnswerRoot(@PathVariable Long id) {
        AnswerRoot answerRoot = answerRootService.findById(id);
        GetAnswerRootResponse response = new GetAnswerRootResponse(answerRoot);
        return ResponseEntity.ok(response);
    }

}
