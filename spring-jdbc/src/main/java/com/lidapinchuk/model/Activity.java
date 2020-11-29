package com.lidapinchuk.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "inst_id")
    private Long instId;

    @Column(name = "work_name")
    private String workName;

    private String measurement;

    private BigDecimal price;

    private BigDecimal amount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

}
