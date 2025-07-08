package org.parksay.core.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "survey_root")
public class SurveyRoot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_survey_root")
    private Long id;

    private String title;
    private String desc;

    @OneToMany(mappedBy = "surveyRoot", cascade = CascadeType.ALL)
    private List<SurveyItem> surveyItemList = new ArrayList<>();
}
