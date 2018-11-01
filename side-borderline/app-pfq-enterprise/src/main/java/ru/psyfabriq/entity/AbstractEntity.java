package ru.psyfabriq.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.psyfabriq.entity.audit.UserDateAudit;

/**
 * @author psyfabriq
 */
@MappedSuperclass
public abstract class AbstractEntity extends UserDateAudit implements Serializable {
    @Id
    protected String id;

    @Column(name = "name", columnDefinition = "VARCHAR(128)", unique = true, nullable = false)
    @Size(max = 128, min = 3, message = "{error.size}")
    @NotBlank(message = "{error.empty}")
    protected String name;

    // @Column(name = "hash",unique=true, nullable=false)
    // @JsonIgnore
    // private String hash;

    public AbstractEntity() {
        super();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
