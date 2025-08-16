package org.parksay.core.service;

import jakarta.persistence.EntityNotFoundException;
import org.parksay.core.entity.AnswerRoot;
import org.parksay.infra.repository.AnswerRootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerRootService {
    @Autowired
    AnswerRootRepository answerRootRepository;


    public AnswerRoot save(AnswerRoot answerRoot) {
        return answerRootRepository.save(answerRoot);
    }

    public AnswerRoot findById(Long id) {
        return answerRootRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<AnswerRoot> findBySurveyRootId(Long surveyRootId) {
        return answerRootRepository.findAllBySurveyRootId(surveyRootId);
    }
}
