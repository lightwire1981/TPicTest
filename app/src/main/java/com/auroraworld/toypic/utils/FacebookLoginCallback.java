package com.auroraworld.toypic.utils;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

public class FacebookLoginCallback implements FacebookCallback<LoginResult> {

    public interface GetFacebookResponse {
        void getResponse(AccessToken accessToken);
    }

    private final GetFacebookResponse getFacebookResponse;

    public FacebookLoginCallback(GetFacebookResponse getFacebookResponse) {
        this.getFacebookResponse = getFacebookResponse;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e("Callback :: ", "onSuccess");
        getFacebookResponse.getResponse(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
        Log.e("Callback :: ", "onCancel");
    }

    @Override
    public void onError(FacebookException error) {
        Log.e("Callback :: ", "onError : " + error.getMessage());
    }

    // 사용자 정보 요청
//    public void requestMe(AccessToken token)
//    {
//        GraphRequest graphRequest;
//        graphRequest = GraphRequest.newMeRequest(token,
//                (object, response) -> Log.e("result",object.toString()));
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,email,gender,birthday");
//        graphRequest.setParameters(parameters);
//        graphRequest.executeAsync();
//    }
}
