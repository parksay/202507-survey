package org.parksay.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "survey_root")
@Immutable
public class SurveyRoot extends BaseEntity implements VersionCloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq_survey_root")
    private Long id;

    private String title;
    private String desc;

    @Setter(AccessLevel.PRIVATE)
    private int ver = 1;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(nullable = false, name = "seq_survey_group")
    @JsonBackReference
    private SurveyGroup surveyGroup;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "surveyRoot", cascade = CascadeType.ALL)
    @JsonManagedReference
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
        surveyItem.changeSurveyRoot(this);
    }

    public void changeSurveyGroup(SurveyGroup surveyGroup) {
        List<SurveyRoot> surveyRootList = surveyGroup.getSurveyRootList();
        if(!surveyRootList.contains(this)) {
            surveyRootList.add(this);
        }
        if(this.surveyGroup != surveyGroup) {
            this.surveyGroup = surveyGroup;
        }
    }

    @Override
    public BaseEntity cloneWithNewVersion(int newVersion) {
        SurveyRoot newSurveyRoot = new SurveyRoot();
        newSurveyRoot.setTitle(this.title);
        newSurveyRoot.setDesc(this.desc);
        newSurveyRoot.setVer(newVersion);
        Iterator<SurveyItem> iterator = this.surveyItemList.iterator();
        while (iterator.hasNext()) {
            SurveyItem item = iterator.next();
            newSurveyRoot.addSurveyItem((SurveyItem)item.cloneWithNewVersion(newVersion));
        }
        return newSurveyRoot;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
