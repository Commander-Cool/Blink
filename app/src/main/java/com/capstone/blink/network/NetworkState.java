package com.capstone.blink.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.capstone.blink.application.Initializer;

import java.util.Observable;

public class NetworkState extends Observable {
    private static boolean isConnected;
    private static NetworkState instance = new NetworkState();
    private NetworkState(){
        isConnected = isConnected(Initializer.getAppContext());
    }

    /**
     * Returns true or false depending on whether phone is connected to internet or not
     *
     * @param context Context
     * @return boolean
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        return nf != null && nf.isConnected();
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public synchronized void setConnected(boolean connected) {
        isConnected = connected;
        setChanged();
        if(connected){
            notifyObservers();
        }
    }

    public static NetworkState getInstance(){
        return instance;
    }
}
