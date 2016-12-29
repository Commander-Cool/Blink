package com.capstone.blink.network.responses;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName(ResponseKeys.STATUS)
    private String status;
    @SerializedName(ResponseKeys.MESSAGE)
    private String message;
    @SerializedName(ResponseKeys.PAYLOAD)
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
