package org.parksay.api.dto;

import lombok.Data;
import lombok.Setter;
import org.parksay.core.entity.*;

import java.util.Iterator;
import java.util.List;

@Setter
public class CreateAnswerRootRequest {

    private Long surveyRootId;
    private List<AnswerItemDto> answerItemList;
    public AnswerRoot convertToAnswerRoot() {
        SurveyRoot surveyRoot = new SurveyRoot();
        surveyRoot.setId(surveyRootId);
        AnswerRoot answerRoot = new AnswerRoot();
        answerRoot.changeSurveyRoot(surveyRoot);
        Iterator<AnswerItemDto> iter = answerItemList.iterator();
        while(iter.hasNext()) {
            AnswerItemDto item = iter.next();
            SurveyItem surveyItem = new SurveyItem();
            surveyItem.setId(item.surveyItemId);
            surveyItem.setType(item.type);
            if (item.type == SurveyItemType.SHORT_TEXT || item.type == SurveyItemType.LONG_TEXT) {
                createItemTxt(answerRoot, surveyItem, item);
            } else if(item.type == SurveyItemType.SINGLE_CHOICE || item.type == SurveyItemType.MULTIPLE_CHOICE) {
                createItemOpt(answerRoot, surveyItem, item);
            }
        }
        return answerRoot;
    }

    private AnswerItemOpt createItemOpt(AnswerRoot answerRoot, SurveyItem surveyItem, AnswerItemDto dto) {
        AnswerItemOpt answerItemOpt = new AnswerItemOpt();
        answerItemOpt.changeAnswerRoot(answerRoot);
        answerItemOpt.setSurveyItem(surveyItem);
        ItemOption opt = new ItemOption();
        opt.setId(dto.itemOptionId);
        answerItemOpt.setItemOption(opt);
        return answerItemOpt;
    }

    private AnswerItemText createItemTxt(AnswerRoot answerRoot, SurveyItem surveyItem, AnswerItemDto dto) {
        AnswerItemText answerItemTxt = new AnswerItemText();
        answerItemTxt.changeAnswerRoot(answerRoot);
        answerItemTxt.setSurveyItem(surveyItem);
        answerItemTxt.setTxtVal(dto.txtVal);
        return answerItemTxt;
    }

    @Data
    private static class AnswerItemDto {
        private Long surveyItemId;
        private SurveyItemType type;
        private Long itemOptionId;
        private String txtVal;

    }


}
