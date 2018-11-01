package ru.psyfabriq.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

public class SubscribeRequest {

    @URL(message = "Not valid URL")
    @NotBlank(message = "URL should not be empty")
    private String rssUrl;

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

}
