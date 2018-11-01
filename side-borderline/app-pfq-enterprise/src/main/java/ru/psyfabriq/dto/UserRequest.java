package ru.psyfabriq.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import ru.psyfabriq.entity.User;


public class UserRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;


    @NotBlank
    @Size(min = 4, max = 40)
    private String code;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @Size(max = 15)
    private String secondname;

    @Size(max = 15)
    private String lastname;

    @Size(max = 256)
    private String aboutme;

    @Size(max = 15)
    private String city;

    @Size(max = 15)
    private String country;

    public UserRequest() {
    }

    public UserRequest(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.secondname = user.getSecondname();
        this.lastname = user.getLastname();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.aboutme = user.getAboutme();
        this.code = user.getCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
