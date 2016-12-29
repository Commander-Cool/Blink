package com.capstone.blink.network;

import com.capstone.blink.network.responses.AuthErrorResponse;
import com.capstone.blink.network.responses.FailedRequestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BaseURL.getServerURL())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final APIInterface API_SERVICE = RETROFIT.create(APIInterface.class);
    private static final int SERVER_DOWN_CODE = -1;

    public static APIInterface getApiService() {
        return API_SERVICE;
    }

    public static <T> void request(Call<T> call, final NetworkCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    AuthErrorResponse authErrorResponse = ApiErrorManager.parseError(response);
                    final String message = authErrorResponse.getMessage();
                    callback.onFail(new FailedRequestResponse(response.code(), message));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onFail(new FailedRequestResponse(SERVER_DOWN_CODE, t.getMessage()));
            }
        });
    }
}