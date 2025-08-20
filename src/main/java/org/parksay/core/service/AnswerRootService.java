package org.parksay.core.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.parksay.core.entity.*;
import org.parksay.infra.repository.AnswerRootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerRootService {
    @Autowired
    AnswerRootRepository answerRootRepository;
    @Autowired
    SurveyRootService surveyRootService;
    @Autowired
    EntityManager entityManager;
    public AnswerRoot save(AnswerRoot answerRoot) {
        // surveyRoot 조회
        SurveyRoot surveyRoot = surveyRootService.findById(answerRoot.getSurveyRoot().getId());
        answerRoot.changeSurveyRoot(surveyRoot);
        // validation
        checkAnswerValidation(surveyRoot, answerRoot);
        // 저장 후 return
        answerRootRepository.save(answerRoot);
        entityManager.clear();  // 영속성 컨텍스트에 있는 애들 일단 clear 하고 하위 클래스들이 가지고 있는 필드들 모두 채워서 새로 가져오기
        return findById(answerRoot.getId());
    }

    public AnswerRoot findById(Long id) {
        return answerRootRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<AnswerRoot> findBySurveyRootId(Long surveyRootId) {
        return answerRootRepository.findAllBySurveyRootId(surveyRootId);
    }

    public boolean checkAnswerValidation(SurveyRoot surveyRoot, AnswerRoot answerRoot) {
        List<SurveyItem> surveyItemList = surveyRoot.getSurveyItemList();
        Iterator<SurveyItem> iter = surveyItemList.iterator();
        // order 되어 있는 것도 아니고, 시간 복잡도가 n^2 이지만 완전탐색밖에는 답이 없을 듯.
        while(iter.hasNext()) {
            SurveyItem item = iter.next();
            Optional<AnswerItemBase> findResult = answerRoot.getAnswerItemList().stream().filter((ele)->{ return ele.getSurveyItem().getId() == item.getId(); }).findFirst();
            if(item.getIsRequired() == ValueYN.Y && findResult.isEmpty()) {
                throw new IllegalArgumentException("필수 질문에 모두 답해주세요");
            }
            if(item.getType() != findResult.get().getSurveyItem().getType()) {
                throw new IllegalArgumentException("응답 유형이 서로 다릅니다");
            }
        }
        //
        return true;
    }
}
