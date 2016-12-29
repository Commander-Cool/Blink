package com.capstone.blink.models;

import com.google.gson.annotations.SerializedName;

public final class User {

    @SerializedName(Utils.ID)
    private int id;
    @SerializedName(Utils.USERNAME)
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}