package com.alphasoft.locationnoticication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {
    public static String getValue(Context context, String key) {
        String value = "";
        String retStr = "";

        try {
            SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
            retStr = shPref.getString(key, value);
        } catch (Exception e) {
            retStr = "";
        }
        return retStr;
    }

    public static int getValueInt(Context context, String key) {
        int valueInt = 0;
        int retInt = 0;
        try {
            SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
            retInt = shPref.getInt(key, valueInt);
        } catch (Exception e) {
            retInt = 0;
        }
        return retInt;
    }

    public static int getValueIntDefaultMinus1(Context context, String key) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
        return shPref.getInt(key, -1);
    }

    public static Long getValueLong(Context context, String key) {
        long valueLong = 0l;
        long retLong = 0l;
        try {
            SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
            retLong = shPref.getLong(key, valueLong);
        } catch (Exception e) {
            retLong = 0l;
        }
        return retLong;
    }

    public static Float getValueFloat(Context context, String key) {
        float valueFloat = 0f;
        float retfloat = 0f;
        try {
            SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
            retfloat = shPref.getFloat(key, valueFloat);
        } catch (Exception e) {
            retfloat = 0f;
        }
        return retfloat;
    }

    public static Boolean getValueBool(Context context, String key) {
        Boolean valueBool = false;
        Boolean retBool = false;
        try {
            SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
            retBool = shPref.getBoolean(key, valueBool);
        } catch (Exception e) {
            retBool = false;
        }
        return retBool;
    }

    public static void removeValue(Context context, String key) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = shPref.edit();
        ed.remove(key).commit();
    }

    public static void setValue(Context context, String key, String value) {

        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = shPref.edit();
        ed.putString(key, value);
        ed.commit();
    }

    public static void setValueInt(Context context, String key, int valueInt) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = shPref.edit();
        ed.putInt(key, valueInt);
        ed.commit();
    }

    public static void setValueLong(Context context, String key, Long valueLong) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = shPref.edit();
        ed.putLong(key, valueLong);
        ed.commit();
    }

    public static void setValueFloat(Context context, String key, Float valueFloat) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = shPref.edit();
        ed.putFloat(key, valueFloat);
        ed.commit();
    }

    public static void setValueBool(Context context, String key, boolean bool) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = shPref.edit();
        ed.putBoolean(key, bool);
        ed.commit();
    }

    public static void clearAllPreferences(Context context) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(context);
        shPref.edit().clear().commit();
    }
}
