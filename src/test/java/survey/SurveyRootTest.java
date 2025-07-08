package survey;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.parksay.Main;
import org.parksay.core.entity.SurveyItem;
import org.parksay.core.entity.SurveyItemType;
import org.parksay.core.service.SurveyRootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = Main.class)
public class SurveyRootTest {

    @Autowired
    SurveyRootService surveyRootService;

    private static List<SurveyItem> testSurveyItemList;

    @BeforeAll
    public void beforeall() {
        this.testSurveyItemList = createTestSurveyItemList();
    }


    @Test
    public void contextLoads() {
        System.out.println("hello world!");
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
        return testSingleItem;
    }


    private SurveyItem createTestMultiItem() {
        SurveyItem testMultiItem = new SurveyItem();
        testMultiItem.setDesc("test_short");
        testMultiItem.setType(SurveyItemType.MULTIPLE_CHOICE);
        return testMultiItem;
    }


}
