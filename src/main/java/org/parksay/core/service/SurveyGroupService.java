package org.parksay.core.service;


import jakarta.persistence.EntityNotFoundException;
import org.parksay.core.entity.SurveyGroup;
import org.parksay.infra.repository.SurveyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyGroupService {
    @Autowired
    SurveyGroupRepository surveyGroupRepository;


    public SurveyGroup save(SurveyGroup surveyGroup) {
        return surveyGroupRepository.save(surveyGroup);
    }

    public SurveyGroup findById(Long id) {
        return surveyGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
