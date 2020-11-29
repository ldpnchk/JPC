package com.lidapinchuk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    private Long instId;

    private String material;

    private Double price;

    private String supplier;

    private String measurement;

    private String balance;

}
