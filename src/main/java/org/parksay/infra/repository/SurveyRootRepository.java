package org.parksay.infra.repository;

import org.parksay.core.entity.SurveyRoot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRootRepository extends JpaRepository<SurveyRoot, Long> {
    Optional<SurveyRoot> findTopBySurveyGroupIdOrderByVerDesc(Long surveyGroupid);
}
