package survey;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.parksay.Main;
import org.parksay.core.entity.*;
import org.parksay.core.service.SurveyGroupService;
import org.parksay.core.service.SurveyRootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest(classes = Main.class)
@Transactional
public class SurveyRootTest {

    @Autowired
    SurveyRootService surveyRootService;
    @Autowired
    SurveyGroupService surveyGroupService;

    @Test
    public void contextLoads() {
        System.out.println("hello world!");
    }


    @Test
    public void createSurveyRootTest() {
        //
        SurveyRoot surveyRoot = new SurveyRoot();
        SurveyTestFactory.putItemsSurveyRoot(surveyRoot);
        SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.addSurveyRoot(surveyRoot);
        //
        surveyGroupService.save(surveyGroup);
        SurveyItem surveyItemOpt = SurveyTestFactory.findItemByType(surveyRoot, SurveyItemType.MULTIPLE_CHOICE);
        //
        Assertions.assertNotNull(surveyRoot.getId());
        Assertions.assertNotNull(surveyRoot.getSurveyItemList().get(0).getId());
        Assertions.assertNotNull(surveyItemOpt.getItemOptionList().get(1).getId());
    }


    @Test
    public void readSurveyRootTest() {
        //
        String testDesc = "hello world! desc";
        String testTitle = "hello world! title";
        String testDescText = "hello world! text";
        String testDescOpt = "hello world! opt";
        String opt1 = "multi_opt1";
        String opt2 = "multi_opt2";
        String opt3 = "multi_opt3";
        ValueYN isRequiredItemText = ValueYN.Y;
        ValueYN isRequiredItemOpt = ValueYN.N;
        SurveyRoot surveyRoot = new SurveyRoot();
        surveyRoot.setDesc(testDesc);
        surveyRoot.setTitle(testTitle);
        surveyRoot.addSurveyItem(SurveyTestFactory.createTextItem(SurveyItemType.LONG_TEXT, testDescText, isRequiredItemText));
        surveyRoot.addSurveyItem(SurveyTestFactory.createOptItem(SurveyItemType.MULTIPLE_CHOICE, testDescOpt, isRequiredItemOpt, List.of(opt1, opt2, opt3)));
        SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.addSurveyRoot(surveyRoot);
        surveyGroupService.save(surveyGroup);
        //
        Long id = surveyRoot.getId();
        SurveyRoot saved = surveyRootService.findById(id);
        //
        Assertions.assertNotNull(saved);
        SurveyItem surveyItemOpt = SurveyTestFactory.findItemByType(saved, SurveyItemType.MULTIPLE_CHOICE);
        SurveyItem surveyItemText = SurveyTestFactory.findItemByType(saved, SurveyItemType.LONG_TEXT);
        Assertions.assertNotNull(surveyItemOpt);
        Assertions.assertNotNull(surveyItemText);
        Assertions.assertNotNull(surveyRoot.getSurveyGroup().getId());
        Assertions.assertEquals(testDesc, saved.getDesc());
        Assertions.assertEquals(testTitle, saved.getTitle());
        Assertions.assertEquals(testDescText, surveyItemText.getDesc());
        Assertions.assertEquals(isRequiredItemText, surveyItemText.getIsRequired());
        Assertions.assertEquals(testDescOpt, surveyItemOpt.getDesc());
        Assertions.assertEquals(isRequiredItemOpt, surveyItemOpt.getIsRequired());
        Assertions.assertEquals(opt1, surveyItemOpt.getItemOptionList().get(0).getDesc());

    }


    // TODO - 설문조사 수정 / 응답 등록 / 응답 조회 테스트 코드 만들기 
}
