package com.lidapinchuk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "inst_id")
    private Long instId;

    private String material;

    private BigDecimal price;

    private String supplier;

    private String measurement;

    private String balance;

}
