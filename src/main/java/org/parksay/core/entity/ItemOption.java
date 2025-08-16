package org.parksay.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name="item_option")
@Immutable
public class ItemOption extends BaseEntity implements VersionCloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="seq_item_option")
    private Long id;

    @Column(nullable = false)
    private String desc;

    @Setter(AccessLevel.PRIVATE)
    private int ver = 1;

    @ManyToOne
    @JoinColumn(name="seq_survey_item", nullable = false)
    @Setter(AccessLevel.NONE)
    private SurveyItem surveyItem;


    public void changeSurveyItem(SurveyItem surveyItem) {
        List<ItemOption> itemOptionList = surveyItem.getItemOptionList();
        if(!itemOptionList.contains(this)) {
            itemOptionList.add(this);
        }
        if(this.surveyItem != surveyItem) {
            this.surveyItem = surveyItem;
        }
    }


    @Override
    public BaseEntity cloneWithNewVersion(int newVersion) {
        ItemOption newItemOption = new ItemOption();
        newItemOption.setDesc(this.getDesc());
        newItemOption.setVer(newVersion);
        return newItemOption;
    }
}
