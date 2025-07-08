package org.parksay.core.entity;

import jakarta.persistence.*;

@Entity(name="item_option")
public class ItemOption extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="seq_item_option")
    private Long id;

    @Column(nullable = false)
    private String ansVal;

    @ManyToOne
    @JoinColumn(name="seq_survey_item")
    private SurveyItem surveyItem;

}
