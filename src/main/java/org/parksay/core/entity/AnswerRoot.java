package org.parksay.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "answer_root")
public class AnswerRoot extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "seq_answer_root")
    private Long id;

    @ManyToOne
    @JoinColumn(name="seq_survey_root", nullable = false)
    SurveyRoot surveyRoot;

    @OneToMany(mappedBy = "answerRoot", cascade = CascadeType.ALL)
    List<AnswerItemBase> answerItemList;
}
