package com.lidapinchuk.model;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    private Long instId;

    private String workName;

    private String measurement;

    private Double price;

    private Double amount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Building building;

}
