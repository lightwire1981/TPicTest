package com.example.tpictest.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tpictest.utils.PreferenceSetting;

public class DatabaseRequest extends AsyncTask<String, String, String> {

    private static String SERVER_IP;
    private static final String PHP_URL = "/TpicTest/res/php/request_handler.php";

    public interface ExecuteListener {
        void onResult(String... result);
    }
    private final ExecuteListener executeListener;

    private final String USE = "USE=";
    private String dbUse;

    public enum REQUEST_TYPE {
        JOIN,
        CREATE_KID,
        LIKE,
        WRITE_REVIEW
    }

    private String TAG = "DatabaseRequest";

    public DatabaseRequest(Context context, ExecuteListener executeListener) {
        SERVER_IP = new PreferenceSetting(context).loadPreference(PreferenceSetting.PREFERENCE_KEY.SERVER_ADDRESS);
        Log.i(TAG, SERVER_IP);
        this.executeListener = executeListener;
    }

    @Override
    protected String doInBackground(String... params) {
        String response;
        String parameters;
        dbUse = params[0];

        switch (dbUse) {
            case "JOIN":
                break;
            default:
                break;
        }


        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
    }
}
