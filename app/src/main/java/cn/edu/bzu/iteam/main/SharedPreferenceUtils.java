package cn.edu.bzu.iteam.main;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceUtils {

    public static void setUserId(Context context, String userid) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userid", userid);
        editor.commit();
    }

    public static void setLoginornot(Context context, int loginornot) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("loginornot", loginornot);
        editor.commit();
    }

    public static String getUserId(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        return sharedpreferences.getString("userid", "");
    }

    public static int getLoginornot(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        return sharedpreferences.getInt("loginornot", 0);
    }
}
