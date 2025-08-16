package survey;


import jakarta.persistence.EntityManager;
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
        SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.addSurveyRoot(surveyRoot);
        surveyGroupService.save(surveyGroup);
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
        SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.addSurveyRoot(surveyRootOld);
        surveyGroupService.save(surveyGroup);

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
        surveyRootOld.setDesc(testDesc2);
        surveyRootOld.setTitle(testTitle2);
        SurveyItem itemTextBefore = SurveyTestFactory.findItemByType(surveyRootOld, SurveyItemType.LONG_TEXT);
        SurveyItem itemOptBefore = SurveyTestFactory.findItemByType(surveyRootOld, SurveyItemType.MULTIPLE_CHOICE);
        itemTextBefore.setTitle(testTitleText2);
        itemTextBefore.setDesc(testDescText2);
        itemTextBefore.setIsRequired(isRequiredItemText2);
        itemOptBefore.setTitle(testTitleOpt2);
        itemOptBefore.setDesc(testDescOpt2);
        itemOptBefore.setIsRequired(isRequiredItemOpt2);
        itemOptBefore.getItemOptionList().get(0).setDesc(opt12);
        itemOptBefore.getItemOptionList().get(1).setDesc(opt22);
        itemOptBefore.getItemOptionList().get(2).setDesc(opt33);
        surveyRootService.modifySurveyRoot(surveyRootOld);


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

}
