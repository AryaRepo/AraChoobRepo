package aryasoft.company.arachoob.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesHelper
{
    private static SharedPreferences preferences = null;
    private static SharedPreferences.Editor editor = null;

    public static void initPreferences(Context AppContext)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(AppContext);
        editor = preferences.edit();
    }

    public static String readString(String KeyName)
    {
        return preferences.getString(KeyName, "");
    }

    public static void writeString(String KeyName, String Value)
    {
        editor.putString(KeyName, Value);
        saveChanges();
    }

    public static int readInt(String KeyName)
    {
        return preferences.getInt(KeyName, -1);
    }

    public static void clearAllPreferences()
    {
        editor.clear();
        editor.commit();
    }
    public static void clearPreferenceByKeyName(String keyName)
    {
        editor.remove(keyName);
        editor.commit();
    }
    public static void writeInt(String KeyName, int Value)
    {
        editor.putInt(KeyName, Value);
        saveChanges();
    }

    public static long readLong(String KeyName)
    {
        return preferences.getLong(KeyName, -1);
    }

    public static void writeLong(String KeyName, long Value)
    {
        editor.putLong(KeyName, Value);
        saveChanges();
    }

    public static float readFloat(String KeyName)
    {
        return preferences.getFloat(KeyName, -1);
    }

    public static void writeFloat(String KeyName, float Value)
    {
        editor.putFloat(KeyName, Value);
        saveChanges();
    }

    public static Boolean readBoolean(String KeyName)
    {
        return preferences.getBoolean(KeyName, false);
    }

    public static void writeBoolean(String KeyName, boolean Value)
    {
        editor.putBoolean(KeyName, Value);
        saveChanges();
    }

    private static void saveChanges()
    {
        editor.apply();
        editor.commit();
    }

}
