package survey;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.parksay.Main;
import org.parksay.core.entity.*;
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
    EntityManager entityManager;


    @Test
    public void contextLoads() {
        System.out.println("hello world!");
    }


    @Test
    public void createSurveyRootTest() {
        //
        SurveyRoot surveyRoot = new SurveyRoot();
        SurveyTestFactory.putItemsSurveyRoot(surveyRoot);
        //
        surveyRootService.save(surveyRoot);
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
        String testTitleText = "hello world? text";
        String testTitleOpt = "hello world? opt";
        String testDescText = "hello world! text";
        String testDescOpt = "hello world! opt";
        String opt1 = "multi_opt1";
        String opt2 = "multi_opt2";
        String opt3 = "multi_opt3";
        ValueYN isRequiredItemText = ValueYN.Y;
        ValueYN isRequiredItemOpt = ValueYN.N;
        SurveyRoot surveyRoot = SurveyTestFactory.createSurveyRoot(testTitle, testDesc);
        surveyRoot.addSurveyItem(SurveyTestFactory.createTextItem(SurveyItemType.LONG_TEXT, testTitleText, testDescText, isRequiredItemText));
        surveyRoot.addSurveyItem(SurveyTestFactory.createOptItem(SurveyItemType.MULTIPLE_CHOICE, testTitleOpt, testDescOpt, isRequiredItemOpt, List.of(opt1, opt2, opt3)));
        surveyRootService.save(surveyRoot);
        //
        entityManager.flush();
        entityManager.clear();
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
        Assertions.assertEquals(testTitleText, surveyItemText.getTitle());
        Assertions.assertEquals(testDescText, surveyItemText.getDesc());
        Assertions.assertEquals(isRequiredItemText, surveyItemText.getIsRequired());
        Assertions.assertEquals(testTitleOpt, surveyItemOpt.getTitle());
        Assertions.assertEquals(testDescOpt, surveyItemOpt.getDesc());
        Assertions.assertEquals(isRequiredItemOpt, surveyItemOpt.getIsRequired());
        Assertions.assertEquals(opt1, surveyItemOpt.getItemOptionList().get(0).getDesc());

    }

    @Test
    public void modifySurveyRootTest() {
        //
        String testDesc = "hello world! desc";
        String testTitle = "hello world! title";
        String testTitleText = "hello world! title text";
        String testTitleOpt = "hello world! title opt";
        String testDescText = "hello world! text";
        String testDescOpt = "hello world! opt";
        String opt1 = "multi_opt1";
        String opt2 = "multi_opt2";
        String opt3 = "multi_opt3";
        ValueYN isRequiredItemText = ValueYN.Y;
        ValueYN isRequiredItemOpt = ValueYN.N;
        SurveyRoot surveyRootOld = SurveyTestFactory.createSurveyRoot(testTitle, testDesc);
        surveyRootOld.addSurveyItem(SurveyTestFactory.createTextItem(SurveyItemType.LONG_TEXT, testTitleText, testDescText, isRequiredItemText));
        surveyRootOld.addSurveyItem(SurveyTestFactory.createOptItem(SurveyItemType.MULTIPLE_CHOICE, testTitleOpt, testDescOpt, isRequiredItemOpt, List.of(opt1, opt2, opt3)));
        surveyRootService.save(surveyRootOld);

        //
        entityManager.flush();
        entityManager.clear();
        String testDesc2 = "modified desc";
        String testTitle2 = "modified title";
        String testTitleText2 = "modified title text";
        String testTitleOpt2 = "modified title opt";
        String testDescText2 = "modified text";
        String testDescOpt2 = "modified opt";
        String opt12 = "modified multi_opt1";
        String opt22 = "modified multi_opt2";
        String opt33 = "modified multi_opt3";
        ValueYN isRequiredItemText2 = ValueYN.N;
        ValueYN isRequiredItemOpt2 = ValueYN.Y;
        SurveyRoot surveyRootParam = new SurveyRoot();
        surveyRootParam.setId(surveyRootOld.getId());
        surveyRootParam.setDesc(testDesc2);
        surveyRootParam.setTitle(testTitle2);
        SurveyItem itemTextBefore = SurveyTestFactory.createTextItem(SurveyItemType.LONG_TEXT, testTitleText2, testDescText2, isRequiredItemText2);
        SurveyItem itemOptBefore = SurveyTestFactory.createOptItem(SurveyItemType.MULTIPLE_CHOICE, testTitleOpt2, testDescOpt2, isRequiredItemOpt2, List.of(opt12, opt22, opt33));
        surveyRootParam.addSurveyItem(itemTextBefore);
        surveyRootParam.addSurveyItem(itemOptBefore);
        surveyRootService.modifySurveyRoot(surveyRootParam);


        //
        entityManager.flush();
        entityManager.clear();
        SurveyRoot surveyRootNew = surveyRootService.findLatestSurveyRootBySurveyGroupId(surveyRootOld.getSurveyGroup().getId());
        Assertions.assertNotNull(surveyRootNew);
        Assertions.assertNotNull(surveyRootNew.getSurveyGroup().getId());
        Assertions.assertNotNull(surveyRootOld.getSurveyGroup().getId());
        Assertions.assertEquals(surveyRootOld.getSurveyGroup().getId(), surveyRootNew.getSurveyGroup().getId());
        Assertions.assertEquals(surveyRootOld.getVer()+1, surveyRootNew.getVer());
        Assertions.assertNotEquals(surveyRootOld.getId(), surveyRootNew.getId());
        SurveyItem itemOptAfter = SurveyTestFactory.findItemByType(surveyRootNew, SurveyItemType.MULTIPLE_CHOICE);
        SurveyItem itemTextAfter = SurveyTestFactory.findItemByType(surveyRootNew, SurveyItemType.LONG_TEXT);
        Assertions.assertNotNull(itemOptAfter);
        Assertions.assertNotNull(itemTextAfter);
        Assertions.assertEquals(testDesc2, surveyRootNew.getDesc());
        Assertions.assertEquals(testTitle2, surveyRootNew.getTitle());
        Assertions.assertEquals(testTitleText2, itemTextAfter.getTitle());
        Assertions.assertEquals(testDescText2, itemTextAfter.getDesc());
        Assertions.assertEquals(testTitleOpt2, itemOptAfter.getTitle());
        Assertions.assertEquals(testDescOpt2, itemOptAfter.getDesc());
        Assertions.assertEquals(isRequiredItemText2, itemTextAfter.getIsRequired());
        Assertions.assertEquals(isRequiredItemOpt2, itemOptAfter.getIsRequired());
        Assertions.assertEquals(opt12, itemOptAfter.getItemOptionList().get(0).getDesc());
        
    }


    @Test
    public void createSurveyApiTest() {
//        - 요청 값에는 [설문조사 이름], [설문조사 설명], [설문 받을 항목]이 포함됩니다.
//        - [설문 받을 항목]은 [항목 이름], [항목 설명], [항목 입력 형태], [항목 필수 여부]의 구성으로 이루어져 있습니다.
//        - [항목 입력 형태]는 [단답형], [장문형], [단일 선택 리스트], [다중 선택 리스트]의 구성으로 이루어져 있습니다.
//        - [단일 선택 리스트], [다중 선택 리스트]의 경우 선택 할 수 있는 후보를 요청 값에 포함하여야 합니다.
//        - [설문 받을 항목]은 1개 ~ 10개까지 포함 할 수 있습니다.
        //
        String textSurveyTitle = "create survey title";
        String textSurveyDesc = "create survey desc";
        //
        String textItemTitle1 = "survey item title1";
        String textItemTitle2 = "survey item title2";
        String textItemTitle3 = "survey item title3";
        String textItemTitle4 = "survey item title4";
        //
        String textItemDesc1 = "survey item desc1";
        String textItemDesc2 = "survey item desc2";
        String textItemDesc3 = "survey item desc3";
        String textItemDesc4 = "survey item desc4";
        //
        ValueYN reqYn1 = ValueYN.Y;
        ValueYN reqYn2 = ValueYN.N;
        ValueYN reqYn3 = ValueYN.Y;
        ValueYN reqYn4 = ValueYN.N;
        //
        String textOptDescSingle = "single opt desc1";
        String textOptDescMulti = "multi opt desc1";
        //
        SurveyItem surveyItem1 = new SurveyItem();
        surveyItem1.setTitle(textItemTitle1);
        surveyItem1.setDesc(textItemDesc1);
        surveyItem1.setType(SurveyItemType.SHORT_TEXT);
        surveyItem1.setIsRequired(reqYn1);
        SurveyItem surveyItem2 = new SurveyItem();
        surveyItem2.setTitle(textItemTitle2);
        surveyItem2.setDesc(textItemDesc2);
        surveyItem2.setType(SurveyItemType.LONG_TEXT);
        surveyItem2.setIsRequired(reqYn2);
        SurveyItem surveyItem3 = new SurveyItem();
        surveyItem3.setTitle(textItemTitle3);
        surveyItem3.setDesc(textItemDesc3);
        surveyItem3.setType(SurveyItemType.SINGLE_CHOICE);
        surveyItem3.setIsRequired(reqYn3);
        ItemOption optSingle = new ItemOption();
        optSingle.setDesc(textOptDescSingle);
        surveyItem3.addItemOption(optSingle);
        SurveyItem surveyItem4 = new SurveyItem();
        surveyItem4.setTitle(textItemTitle4);
        surveyItem4.setDesc(textItemDesc4);
        surveyItem4.setType(SurveyItemType.MULTIPLE_CHOICE);
        surveyItem4.setIsRequired(reqYn4);
        ItemOption optMulti = new ItemOption();
        optMulti.setDesc(textOptDescMulti);
        surveyItem4.addItemOption(optMulti);
        //
        SurveyRoot surveyRoot = new SurveyRoot();
        surveyRoot.setTitle(textSurveyTitle);
        surveyRoot.setDesc(textSurveyDesc);
        surveyRoot.addSurveyItem(surveyItem1);
        surveyRoot.addSurveyItem(surveyItem2);
        surveyRoot.addSurveyItem(surveyItem3);
        surveyRoot.addSurveyItem(surveyItem4);
        //
        surveyRootService.save(surveyRoot);

    }
}
