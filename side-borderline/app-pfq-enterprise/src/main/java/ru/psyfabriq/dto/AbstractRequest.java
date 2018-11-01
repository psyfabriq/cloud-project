package ru.psyfabriq.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AbstractRequest {

    @NotBlank(message = "{error.empty}")
    protected String id = "";

    @Size(max = 128, min = 3, message = "{error.size}")
    @NotBlank(message = "{error.empty}")
    protected String name;

    protected Date createdAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


}
