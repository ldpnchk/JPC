package com.lidapinchuk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long instId;

    private String userName;

    private String lastName;

    private String email;

    private String emailBackup;

    private String TN;

    private String TNBackup;

}
