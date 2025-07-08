package org.parksay.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name="answer_item")
@DiscriminatorValue("TEXT")
public class AnswerItemText extends AnswerItemBase {

    // 상속 관계 구조로 맵핑함으로써 text 답변만 취급하는 entity 를 구현했지만
    // 답변 항목이 필수 답변이 아닐 수 있으므로 nullable
    @Column(nullable = true)
    private String txtVal;
}
