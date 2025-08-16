package org.parksay.infra.repository;

import org.parksay.core.entity.AnswerRoot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRootRepository extends JpaRepository<AnswerRoot, Long> {

    public List<AnswerRoot> findAllBySurveyRootId(Long surveyRootId);
}
