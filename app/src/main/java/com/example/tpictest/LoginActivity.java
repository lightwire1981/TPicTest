package com.example.tpictest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tpictest.utils.RequestApiTask;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String KAKAO = "KAKAO_LOGIN";
    private static OAuthLogin mOAuthLoginInstance;
    public static String accessToken;
    OAuthLoginButton mOAuthLoginButton;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(LoginActivity.this,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                getString(R.string.naver_client_name));

        findViewById(R.id.btnNaverLogin).setOnClickListener(onClickListener);

//        kakaoLogin();

        findViewById(R.id.iBtnKakaoLogin).setOnClickListener(onClickListener);
        findViewById(R.id.btnKakaoLogoutTest).setOnClickListener(onClickListener);
    }

    private final OAuthLoginHandler mOAuthLoginHandler;
    {
        mOAuthLoginHandler = new OAuthLoginHandler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void run(boolean success) {
                if (success) {
                    String accessToken = mOAuthLoginInstance.getAccessToken(getBaseContext());
                    String refreshToken = mOAuthLoginInstance.getRefreshToken(getBaseContext());
                    long expiresAt = mOAuthLoginInstance.getExpiresAt(getBaseContext());
                    String tokenType = mOAuthLoginInstance.getTokenType(getBaseContext());
                    new RequestApiTask(getBaseContext(), mOAuthLoginInstance).execute();
                }
            }
        };
    }

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