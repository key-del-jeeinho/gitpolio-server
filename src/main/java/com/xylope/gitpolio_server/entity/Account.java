package com.xylope.gitpolio_server.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Length(max = 20)
    private String name;

    @Id @Length(max = 50)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;

    @Length(max = 20)
    private String password;
}
