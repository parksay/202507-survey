package survey;

import org.parksay.core.entity.*;

import java.util.Iterator;
import java.util.List;

public class AnswerTestFactory {


    public static String strAnswTxt = "test answer root text1";

    public static AnswerRoot createAnswerRoot(SurveyRoot surveyRoot) {
        AnswerRoot answerRoot = new AnswerRoot();
        answerRoot.changeSurveyRoot(surveyRoot);
        return answerRoot;
    }

    public static AnswerRoot putItemsAnswerRoot(AnswerRoot answerRoot) {
        SurveyRoot surveyRoot = answerRoot.getSurveyRoot();
        List<SurveyItem> surveyItemList = surveyRoot.getSurveyItemList();
        Iterator<SurveyItem> iter = surveyItemList.iterator();
        while(iter.hasNext()) {
            SurveyItem item = iter.next();
            if(isItemOptional(item)) {
                createAnswerItemOpt(answerRoot, item, item.getItemOptionList().get(0));
            } else {
                createAnswerItemText(answerRoot, item, strAnswTxt);
            }
        }
        return answerRoot;
    }

    public static boolean isItemOptional(SurveyItem surveyItem) {
        if(surveyItem.getType() == SurveyItemType.SINGLE_CHOICE || surveyItem.getType() == SurveyItemType.MULTIPLE_CHOICE)
            return true;
        return false;
    }

    public static AnswerItemOpt createAnswerItemOpt(AnswerRoot answerRoot, SurveyItem surveyItem, ItemOption answerOpt) {
        AnswerItemOpt answerItem = new AnswerItemOpt();
        answerItem.changeAnswerRoot(answerRoot);
        answerItem.setSurveyItem(surveyItem);
        answerItem.setItemOption(answerOpt);
        return answerItem;
    }

    public static AnswerItemText createAnswerItemText(AnswerRoot answerRoot, SurveyItem surveyItem, String answerTxt) {
        AnswerItemText answerItem = new AnswerItemText();
        answerItem.changeAnswerRoot(answerRoot);
        answerItem.setSurveyItem(surveyItem);
        answerItem.setTxtVal(answerTxt);
        return answerItem;
    }

    public static AnswerItemBase findItemByType(AnswerRoot answerRoot, SurveyItemType surveyItemType) {
        List<AnswerItemBase> answerItemList = answerRoot.getAnswerItemList();
        Iterator<AnswerItemBase> iter = answerItemList.iterator();
        while(iter.hasNext()) {
            AnswerItemBase item = iter.next();
            if(item.getSurveyItem().getType() == surveyItemType) {
                return item;
            }
        }
        return null;
    }
}
