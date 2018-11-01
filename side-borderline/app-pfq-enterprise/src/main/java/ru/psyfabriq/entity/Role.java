package ru.psyfabriq.entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Column(name = "notdeleted", columnDefinition = "INT(1)")
    boolean notdeleted;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //	@Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "name", columnDefinition = "VARCHAR(60)")
    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String roleName) {
        this.name = roleName;
    }

    public boolean isNotdeleted() {
        return notdeleted;
    }

    public void setNotdeleted(boolean notdeleted) {
        this.notdeleted = notdeleted;
    }


}