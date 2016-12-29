package com.capstone.blink.network;


import com.capstone.blink.network.responses.FailedRequestResponse;

public interface NetworkCallback<T> {
    void onSuccess(T response);
    void onFail(FailedRequestResponse error);
}

