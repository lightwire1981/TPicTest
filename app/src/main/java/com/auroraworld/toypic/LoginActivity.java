package com.auroraworld.toypic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.utils.CustomDialog;
import com.auroraworld.toypic.utils.FacebookLoginCallback;
import com.auroraworld.toypic.utils.PreferenceSetting;
import com.auroraworld.toypic.utils.RequestApiTask;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    public static final String NAVER = "NAVER_LOGIN";
    public static final String KAKAO = "KAKAO_LOGIN";
    public static final String FACEBOOK = "FACEBOOK_LOGIN";
    public static final String EMAIL = "EMAIL_LOGIN";
    public static final String NO_LOGIN = "NO_LOGIN";
    private static OAuthLogin mOAuthLoginInstance;
//    public static String accessToken;
    OAuthLoginButton mOAuthLoginButton;
//    Button logout;

    private FacebookLoginCallback facebookLoginCallback;
    private CallbackManager callbackManager;

    private String emailForm;
    private String phoneNumber;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Spinner spnEmailform = findViewById(R.id.sPnrLoginEmailForm);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getBaseContext(), R.layout.spnr_item_email_form, getResources().getStringArray(R.array.txt_email_form));
        spnEmailform.setAdapter(adapter);
        spnEmailform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emailForm = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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
            phoneNumber = "NULL";
        }
//        LoginCheck();

        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(LoginActivity.this,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                getString(R.string.naver_client_name));
        mOAuthLoginButton = findViewById(R.id.btnNaverLogin);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        mOAuthLoginButton.setBgResourceId(R.drawable.tp_btn02);


//        callbackManager = CallbackManager.Factory.create();
//        FacebookLoginCallback facebookLoginCallback = new FacebookLoginCallback(getFacebookResponse);

//        LoginButton floginBtn = findViewById(R.id.btnFacebookLogin);
//        floginBtn.setPermissions(Arrays.asList("public_profile", "email"));
//        floginBtn.registerCallback(callbackManager, facebookLoginCallback);

        facebookLoginCallback = new FacebookLoginCallback(getFacebookResponse);
        ImageButton floginbBtn = findViewById(R.id.btnFacebookLogin);
        floginbBtn.setOnClickListener(onClickListener);

        findViewById(R.id.btnLoginEmail).setOnClickListener(onClickListener);
        findViewById(R.id.btnJoinEmail).setOnClickListener(onClickListener);
        findViewById(R.id.btnNaverLogin).setOnClickListener(onClickListener);
        findViewById(R.id.iBtnKakaoLogin).setOnClickListener(onClickListener);
        findViewById(R.id.iBtnLoginExit).setOnClickListener(onClickListener);
//        findViewById(R.id.btnKakaoLogoutTest).setOnClickListener(onClickListener);
    }

    private void LoginCheck() {
        String loginType = new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE);

        switch (loginType) {
            case NAVER:
                mOAuthLoginInstance.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
                break;
            case KAKAO:
                kakaoLogin();
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

                            GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, (object, response) -> {
                                        Log.e("result", object.toString());
                                try {
                                    object.put("phone", phoneNumber);
                                    new PreferenceSetting(getBaseContext()).saveUserInfo(object);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intent);
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

//        Intent intent = new Intent(getBaseContext(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
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
            response.put("phone", phoneNumber);
            UserJoin(response);
            PreferenceSetting preferenceSetting = new PreferenceSetting(getBaseContext());
            preferenceSetting.saveUserInfo(response);
            preferenceSetting.savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, NAVER);

            Log.i(NAVER, "id : "+id +" email : "+email);


//            LoadMainPage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btnJoinEmail:
                Intent intent = new Intent(getBaseContext(), JoinActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLoginEmail:
                String userid = ((EditText)findViewById(R.id.eTxtLoginEmailId)).getText().toString();
                String userEmail = userid+"@"+emailForm;
                String password = ((EditText)findViewById(R.id.eTxtLoginEmailPassword)).getText().toString();
                if (userid.isEmpty()||password.isEmpty()) {
                    new CustomDialog(LoginActivity.this, CustomDialog.DIALOG_CATEGORY.FORM_INVALID, (response, data) -> {

                    }).show();
                    return;
                }
                JSONObject userData = new JSONObject();
                try {
                    userData.put("email", userEmail);
                    userData.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new DatabaseRequest(getBaseContext(), result -> {
                    switch (result[0]) {
                        case "NOT_USER":
                            new CustomDialog(LoginActivity.this, CustomDialog.DIALOG_CATEGORY.EMAIL, (response, data) -> hideNavigationBar()).show();
                            break;
                        case "NOT_PWD":
                            new CustomDialog(LoginActivity.this, CustomDialog.DIALOG_CATEGORY.PASSWORD, (response, data) -> hideNavigationBar()).show();
                            break;
                        default:
                            JSONObject userInfo = new JSONObject();
                            try {
                                JSONArray data = new JSONArray(result[0]);
                                JSONObject user = data.getJSONObject(0);
                                userInfo.put("id", user.getString("user_id"));
                                userInfo.put("name", user.getString("user_name"));
                                userInfo.put("email", user.getString("user_email"));
                                userInfo.put("phone", user.getString("user_phone"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            PreferenceSetting preferenceSetting = new PreferenceSetting(getBaseContext());
                            preferenceSetting.saveUserInfo(userInfo);
                            preferenceSetting.savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, EMAIL);

                            LoadMainPage();
                            break;
                    }
                }).execute(DBRequestType.EMAIL_LOGIN.name(), userData.toString());
                break;
            case R.id.btnNaverLogin:
                mOAuthLoginInstance.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
                break;
            case R.id.iBtnKakaoLogin:
                kakaoLogin();
                break;
            case R.id.btnFacebookLogin:
                callbackManager = CallbackManager.Factory.create();
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                loginManager.registerCallback(callbackManager, facebookLoginCallback);
                break;
//            case R.id.btnKakaoLogoutTest:
//                UserApiClient.getInstance().logout(error -> {
//                    if (error != null) {
//                        Log.e(KAKAO, "로그아웃 실패", error);
//                        Toast.makeText(getApplicationContext(), "로그아웃 실패", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Log.e(KAKAO, "로그아웃 성공, SDK에서 토큰 삭제됨");
//                        Toast.makeText(getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();
//                    }
//                    return null;
//                });
//                DatabaseRequest.ExecuteListener listener = result -> Log.i("Test Result>>>> ", result[0]);
//
//                new DatabaseRequest(getBaseContext(), listener).execute(DBRequestType.TEST.name(), null);
//                break;
            case R.id.iBtnLoginExit:
                LoadMainPage();
                break;
            default:
                break;
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar();
    }

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

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("id", user.getId());
                            jsonObject.put("name", user.getProperties().get("nickname"));
                            jsonObject.put("email", user.getKakaoAccount().getEmail());
                            jsonObject.put("phone", phoneNumber);
                            preferenceSetting.saveUserInfo(jsonObject);
                            UserJoin(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(user.getProperties()).get("nickname")+" 님 로그인", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
                    }
                    return null;
                });
            }
            return null;
        }));
    }

    FacebookLoginCallback.GetFacebookResponse getFacebookResponse = accessToken -> {
        GraphRequest graphRequest;
        graphRequest = GraphRequest.newMeRequest(accessToken,(object, response) -> {
            Log.e("result",object.toString());
            try {
                object.put("phone", phoneNumber);
                new PreferenceSetting(getBaseContext()).saveUserInfo(object);
                UserJoin(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    };


    private void UserJoin(JSONObject object) {
        new DatabaseRequest(getBaseContext(), executeListener).execute(DBRequestType.JOIN.name(), object.toString());
    }

    DatabaseRequest.ExecuteListener executeListener = result -> {
        Log.i("Join Result", result[0]);
        LoadMainPage();
    };

    private void LoadMainPage() {
        Intent intent = new Intent(getBaseContext(), IntermediateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoadMainPage();
    }

    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}