package ru.psyfabriq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "VARCHAR(25)")
    private String username;
    @Column(columnDefinition = "VARCHAR(72)")
    private String password;
    @Column(columnDefinition = "INT(1)")
    private boolean active;
}

