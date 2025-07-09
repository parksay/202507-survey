package survey;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.parksay.Main;
import org.parksay.core.entity.ItemOption;
import org.parksay.core.entity.SurveyItem;
import org.parksay.core.entity.SurveyItemType;
import org.parksay.core.entity.SurveyRoot;
import org.parksay.core.service.SurveyRootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = Main.class)
public class SurveyRootTest {

    @Autowired
    SurveyRootService surveyRootService;


    @Test
    public void contextLoads() {
        System.out.println("hello world!");
    }

    @Test
    public void registSurveyRootTest() {
        //
        SurveyRoot surveyRoot = new SurveyRoot();
        surveyRoot.setSurveyItemList(createTestSurveyItemList());
        //
        surveyRootService.save(surveyRoot);
        //
        Assertions.assertNotNull(surveyRoot.getId());
        Assertions.assertNotNull(surveyRoot.getSurveyItemList().get(0).getId());
        Assertions.assertNotNull(surveyRoot.getSurveyItemList().get(3).getItemOptionList().get(1).getId());
    }

    private List<SurveyItem> createTestSurveyItemList() {
        List<SurveyItem> testSurveyItemList = new ArrayList<>();
        testSurveyItemList.add(createTestShortItem());
        testSurveyItemList.add(createTestLongItem());
        testSurveyItemList.add(createTestSingleItem());
        testSurveyItemList.add(createTestMultiItem());
        return testSurveyItemList;
    }

    private SurveyItem createTestShortItem() {
        SurveyItem testShortItem = new SurveyItem();
        testShortItem.setDesc("test_short");
        testShortItem.setType(SurveyItemType.SHORT_TEXT);
        return testShortItem;
    }


    private SurveyItem createTestLongItem() {
        SurveyItem testLongItem = new SurveyItem();
        testLongItem.setDesc("test_long");
        testLongItem.setType(SurveyItemType.LONG_TEXT);
        return testLongItem;
    }


    private SurveyItem createTestSingleItem() {
        SurveyItem testSingleItem = new SurveyItem();
        testSingleItem.setDesc("test_short");
        testSingleItem.setType(SurveyItemType.SINGLE_CHOICE);
        testSingleItem.getItemOptionList().add(createTestItemOption("single_opt1"));
        testSingleItem.getItemOptionList().add(createTestItemOption("single_opt2"));
        return testSingleItem;
    }


    private SurveyItem createTestMultiItem() {
        SurveyItem testMultiItem = new SurveyItem();
        testMultiItem.setDesc("test_short");
        testMultiItem.setType(SurveyItemType.MULTIPLE_CHOICE);
        testMultiItem.getItemOptionList().add(createTestItemOption("multi_opt1"));
        testMultiItem.getItemOptionList().add(createTestItemOption("multi_opt2"));
        testMultiItem.getItemOptionList().add(createTestItemOption("multi_opt3"));
        return testMultiItem;
    }

    private ItemOption createTestItemOption(String desc) {
        ItemOption itemOption = new ItemOption();
        itemOption.setDesc(desc);
        return itemOption;
    }

}
