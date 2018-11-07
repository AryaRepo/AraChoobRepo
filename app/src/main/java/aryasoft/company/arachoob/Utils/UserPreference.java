package aryasoft.company.arachoob.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import aryasoft.company.arachoob.App.MyApplication;

public class UserPreference {

    private static final String USER_SHARED_PREFERENCES = "user_shared_preferences";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_MOBILE_NUMBER = "user_mobile_number";
    private static final String KEY_USER_PASSWORD = "user_password";
    private static final String KEY_IS_USER_LOGIN = "is_user_login";
    private static final String KEY_USER_FULL_NAME = "user_full_name";
    private static SharedPreferences UserSharedPreferences;

    public static void initialize(Context context) {
        UserSharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCES, context.MODE_PRIVATE);
    }

    public static void setUserId(int userId) {
        SharedPreferences.Editor editor = UserSharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public static int getUserId() {
        return UserSharedPreferences.getInt(KEY_USER_ID, -5);
    }

    public static void setUserMobileNumber(String mobileNumber) {
        SharedPreferences.Editor editor = UserSharedPreferences.edit();
        editor.putString(KEY_USER_MOBILE_NUMBER, mobileNumber);
        editor.apply();
    }

    public static String getUserMobileNumber() {
        return UserSharedPreferences.getString(KEY_USER_MOBILE_NUMBER, "");
    }

    public static void setUserPassword(String password) {
        SharedPreferences.Editor editor = UserSharedPreferences.edit();
        editor.putString(KEY_USER_PASSWORD, password);
        editor.apply();
    }

    public static String getUserPassword() {
        return UserSharedPreferences.getString(KEY_USER_PASSWORD, "");
    }

    public static void isUserLogin(boolean loginState) {
        SharedPreferences.Editor editor = UserSharedPreferences.edit();
        editor.putBoolean(KEY_IS_USER_LOGIN, loginState);
        editor.apply();
    }

    public static boolean isUserLogin() {
        return UserSharedPreferences.getBoolean(KEY_IS_USER_LOGIN, false);
    }

    public static void setUserFullName(String fullName) {
        SharedPreferences.Editor editor = UserSharedPreferences.edit();
        editor.putString(KEY_USER_FULL_NAME, fullName);
        editor.apply();
    }

    public static String getUserFullName() {
        return UserSharedPreferences.getString(KEY_USER_FULL_NAME, "مهمان");
    }

}
