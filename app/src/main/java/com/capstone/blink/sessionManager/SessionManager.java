package com.capstone.blink.sessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.capstone.blink.models.User;
import com.capstone.blink.sharedPreferences.PreferenceKeys;
import com.capstone.blink.sharedPreferences.PreferenceProvider;
import com.capstone.blink.sharedPreferences.Preferences;
import com.google.gson.Gson;


/**
 *
 */

public final class SessionManager {

    /**
     * <p>Creates a new active session for a logged in user</p>
     *
     * @param context Context
     * @param user    User
     */
    public static void newSession(Context context, User user) {
        clear(context);
        syncUser(context, user);
        Session.getInstance().setCurrentUser(user);

    }

    private synchronized static void syncUser(Context context, User user) {
        SharedPreferences.Editor editor = PreferenceProvider.getEditor(context, Preferences.SESSION);
        Gson userGson = new Gson();
        String userJson = userGson.toJson(user);
        editor.putString(PreferenceKeys.CURRENT_USER, userJson);
        editor.putBoolean(PreferenceKeys.IS_LOGGED_IN, true);
        editor.apply();
    }

    public synchronized static void syncUser(Context context) {
        syncUser(context, getCurrentUser());
    }


    /**
     * <p>Destroys the current user session (logged out)</p>
     *
     * @param context Context
     */
    public static void destroySession(Context context) {
        Session.getInstance().endSession();
        SharedPreferences.Editor editor = PreferenceProvider.getEditor(context, Preferences.SESSION);
        editor.clear();
        editor.apply();
    }

    /**
     * <p>Checks to see if a previously created session is available</p>
     *
     * @return boolean | Previously logged in our not
     */
    public static boolean isLoggedIn(Context context) {
        boolean loggedIn = PreferenceProvider.getSharedPreferences(context, Preferences.SESSION).getBoolean(PreferenceKeys.IS_LOGGED_IN, false);
        if (loggedIn) {
            loggedIn = restore(context);
        }
        return loggedIn;
    }

    /**
     * <p>Restore a user object from a previous session</p>
     *
     * @return user
     */
    private static boolean restore(Context context) {
        try {
            Gson gson = new Gson();
            final SharedPreferences sessionSharedPrefs = PreferenceProvider.getSharedPreferences(context, Preferences.SESSION);
            String json = sessionSharedPrefs.getString(PreferenceKeys.CURRENT_USER, "");
            if (!json.equals("")) {
                User user = gson.fromJson(json, User.class);
                final Session session = Session.getInstance();
                session.setCurrentUser(user);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * <p>Gets the Current Session's user</p>
     *
     * @return User
     */
    public static User getCurrentUser() {
        return Session.getInstance().getCurrentUser();
    }

    private static void clear(Context context){
        PreferenceProvider.getEditor(context, Preferences.SESSION).clear();
    }

}
