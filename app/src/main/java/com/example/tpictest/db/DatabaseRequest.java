package com.example.tpictest.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tpictest.utils.PreferenceSetting;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String PHP_URL = "/TPicTest/res/php/request_handler.php";

    public interface ExecuteListener {
        void onResult(String... result);
    }
    private final ExecuteListener executeListener;

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
            case CREATE_KID:
            case GET_ALL_GOODS:
            case GET_ALL_BRAND:
                parameters = MakeParameter(params);
                Log.i(TAG, "Parameter Check ::" + parameters);
                break;
            case LIKE:
                break;
            case WRITE_REVIEW:
                break;
            case TEST:
                parameters = MakeParameter(params);
                Log.i(TAG, "Parameter Check ::" + parameters);
                break;
            default:
                break;
        }

        try {
            String url = "http://"+SERVER_IP+PHP_URL;
            Log.d(TAG, "<<<<<<<<<<<<<  " + url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL(url)).openConnection();
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
                Log.d(TAG, httpURLConnection.getResponseMessage());
                Log.d(TAG, httpURLConnection.getResponseCode()+"");
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
        if (response == null) {
            Log.e(TAG, "<<<<<<<<<<Result Error");
            return;
        }
        switch (requestType) {
            case JOIN:
            case CREATE_KID:
            case GET_ALL_GOODS:
            case GET_ALL_BRAND:
                Log.d(TAG, response);
                executeListener.onResult(response);
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

    private String MakeParameter(String... params) {
        //region Parameter Value

        String USE = "USE=";
        String AND = "&";

        String USER_ID = "USER_ID=";
        String USER_NAME = "USER_NAME=";
        String USER_EMAIL = "USER_EMAIL=";
        String USER_PHONE = "USER_PHONE=";


        String CHILD_ORDER = "CHILD_ORDER=";
        String CHILD_GENDER = "CHILD_GENDER=";
        String CHILD_NICK = "CHILD_NICK=";
        String CHILD_BIRTH = "CHILD_BIRTH=";
        String CHILD_CHAR = "CHILD_CHAR=";
        String CHILD_PERSON = "CHILD_PERSON=";


        //endregion

        String parameterValue;
        try {
            JSONObject jsonObject = new JSONObject();
            if (params.length > 1) {
                jsonObject = new JSONObject(params[1]);
            }
            switch (requestType) {
                case JOIN:
                    parameterValue = USE + params[0] + AND +
                            USER_ID +jsonObject.get("id").toString() + AND +
                            USER_NAME +jsonObject.get("name").toString() + AND +
                            USER_EMAIL +jsonObject.get("email").toString() + AND +
                            USER_PHONE +jsonObject.get("phone").toString()
                    ;
                    break;
                case CREATE_KID:
                    parameterValue = USE + params[0] + AND +
                            USER_ID +jsonObject.get("id").toString() + AND +
                            CHILD_ORDER +jsonObject.get("child_order").toString() + AND +
                            CHILD_GENDER +jsonObject.get("child_gender").toString() + AND +
                            CHILD_NICK +jsonObject.get("child_nick").toString() + AND +
                            CHILD_BIRTH +jsonObject.get("child_birth").toString() + AND +
                            CHILD_CHAR +jsonObject.get("child_character").toString() + AND +
                            CHILD_PERSON +jsonObject.get("child_personality").toString();
                    break;
                case GET_ALL_GOODS:
                case GET_ALL_BRAND:
                case TEST:
                    parameterValue = USE + params[0];
                    break;
                default:
                    parameterValue = null;
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            parameterValue = null;
        }
        return parameterValue;
    }
}
