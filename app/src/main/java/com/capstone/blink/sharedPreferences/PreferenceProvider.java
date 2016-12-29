package com.capstone.blink.sharedPreferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public final class PreferenceProvider {

    public static SharedPreferences.Editor getEditor(Context context, Preferences preference){
        return getSharedPreferences(context, preference).edit();
    }

    public static SharedPreferences getSharedPreferences(Context context, Preferences preference){
        if(preference == Preferences.SETTINGS){
            return PreferenceManager.getDefaultSharedPreferences(context);
        }

        return context.getSharedPreferences(preference.toString(), Context.MODE_PRIVATE);
    }

}
