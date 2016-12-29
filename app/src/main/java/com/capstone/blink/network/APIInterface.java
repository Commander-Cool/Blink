package com.capstone.blink.network;

import com.capstone.blink.network.requestData.AuthData;
import com.capstone.blink.network.responses.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    // URLS
    String LOGIN = "auth/login";
    String REGISTER = "auth/register";

    // URL Params

    @POST(LOGIN)
    Call<UserResponse> getUser(
            @Body AuthData data
    );

    @POST(REGISTER)
    Call<UserResponse> createUser(
            @Body AuthData data
    );
}
