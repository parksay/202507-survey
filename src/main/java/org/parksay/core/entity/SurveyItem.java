package org.parksay.core.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "survey_item")
public class SurveyItem extends BaseEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "seq_item")
        private Long id;

        @ManyToOne
        @JoinColumn(name = "seq_survey", nullable = false)
        private SurveyRoot surveyRoot;

        @Column(nullable = false)
        private String desc;

        @Column(length=1)
        @Enumerated(EnumType.STRING)
        private ValueYN isRequired = ValueYN.N;

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private SurveyItemType type;

        @Setter(AccessLevel.NONE)
        @OneToMany(mappedBy = "surveyItem", cascade = CascadeType.ALL)
        private List<ItemOption> itemOptionList = new ArrayList<>();

        // TODO - ITEM entity 도 text 와 opt 로 분리하는 게 나을까?
        //  각 요소에 대한 null check 가 정교해지긴 하겠지.
        //  하지만 그만큼의 가치가 있으려나


        public void setSurveyRoot(SurveyRoot surveyRoot) {
                List<SurveyItem> surveyItemList = surveyRoot.getSurveyItemList();
                if(!surveyItemList.contains(this)) {
                        surveyItemList.add(this);
                }
                if(this.surveyRoot != surveyRoot) {
                        this.surveyRoot = surveyRoot;
                }
        }

        // TODO - 이거 연관관계 편의 메소드도 매번 반복되는 중복 코드들일 거 같은데
        //   간단하게 어노테이션 같은 걸로 안 되려나.
        //   어노테이션에 속성값으로 하위 클래스 넣어서
        //   아니면 util 같은 걸로 클래스 받아서 만들든가..
        //   빈 등록 후처리 같은 걸로 메소드 추가하든.
        //   어쨌든 반복 코드를 없앨 방법이 있지 않을까.
        //   어쩌면 이미 있을 것 같기도 하고
        public void addItemOption(ItemOption itemOption) {
                if(!this.itemOptionList.contains(itemOption)) {
                        this.itemOptionList.add(itemOption);
                }
                if(itemOption.getSurveyItem() == this) {
                        return;
                }
                if(itemOption.getSurveyItem() != null) {
                        itemOption.getSurveyItem().getItemOptionList().remove(itemOption);
                }
                itemOption.setSurveyItem(this);
        }
}

