package com.lidapinchuk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name = "users")
@Entity
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "inst_id")
    private Long instId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "email_backup")
    private String emailBackup;

    private String TN;

    @Column(name = "TN_backup")
    private String TNBackup;

}
