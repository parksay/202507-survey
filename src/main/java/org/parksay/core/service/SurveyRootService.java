package org.parksay.core.service;


import org.parksay.core.entity.SurveyRoot;
import org.parksay.infra.repository.SurveyRootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyRootService {

    @Autowired
    SurveyRootRepository surveyRootRepository;
    public SurveyRoot save(SurveyRoot surveyRoot) {
        return surveyRootRepository.save(surveyRoot);
    }


}
