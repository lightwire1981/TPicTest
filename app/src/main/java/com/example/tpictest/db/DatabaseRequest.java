package com.example.tpictest.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tpictest.utils.PreferenceSetting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DatabaseRequest extends AsyncTask<String, String, String> {

    private static String SERVER_IP;
    private static final String PHP_URL = "/TpicTest/res/php/request_handler.php";

    public interface ExecuteListener {
        void onResult(String... result);
    }
    private final ExecuteListener executeListener;

    private final String USE = "USE=";
    private DBRequestType requestType;

    private String TAG = "DatabaseRequest";

    public DatabaseRequest(Context context, ExecuteListener executeListener) {
        SERVER_IP = new PreferenceSetting(context).loadPreference(PreferenceSetting.PREFERENCE_KEY.SERVER_ADDRESS);
        Log.i(TAG, SERVER_IP);
        this.executeListener = executeListener;
    }

    @Override
    protected String doInBackground(String... params) {
        String response;
        String parameters = "";
        String dbUse = params[0];

        requestType = DBRequestType.valueOf(dbUse);

        switch (requestType) {
            case JOIN:
                parameters = MakeParameter(requestType, params);
                Log.i(TAG, "Parameter Check ::" + parameters);
                break;
            case CREATE_KID:
                break;
            case LIKE:
                break;
            case WRITE_REVIEW:
                break;
            default:
                break;
        }

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL("http://"+SERVER_IP+PHP_URL)).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream(), StandardCharsets.UTF_8);
            PrintWriter printWriter = new PrintWriter(outputStreamWriter);
            printWriter.write(parameters);
            printWriter.flush();

            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Http Connection Fail");
                return null;
            }
            response = (new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))).readLine();
            Log.d(TAG, "Response Value :: "+response);

            printWriter.close();
            outputStreamWriter.close();

        } catch (IOException e) {
            Log.e("Call at : "+e.getStackTrace()[1].getClassName()+" "+
                    e.getStackTrace()[1].getMethodName(), "Exception Occur");
            return null;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        switch (requestType) {
            case JOIN:
                Log.d(TAG, response);
                executeListener.onResult(response);
                break;
            case CREATE_KID:
                break;
            case LIKE:
                break;
            case WRITE_REVIEW:
                break;
            default:
                break;
        }
        super.onPostExecute(response);
    }

    private String MakeParameter(DBRequestType type, String... params) {

        switch (type) {
            case JOIN:
                break;
            default:
                break;
        }

        return null;
    }
}
