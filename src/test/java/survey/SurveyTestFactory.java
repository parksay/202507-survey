package survey;

import org.parksay.core.entity.*;

import java.util.ArrayList;
import java.util.List;

public class SurveyTestFactory {

    public static String strDescShort = "test_short";
    public static String strDescLong = "test_long";
    public static String strDescSingle = "test_single";
    public static String strDescMulti = "test_multi";
    public static String strOptSingle1 = "opt_single1";
    public static String strOptSingle2 = "opt_single2";
    public static String strOptSingle3 = "opt_single3";
    public static String strOptMulti1 = "opt_multi1";
    public static String strOptMulti2 = "opt_multi2";
    public static String strOptMulti3 = "opt_multi3";

    public static void putItemsSurveyRoot(SurveyRoot surveyRoot) {
        surveyRoot.addSurveyItem(createTextItem(SurveyItemType.SHORT_TEXT, strDescShort, ValueYN.Y));
        surveyRoot.addSurveyItem(createTextItem(SurveyItemType.LONG_TEXT, strDescLong, ValueYN.N));
        surveyRoot.addSurveyItem(createOptItem(SurveyItemType.SINGLE_CHOICE, strDescSingle, ValueYN.Y, List.of(strOptSingle1, strOptSingle2, strOptSingle3)));
        surveyRoot.addSurveyItem(createOptItem(SurveyItemType.MULTIPLE_CHOICE, strDescMulti, ValueYN.N, List.of(strOptMulti1, strOptMulti2, strOptMulti3)));
    }

    public static SurveyItem createTextItem(SurveyItemType type, String desc, ValueYN isRequired) {
        return createItem(type, desc, isRequired);
    }

    public static SurveyItem createOptItem(SurveyItemType type, String desc, ValueYN isRequired, List<String> optDescStrList) {
        SurveyItem item = createItem(type, desc, isRequired);
        optDescStrList.forEach((ele)->{
            item.addItemOption(createItemOption(ele));
        });
        return item;
    }

    public static SurveyItem createItem(SurveyItemType type, String desc, ValueYN isRequired) {
        SurveyItem item = new SurveyItem();
        item.setType(type);
        item.setDesc(desc);
        item.setIsRequired(isRequired);
        item.setType(type);
        return item;
    }

    public static ItemOption createItemOption(String desc) {
        ItemOption option = new ItemOption();
        option.setDesc(desc);
        return option;
    }

    public static SurveyItem findItemByType(SurveyRoot surveyRoot, SurveyItemType type) {
        return surveyRoot.getSurveyItemList().stream()
                .filter(item -> item.getType() == type)
                .findFirst()
                .orElseThrow();
    }

}

