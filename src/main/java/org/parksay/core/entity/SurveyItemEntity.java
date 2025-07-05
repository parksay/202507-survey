package org.parksay.core.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class SurveyItemEntity extends BaseEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "seq_item")
        private Long id;

        @ManyToOne
        @JoinColumn(name = "seq_survey", nullable = false)
        private SurveyRootEntity surveyRoot;

        private String title;
        private String desc;

        @Column(length=1)
        private String isRequired;

        private SurveyItemType type; 

        @OneToMany(mappedBy = "surveyItem", cascade = CascadeType.ALL)
        private List<ItemOptionEntity> itemOptionList = new ArrayList<>();
}
