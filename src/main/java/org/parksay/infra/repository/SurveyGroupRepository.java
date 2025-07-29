package org.parksay.infra.repository;


import org.parksay.core.entity.SurveyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  SurveyGroupRepository extends JpaRepository<SurveyGroup, Long> {
}
