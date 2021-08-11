package com.example.tpictest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class PreferenceSetting {
    private final Context context;
    //    private static final String DEFAULT_SERVER_IP = "127.0.0.1";
    private static final String DEFAULT_SERVER_IP = "211.38.3.53";
    private static final String DEFAULT_LOGIN_TYPE = "none";
    public enum PREFERENCE_KEY {
        SERVER_ADDRESS,
        USER_INFO,
        LOGIN_TYPE
    }
    private final String[] USER_INFO_TYPE = {
            "id", "name", "email", "phone"
    };
    private static final String TAG = "PreferenceSetting";

    public PreferenceSetting(Context context) {this.context = context;}

    public String loadPreference(PREFERENCE_KEY category) {
        String returnValue;

        SharedPreferences preferences = context.getSharedPreferences("prefInfo", Activity.MODE_PRIVATE);
        switch (category) {
            case USER_INFO:
                returnValue = preferences.getString(PREFERENCE_KEY.USER_INFO.name(), "");
                break;
            case SERVER_ADDRESS:
                returnValue = preferences.getString(PREFERENCE_KEY.SERVER_ADDRESS.name(), DEFAULT_SERVER_IP);
                break;
            case LOGIN_TYPE:
                returnValue = preferences.getString(PREFERENCE_KEY.LOGIN_TYPE.name(), DEFAULT_LOGIN_TYPE);
                break;
            default:
                returnValue = null;
                break;
        }
        return returnValue;
    }

    public void savePreference(PREFERENCE_KEY category, String value) {
        SharedPreferences preferences = context.getSharedPreferences("prefInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(category.name(), value);
//        switch (category) {
//            case USER_INFO:
//                editor.putString(PREFERENCE_KEY.USER_INFO.name(), value);
//                break;
//            case SERVER_ADDRESS:
//                editor.putString(PREFERENCE_KEY.USER_INFO.name(), value);
//                break;
//            default:
//                editor.putString("ServerAddress", value);
//                break;
//        }
        editor.apply();
        Log.i(TAG, "정보 반영 됨");
//        Toast.makeText(context, "정보 반영 됨", Toast.LENGTH_SHORT).show();
    }

    public void saveUserInfo(String... values) {
        Log.i(TAG, "user information count : " + values.length);

        JSONObject jsonObject = new JSONObject();
        try {
            int index = 0;
            for (String info : values) {
                jsonObject.put(USER_INFO_TYPE[index], info);
                index++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        savePreference(PREFERENCE_KEY.USER_INFO, jsonObject.toString());
    }

    public void saveUserInfo(JSONObject jsonObject) {
        savePreference(PREFERENCE_KEY.USER_INFO, jsonObject.toString());
    }
}
