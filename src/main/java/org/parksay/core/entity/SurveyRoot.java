package org.parksay.core.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "survey_root")
public class SurveyRoot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_survey_root")
    private Long id;

    private String title;
    private String desc;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "surveyRoot", cascade = CascadeType.ALL)
    private List<SurveyItem> surveyItemList = new ArrayList<>();


    public void addSurveyItem(SurveyItem surveyItem) {
        if(!this.surveyItemList.contains(surveyItem)) {
            this.surveyItemList.add(surveyItem);
        }
        if(surveyItem.getSurveyRoot() == this) {
            return;
        }
        if(surveyItem.getSurveyRoot() != null) {
            surveyItem.getSurveyRoot().getSurveyItemList().remove(surveyItem);
        }
        surveyItem.setSurveyRoot(this);
    }
}
