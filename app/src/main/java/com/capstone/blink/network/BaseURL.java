package com.capstone.blink.network;

final class BaseURL {

    private static boolean genymotion = true;

    // Development client URLS
    private static final String GENYMOTION_DEVELOPMENT_SERVER_IP = "10.0.3.2";
    private static final String DEVELOPMENT_SERVER_IP = "";

    // Client URL config
    private static String devServer = (genymotion) ? GENYMOTION_DEVELOPMENT_SERVER_IP : DEVELOPMENT_SERVER_IP;
    private static final String DEVELOPMENT_SERVER_URL = "http://" + devServer;

    static String getServerURL() {
        return DEVELOPMENT_SERVER_URL;
    }
}