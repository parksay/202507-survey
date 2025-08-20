package org.parksay.core.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.List;
//
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "type"
//)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = AnswerItemOpt.class, name = "opt"),
//        @JsonSubTypes.Type(value = AnswerItemText.class, name = "txt")
//})

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "answer_item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="answer_type")
public class AnswerItemBase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="seq_answer_item")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seq_answer_root", nullable = false)
    @Setter(AccessLevel.NONE)
    @JsonBackReference
    private AnswerRoot answerRoot;

    @ManyToOne
    @JoinColumn(name="seq_survey_item", nullable = false)
    @JsonIgnore
    private SurveyItem surveyItem;

    public void changeAnswerRoot(AnswerRoot answerRoot) {
        List<AnswerItemBase> answerItemList = answerRoot.getAnswerItemList();
        if(!answerItemList.contains(this)) {
            answerItemList.add(this);
        }
        if(this.answerRoot != answerRoot) {
            this.answerRoot = answerRoot;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @JsonIgnore
    public boolean isOpt() {
        if(this.surveyItem.getType() == SurveyItemType.SINGLE_CHOICE || this.surveyItem.getType() == SurveyItemType.MULTIPLE_CHOICE) {
            return true;
        }
        return false;
    }

    // 상속 관계로 설계하기 전의 문제점
    // answer type check 가 제대로 되지 않음
    // text로 답하는 항목인데 option 을 null 로 가지고 있음
    // 반대로 option 으로 답해야 하는 항목인데 text 를 null 로 가지고 있음
    // 그러니까 answer entity 에 text 든 option 이든 null 을 허용할 수밖에 없음
    // option 안에서도, multi 가 있을 수 있으니 type 선언은 option list 로 선언해 둠
    // 그치만 single 항목에서는 element 가 1개뿐인 list 가 됨
    // 그러면 answer type 에 따라서 option list 의 length 를 check 해야 하는 건가?
    // 이런 식으로 answer type 마다 다른 null check 나 validation check 가 필요할 수 있는데
    // 상속관계로 안 하고 한 entity 로만 욱여넣으면 type 에 따른 check 가 어려워짐
    // short / long / single / multi 각각 나누어야 할까.
    // multi 일 경우엔 answerItem 하나를 두고 그 아래 answerItem <-> itemOption 을 잇는 중간 테이블을 한 계층 더 넣든지 해서
}
