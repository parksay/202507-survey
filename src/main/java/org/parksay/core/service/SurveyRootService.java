package org.parksay.core.service;


import jakarta.persistence.EntityNotFoundException;
import org.parksay.core.entity.SurveyGroup;
import org.parksay.core.entity.SurveyRoot;
import org.parksay.infra.repository.SurveyRootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SurveyRootService {

    @Autowired
    SurveyRootRepository surveyRootRepository;
    @Autowired
    SurveyGroupService surveyGroupService;

    public SurveyRoot save(SurveyRoot surveyRoot) {
        if(surveyRoot.getSurveyItemList().size() > 10) {
            throw new IllegalArgumentException("설문 항목은 최대 10개까지만 등록할 수 있습니다");
        }
        if(surveyRoot.getSurveyGroup() == null) {
            SurveyGroup surveyGroup = new SurveyGroup();
            surveyGroup.addSurveyRoot(surveyRoot);
            surveyGroupService.save(surveyRoot.getSurveyGroup());
        }
        return surveyRootRepository.save(surveyRoot);
    }

    public SurveyRoot findById(Long id) {
        return surveyRootRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public SurveyRoot modifySurveyRoot(SurveyRoot surveyRootParam) {
        SurveyRoot surveyRootOld = this.findById(surveyRootParam.getId());
        SurveyRoot surveyRootNew = (SurveyRoot)surveyRootParam.cloneWithNewVersion(surveyRootOld.getVer()+1);
        surveyRootNew.changeSurveyGroup(surveyRootOld.getSurveyGroup());
        return this.save(surveyRootNew);

//        SurveyRoot surveyRootOld = this.findById(surveyRootParam.getId());
//        SurveyRoot surveyRootNew = (SurveyRoot)surveyRootParam.cloneWithNewVersion(surveyRootOld.getVer()+1);
//        this.save(surveyRootNew);
//        surveyRootNew.changeSurveyGroup(surveyRootOld.getSurveyGroup());
//        return surveyRootNew;
//        여기서 save() 할 때 surveyRootNew 가 참조하는 surveyGroup 이 null 이므로 save() 안에서 surveyGroup 을 새로 만들어 버림.
//        surveyRootOld 가 참조하는 group 은 seq 이 4이고, 그 seq 이 4인 group 에서 select 해올 텐데,
//        save 할 때는 group 이 null 인 상태여서 group 을 새로 만들어서 저장하는 바람에 seq 이 5인 group 으로 새로 저장해버림
//        newSurveyRoot 는 seq 이 5인 group 에 저장돼 있고, oldSurveyRoot 는 seq 이 4인 group 을 참조하고 있으니 못 찾음.
//        seq 이 4인 group 에는 그대로 oldSurveyRoot만 남아 있으므로, 결국 그냥 oldSurveyRoot 를 다시 그대로 select 해오게 되는 거.
//        고칠 거면 save() 를 고쳐야 함. null check 하는 if문 안에서 group.save() 하고 바로 return 해버리고, if문 밖에서는 root.save() 따로 해버리는 식으로.
    }

    public SurveyRoot findLatestSurveyRootBySurveyGroupId(Long surveyGroupid) {
        return surveyRootRepository.findTopBySurveyGroupIdOrderByVerDesc(surveyGroupid).orElseThrow(EntityNotFoundException::new);
    }
}
