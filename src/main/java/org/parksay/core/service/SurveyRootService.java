package org.parksay.core.service;


import jakarta.persistence.EntityNotFoundException;
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

    public SurveyRoot findById(Long id) {
        return surveyRootRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public SurveyRoot modifySurveyRoot(SurveyRoot surveyRootOld) {
        SurveyRoot surveyRootNew = (SurveyRoot)surveyRootOld.cloneWithNewVersion(surveyRootOld.getVer()+1);
        return surveyRootRepository.save(surveyRootNew);
    }

    public SurveyRoot findLatestSurveyRootBySurveyGroupId(Long surveyGroupid) {
        return surveyRootRepository.findTopBySurveyGroupIdOrderByVerDesc(surveyGroupid).orElseThrow(EntityNotFoundException::new);
    }
}
