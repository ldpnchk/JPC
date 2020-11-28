package com.lidapinchuk.model;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Building {

    private Long instId;

    private String buildingName;

    private Boolean isActive;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Report report;

}
