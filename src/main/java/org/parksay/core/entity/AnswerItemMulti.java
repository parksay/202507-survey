package org.parksay.core.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(name="answer_item")
@DiscriminatorValue("MULTI")
public class AnswerItemMulti extends AnswerItemBase {
    @ManyToOne
    @JoinColumn(name="seq_item_option")
    @Column(nullable = false)    // 다중 선택 전용 entity 이므로 null check 가 가능해짐
    private List<ItemOption> itemOptionList;

}