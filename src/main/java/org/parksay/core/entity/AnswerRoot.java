package org.parksay.core.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

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

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "answerRoot", cascade = CascadeType.ALL)
    List<AnswerItemBase> answerItemList;


    public void addSurveyItem(AnswerItemBase answerItem) {
        if(!this.answerItemList.contains(answerItem)) {
            this.answerItemList.add(answerItem);
        }
        if(answerItem.getAnswerRoot() == this) {
            return;
        }
        if(answerItem.getAnswerRoot() != null) {
            answerItem.getAnswerRoot().getAnswerItemList().remove(answerItem);
        }
        answerItem.setAnswerRoot(this);
    }
}
