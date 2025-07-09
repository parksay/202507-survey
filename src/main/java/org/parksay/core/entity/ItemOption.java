package org.parksay.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name="item_option")
public class ItemOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="seq_item_option")
    private Long id;

    @Column(nullable = false)
    private String desc;

    @ManyToOne
    @JoinColumn(name="seq_survey_item", nullable = false)
    private SurveyItem surveyItem;

}
