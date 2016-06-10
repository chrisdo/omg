package de.bowsern.omg;

import android.content.Context;

/**
 * Created by felix on 09-Jun-16.
 */
public class User {

    public static final String KEY_USERNAME = "username";
    public static final String PREFS_FILE = "omg_userprefs";

    private static String username;

    public static String getUsername(Context context) {
        if (username != null) {
            return username;
        }

        username = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE).getString(KEY_USERNAME, null);
        return username;
    }

    public static void setUsername(String username, Context context) {
        User.username = username;
        context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE).edit().putString(KEY_USERNAME, username).commit();
    }

}
