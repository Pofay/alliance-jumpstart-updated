package com.alliance.jumpstart.utils;

public enum JobHiringCategories {
    MEETING("MEETING"),
    PROJECT("PROJECT"),
    SHOPPING("SHOPPING"),
    LESSON("LESSON"),
    OUTDOOR("OUTDOOR");
    private String value;

    JobHiringCategories(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
