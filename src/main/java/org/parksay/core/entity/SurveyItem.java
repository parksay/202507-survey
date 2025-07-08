package org.parksay.core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "survey_item")
@Data
public class SurveyItem extends BaseEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "seq_item")
        private Long id;

        @ManyToOne
        @JoinColumn(name = "seq_survey", nullable = false)
        private SurveyRoot surveyRoot;

        @Column(nullable = false)
        private String desc;

        @Column(length=1)
        private ValueYN isRequired = ValueYN.N;

        @Column(nullable = false)
        private SurveyItemType type;

        @OneToMany(mappedBy = "surveyItem", cascade = CascadeType.ALL)
        private List<ItemOption> itemOptionList = new ArrayList<>();

        // TODO - 연관관계 편의 메소드 넣기
}

