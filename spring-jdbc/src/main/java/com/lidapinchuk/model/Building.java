package com.lidapinchuk.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Building {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "inst_id")
    private Long instId;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "is_active")
    private Boolean isActive;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

}
