package org.parksay.core.entity;

import jakarta.persistence.*;

@Entity
public class AnswerItemEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="seq_answer_item")
    private Long id;

    @ManyToOne
    @JoinColumn(name="seq_survey_item")
    @Column(nullable = false)
    private SurveyItemEntity surveyItem;

    @ManyToOne
    @JoinColumn(name="seq_item_option")
    @Column(nullable = true)    // 주관식이라면 null일 수 있음
    private ItemOptionEntity itemOption;

    @Column(nullable = true)    // 객관식이라면 null일 수 있음
    private String txtVal;

}
