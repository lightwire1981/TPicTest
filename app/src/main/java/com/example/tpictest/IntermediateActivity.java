package com.example.tpictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tpictest.utils.PreferenceSetting;
import com.example.tpictest.utils.RequestApiTask;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.example.tpictest.LoginActivity.FACEBOOK;
import static com.example.tpictest.LoginActivity.KAKAO;
import static com.example.tpictest.LoginActivity.NAVER;

public class IntermediateActivity extends AppCompatActivity {

    private String phoneNumber;
    private static OAuthLogin mOAuthLoginInstance;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar();
        setContentView(R.layout.activity_blank);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        phoneNumber = telephonyManager.getLine1Number();
        if (phoneNumber != null && phoneNumber.startsWith("+82")) {
            phoneNumber = phoneNumber.replace("+82", "0");
        } else {
            phoneNumber = "";
        }

        switch (LoginCheck()) {
            case NAVER:
                mOAuthLoginInstance = OAuthLogin.getInstance();
                mOAuthLoginInstance.init(IntermediateActivity.this,
                        getString(R.string.naver_client_id),
                        getString(R.string.naver_client_secret),
                        getString(R.string.naver_client_name));
                mOAuthLoginInstance.startOauthLoginActivity(IntermediateActivity.this, mOAuthLoginHandler);
                break;
            case KAKAO:
                kakaoLogin();
                break;
            case FACEBOOK:
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn) {
                    LoginManager.getInstance().retrieveLoginStatus(this, new LoginStatusCallback() {
                        @Override public void onCompleted(AccessToken accessToken) {
                            // User was previously logged in, can log them in directly here.
                            // If this callback is called, a popup notification appears that says
                            // "Logged in as <User Name>" }
                            GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, (object, response) -> {
                                Log.e("result", object.toString());
                                try {
                                    object.put("phone", phoneNumber);
                                    new PreferenceSetting(getBaseContext()).saveUserInfo(object);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LoadMainPage();
                            });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email,gender,birthday");
                            graphRequest.setParameters(parameters);
                            graphRequest.executeAsync();
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
                } else {
                    new PreferenceSetting(getBaseContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, LoginActivity.NO_LOGIN);
                    LoadMainPage();
                }
                break;
            default:
                LoadMainPage();
                break;
        }
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

            Log.i(NAVER, "id : "+id +" email : "+email);

            new PreferenceSetting(getBaseContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, NAVER);
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private void kakaoLogin() {
        UserApiClient.getInstance().loginWithKakaoTalk(IntermediateActivity.this,((oAuthToken, error) -> {
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
                        LoadMainPage();
                    }
                    return null;
                });
            }
            return null;
        }));
    }

    private void LoadMainPage() {
        getWindow().getDecorView().postDelayed(() -> {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
//            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, 1000);
    }

    private String LoginCheck() {
        return new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE);
    }

    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}