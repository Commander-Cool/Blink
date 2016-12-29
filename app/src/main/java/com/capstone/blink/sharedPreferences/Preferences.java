package com.capstone.blink.sharedPreferences;

public enum Preferences {
    SETTINGS("settings"), SESSION("session");

    private String fileType;

    Preferences(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return fileType;
    }
}