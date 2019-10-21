package com.equipo.superttapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    public static final String DEFAULT_STRING = "DEFAULT_STRING";
    public static final int DEFAULT_INT = -1;
    public static final String PREFERENCES_NAME = "preferencias-user";
    public static final String KEY_USER_IS_LOGGED = "user-is-logged";
    public static final String KEY_USER_EMAIL = "user-email";
    public static final String KEY_USER_ID = "user-id";
    public static final String KEY_USER_NAME = "user-name";
    public static final String KEY_USER_LAST_NAME = "user-last-name";
    public static final String KEY_USER_TOKEN = "user-token";
    public static final String KEY_USER_IMAGE ="user-image";

    public PreferencesManager(Context context, String preferencesName, int mode) {
        this.context = context;
        this.preferences = context.getSharedPreferences(preferencesName, mode);
    }

    public void saveValue(String key, String value) {
        editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveValue(String key, Boolean value) {
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void saveValue(String key, Integer value) {
        editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        return preferences.getString(key, DEFAULT_STRING);
    }

    public int getIntegerValue(String key) {
        return preferences.getInt(key, DEFAULT_INT);
    }

    public Boolean getBooleanValue(String key) {
        return preferences.getBoolean(key, false);
    }

    public Boolean keyExists(String key) {
        return preferences.contains(key);
    }

    public void deleteValue(String key) {
        editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
