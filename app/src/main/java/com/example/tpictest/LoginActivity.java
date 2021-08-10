package com.example.tpictest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tpictest.utils.FacebookLoginCallback;
import com.example.tpictest.utils.PreferenceSetting;
import com.example.tpictest.utils.RequestApiTask;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String NAVER = "NAVER_LOGIN";
    private static final String KAKAO = "KAKAO_LOGIN";
    private static final String FACEBOOK = "FACEBOOK_LOGIN";
    private static OAuthLogin mOAuthLoginInstance;
//    public static String accessToken;
//    OAuthLoginButton mOAuthLoginButton;
//    Button logout;

    private CallbackManager callbackManager;
    private FacebookLoginCallback facebookLoginCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginCheck();

        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(LoginActivity.this,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                getString(R.string.naver_client_name));

        callbackManager = CallbackManager.Factory.create();
        facebookLoginCallback = new FacebookLoginCallback();

        LoginButton floginBtn = findViewById(R.id.btnFacebookLogin);
        floginBtn.setPermissions(Arrays.asList("public_profile", "email"));
        floginBtn.registerCallback(callbackManager, facebookLoginCallback);


        findViewById(R.id.btnNaverLogin).setOnClickListener(onClickListener);

//        kakaoLogin();

        findViewById(R.id.iBtnKakaoLogin).setOnClickListener(onClickListener);
        findViewById(R.id.btnKakaoLogoutTest).setOnClickListener(onClickListener);
    }

    private void LoginCheck() {
        String loginType = new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE);

        switch (loginType) {
            case NAVER:
                break;
            case KAKAO:
                break;
            case FACEBOOK:
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn) {
//                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

                    LoginManager.getInstance().retrieveLoginStatus(this, new LoginStatusCallback() {
                        @Override public void onCompleted(AccessToken accessToken) {
                            // User was previously logged in, can log them in directly here.
                            // If this callback is called, a popup notification appears that says
                            // "Logged in as <User Name>" }
                            GraphRequest.newMeRequest(accessToken,
                                    (object, response) -> Log.e("result", object.toString()));
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure() {
                            // No access token could be retrieved for the user
                            Toast.makeText(getBaseContext(), "Facebook reLogin Fail", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Exception exception) {
                            // An error occurred
                            Toast.makeText(getBaseContext(), "Facebook reLogin Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        new PreferenceSetting(getBaseContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, FACEBOOK);

        assert data != null;
//        Log.i(FACEBOOK, data.get;
//        Toast.makeText(getBaseContext(), data.toString(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    private final OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {

        @Override
        public void run(boolean success) {
            if (success) {
//                String accessToken = mOAuthLoginInstance.getAccessToken(getBaseContext());
//                String refreshToken = mOAuthLoginInstance.getRefreshToken(getBaseContext());
//                long expiresAt = mOAuthLoginInstance.getExpiresAt(getBaseContext());
//                String tokenType = mOAuthLoginInstance.getTokenType(getBaseContext());
                new RequestApiTask(getBaseContext(), mOAuthLoginInstance, naverLoginResult).execute();
            }
        }
    };

    RequestApiTask.NaverLoginResult naverLoginResult = jsonObject -> {
        JSONObject response;
        try {
            response = jsonObject.getJSONObject("response");
            String id = response.getString("id");
            String email = response.getString("email");

            new PreferenceSetting(getBaseContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, NAVER);
//            Toast.makeText(getBaseContext(), "id : "+id +" email : "+email, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btnNaverLogin:
                mOAuthLoginInstance.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
                break;
            case R.id.iBtnKakaoLogin:
                kakaoLogin();
                break;
            case R.id.btnKakaoLogoutTest:
                UserApiClient.getInstance().logout(error -> {
                    if (error != null) {
                        Log.e(KAKAO, "로그아웃 실패", error);
                        Toast.makeText(getApplicationContext(), "로그아웃 실패", Toast.LENGTH_SHORT).show();
                    }else {
                        Log.e(KAKAO, "로그아웃 성공, SDK에서 토큰 삭제됨");
                        Toast.makeText(getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();
                    }
                    return null;
                });
                break;
            default:
                break;
        }

    };

    private void kakaoLogin() {
        UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this,((oAuthToken, error) -> {
            if (error != null) {
                Log.e(KAKAO, "로그인 실패", error);
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
            } else if (oAuthToken != null) {
                Log.i(KAKAO, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
//                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                UserApiClient.getInstance().me((user, meError) -> {
                    if (meError != null) {
                        Log.e(KAKAO, "카카오 사용자 정보 요청 실패", meError);
                    } else {
                        Log.i(KAKAO, "사용자 정보 요청 성공" +
                                "\n회원번호 : "+user.getId() +
                                "\n이메일 : "+ Objects.requireNonNull(user.getKakaoAccount()).getEmail() +
                                "\n닉네임 : "+ Objects.requireNonNull(user.getProperties()).get("nickname"));
                        PreferenceSetting preferenceSetting = new PreferenceSetting(getBaseContext());
                        preferenceSetting.savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, KAKAO);

                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(user.getProperties()).get("nickname")+" 님 로그인", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    return null;
                });
            }
            return null;
        }));
    }
}