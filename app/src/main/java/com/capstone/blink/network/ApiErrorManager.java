package com.capstone.blink.network;

import com.capstone.blink.network.responses.AuthErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

class ApiErrorManager {

    static AuthErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, AuthErrorResponse> converter =
                ApiManager.RETROFIT
                        .responseBodyConverter(AuthErrorResponse.class, new Annotation[0]);

        AuthErrorResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new AuthErrorResponse();
        }

        return error;
    }
}