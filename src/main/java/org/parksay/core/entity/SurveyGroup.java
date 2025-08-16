package org.parksay.core.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonManagedReference
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
