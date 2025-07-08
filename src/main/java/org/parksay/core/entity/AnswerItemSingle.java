package org.parksay.core.entity;

import jakarta.persistence.*;

@Entity(name="answer_item")
@DiscriminatorValue("SINGLE")
public class AnswerItemSingle extends  AnswerItemBase {
    @ManyToOne
    @JoinColumn(name="seq_item_option")
    @Column(nullable = true)    // entity 를 상속 관계 구조로 구현했기 때문에 null check 가능
    private ItemOption itemOption;

}
