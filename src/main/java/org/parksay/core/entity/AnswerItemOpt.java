package org.parksay.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("OPT")
public class AnswerItemOpt extends AnswerItemBase {

    @ManyToOne
    @JoinColumn(name = "seq_item_option", nullable = true)
    private ItemOption itemOption;

}