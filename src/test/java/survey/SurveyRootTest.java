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
import java.util.Optional;

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
        createTestSurveyItemList(surveyRoot);
        //
        surveyRootService.save(surveyRoot);
        SurveyItem surveyItemOpt = surveyRoot.getSurveyItemList().stream()
                .filter(item -> item.getType() == SurveyItemType.SINGLE_CHOICE)
                .findFirst().get();
        //
        Assertions.assertNotNull(surveyRoot.getId());
        Assertions.assertNotNull(surveyRoot.getSurveyItemList().get(0).getId());
        Assertions.assertNotNull(surveyItemOpt.getItemOptionList().get(1).getId());
    }

    private void createTestSurveyItemList(SurveyRoot surveyRoot) {
        surveyRoot.addSurveyItem(createTestShortItem());
        surveyRoot.addSurveyItem(createTestLongItem());
        surveyRoot.addSurveyItem(createTestSingleItem());
        surveyRoot.addSurveyItem(createTestMultiItem());
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
        testSingleItem.addItemOption(createTestItemOption("single_opt1"));
        testSingleItem.addItemOption(createTestItemOption("single_opt2"));
        return testSingleItem;
    }


    private SurveyItem createTestMultiItem() {
        SurveyItem testMultiItem = new SurveyItem();
        testMultiItem.setDesc("test_short");
        testMultiItem.setType(SurveyItemType.MULTIPLE_CHOICE);
        testMultiItem.addItemOption(createTestItemOption("multi_opt1"));
        testMultiItem.addItemOption(createTestItemOption("multi_opt2"));
        testMultiItem.addItemOption(createTestItemOption("multi_opt3"));
        return testMultiItem;
    }

    private ItemOption createTestItemOption(String desc) {
        ItemOption itemOption = new ItemOption();
        itemOption.setDesc(desc);
        return itemOption;
    }

}
