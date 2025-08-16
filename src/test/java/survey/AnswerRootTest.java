package survey;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.parksay.Main;
import org.parksay.core.entity.*;
import org.parksay.core.service.AnswerRootService;
import org.parksay.core.service.SurveyGroupService;
import org.parksay.core.service.SurveyRootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = Main.class)
@Transactional
public class AnswerRootTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    SurveyRootService surveyRootService;

    @Autowired
    SurveyGroupService surveyGroupService;

    @Autowired
    AnswerRootService answerRootService;

    private static SurveyGroup testSurveyGroup;

    @Test
    public void contextLoads() {
        // smoke test
        System.out.println("hello world!");
    }

    @BeforeAll
    public static void beforeAll () {
        SurveyRoot surveyRoot = SurveyTestFactory.createSurveyRoot("answer test-1", "survey1");
        SurveyTestFactory.putItemsSurveyRoot(surveyRoot);
        testSurveyGroup = new SurveyGroup();
        surveyRoot.changeSurveyGroup(testSurveyGroup);
   }

    @Test
    public void createAnswerRootTest() {
        //
        SurveyRoot testSurveyRoot2 = SurveyTestFactory.createSurveyRoot("answer test-2", "survey2");
        testSurveyRoot2.addSurveyItem(SurveyTestFactory.createTextItem(SurveyItemType.SHORT_TEXT, SurveyTestFactory.strTitleShort, SurveyTestFactory.strDescShort, ValueYN.N));
        testSurveyRoot2.addSurveyItem(SurveyTestFactory.createOptItem(SurveyItemType.MULTIPLE_CHOICE, SurveyTestFactory.strTitleMulti, SurveyTestFactory.strDescMulti, ValueYN.N, List.of(SurveyTestFactory.strOptMulti1, SurveyTestFactory.strOptMulti2, SurveyTestFactory.strOptMulti3)));
        SurveyGroup surveyGroup2 = new SurveyGroup();
        surveyGroup2.addSurveyRoot(testSurveyRoot2);
        surveyGroupService.save(surveyGroup2);

        //
        String testAnswerTxt = "answer root test1";
        SurveyRoot savedSurveyRoot = surveyRootService.findLatestSurveyRootBySurveyGroupId(surveyGroup2.getId());
        AnswerRoot answerRoot = AnswerTestFactory.createAnswerRoot(savedSurveyRoot);
        SurveyItem surveyItemText = SurveyTestFactory.findItemByType(savedSurveyRoot, SurveyItemType.SHORT_TEXT);
        AnswerTestFactory.createAnswerItemText(answerRoot, surveyItemText, testAnswerTxt);
        SurveyItem surveyItemOpt = SurveyTestFactory.findItemByType(savedSurveyRoot, SurveyItemType.MULTIPLE_CHOICE);
        ItemOption testAnswerOpt = surveyItemOpt.getItemOptionList().get(1);
        AnswerTestFactory.createAnswerItemOpt(answerRoot, surveyItemOpt, testAnswerOpt);
        answerRootService.save(answerRoot);
        Long testAnswerOptId = testAnswerOpt.getId();

        //
        entityManager.flush();
        entityManager.clear();
        AnswerRoot savedAnswerRoot = answerRootService.findById(answerRoot.getId());
        Assertions.assertNotNull(savedAnswerRoot.getId());
        Assertions.assertTrue(savedAnswerRoot.getAnswerItemList().size() > 0);
        AnswerItemText savedAnswerItemText = (AnswerItemText) AnswerTestFactory.findItemByType(savedAnswerRoot, SurveyItemType.SHORT_TEXT);
        Assertions.assertEquals(testAnswerTxt, savedAnswerItemText.getTxtVal());
        AnswerItemOpt savedAnswerItemOpt = (AnswerItemOpt) AnswerTestFactory.findItemByType(savedAnswerRoot, SurveyItemType.MULTIPLE_CHOICE);
        Assertions.assertEquals(testAnswerOptId, savedAnswerItemOpt.getItemOption().getId());
    }


    @Test
    public void readAnswerRootTest() {
        //
        SurveyRoot surveyRoot = SurveyTestFactory.createSurveyRoot("title_ReadAnswerRootTest", "dsc_ReadAnswerRootTest");
        SurveyTestFactory.putItemsSurveyRoot(surveyRoot);
        SurveyGroup surveyGroup = new SurveyGroup();
        surveyRoot.changeSurveyGroup(surveyGroup);
        surveyGroupService.save(surveyGroup);
        AnswerRoot answerRoot = AnswerTestFactory.createAnswerRoot(surveyRoot);
        String textShort = "read answer test short";
        String textLong = "read answer test long";
        ItemOption optSingle = surveyRoot.getSurveyItemList().get(2).getItemOptionList().get(0);
        ItemOption optMulti = surveyRoot.getSurveyItemList().get(3).getItemOptionList().get(0);
        AnswerTestFactory.createAnswerItemText(answerRoot, SurveyTestFactory.findItemByType(surveyRoot, SurveyItemType.SHORT_TEXT), textShort);
        AnswerTestFactory.createAnswerItemText(answerRoot, SurveyTestFactory.findItemByType(surveyRoot, SurveyItemType.LONG_TEXT), textLong);
        AnswerTestFactory.createAnswerItemOpt(answerRoot, SurveyTestFactory.findItemByType(surveyRoot, SurveyItemType.SINGLE_CHOICE), optSingle);
        AnswerTestFactory.createAnswerItemOpt(answerRoot, SurveyTestFactory.findItemByType(surveyRoot, SurveyItemType.MULTIPLE_CHOICE), optMulti);
        answerRootService.save(answerRoot);

        //
        entityManager.flush();
        entityManager.clear();
        List<AnswerRoot> answerRootList = answerRootService.findBySurveyRootId(surveyRoot.getId());

        //
        Assertions.assertTrue(answerRootList.size() == 1);
        Assertions.assertEquals(answerRootList.get(0).getId(), answerRoot.getId());
        Assertions.assertEquals(answerRootList.get(0).getSurveyRoot().getId(), surveyRoot.getId());
        Assertions.assertEquals(((AnswerItemText)(AnswerTestFactory.findItemByType(answerRootList.get(0), SurveyItemType.SHORT_TEXT))).getTxtVal(), textShort);
        Assertions.assertEquals(((AnswerItemText)(AnswerTestFactory.findItemByType(answerRootList.get(0), SurveyItemType.LONG_TEXT))).getTxtVal(), textLong);
        Assertions.assertEquals(((AnswerItemOpt)(AnswerTestFactory.findItemByType(answerRootList.get(0), SurveyItemType.SINGLE_CHOICE))).getItemOption().getId(), optSingle.getId());
        Assertions.assertEquals(((AnswerItemOpt)(AnswerTestFactory.findItemByType(answerRootList.get(0), SurveyItemType.MULTIPLE_CHOICE))).getItemOption().getId(), optMulti.getId());


    }
}
