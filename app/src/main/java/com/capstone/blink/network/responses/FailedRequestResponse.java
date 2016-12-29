package com.capstone.blink.network.responses;

import android.content.Context;

import com.capstone.blink.R;
import com.capstone.blink.application.Initializer;
import com.capstone.blink.dialog.Dialog;

public class FailedRequestResponse {

    private int code;
    private String message;

    public FailedRequestResponse(int code, String message) {
        this.code = code;
        this.message = message;
        checkSessionValid();
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private void checkSessionValid(){
        final Context context = Initializer.getAppContext();
        final int code = getCode();
        switch (code){
            case -1:
                Dialog.makeToast(context, context.getString(R.string.server_down));
        }
        if(code <= 400 && code >= 500){
            Dialog.makeToast(context, context.getString(R.string.error_has_occurred));
        }
    }
}