package com.auroraworld.toypic.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestApiTask extends AsyncTask<Void, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    private final OAuthLogin mOAuthLoginModule;
    private NaverLoginResult naverLoginResult;

    public RequestApiTask(Context mContext, OAuthLogin mOauthLoginModule, NaverLoginResult naverLoginResult) {
        this.mContext = mContext;
        this.mOAuthLoginModule = mOauthLoginModule;
        this.naverLoginResult = naverLoginResult;
    }

    public interface NaverLoginResult {
        void getLoginInfo(JSONObject jsonObject);
    }

    @Override
    protected String doInBackground(Void... params) {
        String url = "https://openapi.naver.com/v1/nid/me";
        String at = mOAuthLoginModule.getAccessToken(mContext);
        return mOAuthLoginModule.requestApi(mContext, at, url);
    }

    @Override
    protected void onPostExecute(String content) {
        try {
            JSONObject loginResult = new JSONObject(content);
            if (loginResult.getString("resultcode").equals("00")){
                naverLoginResult.getLoginInfo(loginResult);
//                JSONObject response = loginResult.getJSONObject("response");
//                String id = response.getString("id");
//                String email = response.getString("email");
//                Toast.makeText(mContext, "id : "+id +" email : "+email, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
