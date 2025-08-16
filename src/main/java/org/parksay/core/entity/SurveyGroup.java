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
@Entity(name = "survey_group")
public class SurveyGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_survey_group")
    private Long id;

    @OneToMany(mappedBy = "surveyGroup", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    List<SurveyRoot> surveyRootList = new ArrayList<>();

    public void addSurveyRoot(SurveyRoot surveyRoot) {
        if(!this.surveyRootList.contains(surveyRoot)) {
            this.surveyRootList.add(surveyRoot);
        }
        if(surveyRoot.getSurveyGroup() == this) {
            return;
        }
        if(surveyRoot.getSurveyGroup() != null) {
            surveyRoot.getSurveyGroup().getSurveyRootList().remove(surveyRoot);
        }
        surveyRoot.changeSurveyGroup(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
