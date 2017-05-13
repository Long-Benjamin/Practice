package com.along.practice.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.along.practice.common.C;

/**
 * Created by hpw on 16/10/28.
 */

public class SpUtil {
    static SharedPreferences sharedPre;
    private static final boolean DEFAULT_NO_IMAGE = false;
    private static final boolean DEFAULT_AUTO_SAVE = true;

    public static void init(Context context) {
        sharedPre = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static int getThemeIndex(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt("ThemeIndex", 9);
    }

    public static void setThemeIndex(Context context, int index) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt("ThemeIndex", index).apply();
    }

    public static boolean getNightModel(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("pNightMode", false);
    }

    public static void setNightModel(Context context, boolean nightModel) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("pNightMode", nightModel).apply();
    }

    public static boolean getNoImageState() {
        return sharedPre.getBoolean(C.SPKey.SP_NO_IMAGE, DEFAULT_NO_IMAGE);
    }

    public static void setNoImageState(boolean state) {
        sharedPre.edit().putBoolean(C.SPKey.SP_NO_IMAGE, state).apply();
    }

    public static boolean getAutoCacheState() {
        return sharedPre.getBoolean(C.SPKey.SP_AUTO_CACHE, DEFAULT_AUTO_SAVE);
    }

    public static void setAutoCacheState(boolean state) {
        sharedPre.edit().putBoolean(C.SPKey.SP_AUTO_CACHE, state).apply();
    }
}

