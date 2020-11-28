package com.lidapinchuk.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    private Long instId;

    private String reportName;

    private Double price;

    private Date orderDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}
