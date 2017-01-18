package com.capstone.blink.database;

public interface DatabaseChangeListener {

    void onSuccess(String value);

    void onFail(String value);
}
