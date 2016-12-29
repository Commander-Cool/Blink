package com.capstone.blink.dialog;

import android.content.Context;
import android.widget.Toast;

public class Dialog {
    /**
     * Output a toast message
     * @param context Context
     * @param msg String
     */
    public static void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}