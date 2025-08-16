package org.parksay.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.parksay.core.entity.SurveyGroup;
import org.parksay.core.entity.SurveyItem;
import org.parksay.core.entity.SurveyRoot;

import java.util.Iterator;
import java.util.List;

@Setter
public class ModifySurveyRootRequest {


    private Long id;
    private String title;
    private String desc;
    private List<SurveyItem> surveyItemList;

    public SurveyRoot convertToSurveyRoot() {
        SurveyRoot surveyRoot = new SurveyRoot();
        surveyRoot.setId(id);
        surveyRoot.setTitle(title);
        surveyRoot.setDesc(desc);
        Iterator<SurveyItem> iter = surveyItemList.iterator();
        while(iter.hasNext()) {
            SurveyItem item = iter.next();
            surveyRoot.addSurveyItem(item);
        }
        return surveyRoot;
    }
}
