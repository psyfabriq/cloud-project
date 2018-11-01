package ru.psyfabriq.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TegResponse extends AbstractRequest {

    @Size(max = 256, min = 3, message = "{error.size}")
    @NotBlank(message = "{error.empty}")
    private String description;

    @NotBlank(message = "{error.empty}")
    private String menuname;

    @NotBlank(message = "{error.empty}")
    private String teg;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getTeg() {
        return teg;
    }

    public void setTeg(String teg) {
        this.teg = teg;
    }


}
