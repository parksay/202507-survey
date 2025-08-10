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
        testSurveyRoot2.addSurveyItem(SurveyTestFactory.createTextItem(SurveyItemType.SHORT_TEXT, SurveyTestFactory.strDescShort, ValueYN.N));
        testSurveyRoot2.addSurveyItem(SurveyTestFactory.createOptItem(SurveyItemType.MULTIPLE_CHOICE, SurveyTestFactory.strDescMulti, ValueYN.N, List.of(SurveyTestFactory.strOptMulti1, SurveyTestFactory.strOptMulti2, SurveyTestFactory.strOptMulti3)));
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
        Assertions.assertEquals(testAnswerOptId, savedAnswerItemOpt.getId());
    }

}
