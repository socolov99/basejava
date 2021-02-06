package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LinkedIn("LinkedIn"),
    GitHub("GitHub"),
    Stackoverflow("Stackoverflow");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
