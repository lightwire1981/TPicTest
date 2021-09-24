package com.auroraworld.toypic.fragments;

import static com.auroraworld.toypic.LoginActivity.KAKAO;
import static com.auroraworld.toypic.LoginActivity.NO_LOGIN;
import static com.facebook.FacebookSdk.getApplicationContext;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.auroraworld.toypic.LoginActivity;
import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.utils.CustomDialog;
import com.auroraworld.toypic.utils.PreferenceSetting;
import com.facebook.login.LoginManager;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.SETTING;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        String userMail = "";
        try {
            JSONObject userInfo = new JSONObject(new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO));
            userMail = userInfo.get("email").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((TextView)view.findViewById(R.id.tVwSettingUserEmail)).setText(userMail);
        view.findViewById(R.id.btnSettingLogout).setOnClickListener(onClickListener);
        view.findViewById(R.id.iBtnSettingBack).setOnClickListener(onClickListener);
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btnSettingLogout:
                new CustomDialog(getActivity(), CustomDialog.DIALOG_CATEGORY.LOGOUT, ((response, data) -> {
                    if (response) {
                        String loginType = new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE);
                        switch (loginType) {
                            case LoginActivity.NAVER:
                                OAuthLogin mOAuthLoginModule = OAuthLogin.getInstance();
                                mOAuthLoginModule.logout(getContext());
                                Log.e(KAKAO, "로그아웃, 로컬 토큰 삭제됨");
                                new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, NO_LOGIN);
                                new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO, null);
                                Toast.makeText(getApplicationContext(), getString(R.string.naver_logout), Toast.LENGTH_SHORT).show();
                                LoadHome();
                                break;
                            case KAKAO:
                                UserApiClient.getInstance().logout(error -> {
                                    if (error != null) {
                                        Log.e(KAKAO, "로그아웃 실패", error);
                                        Toast.makeText(getApplicationContext(), "로그아웃 실패", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e(KAKAO, "로그아웃 성공, SDK에서 토큰 삭제됨");
                                        new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, NO_LOGIN);
                                        new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO, null);
                                        Toast.makeText(getApplicationContext(), getString(R.string.kakao_logout), Toast.LENGTH_SHORT).show();
                                        LoadHome();
                                    }
                                    return null;
                                });
                                break;
                            case LoginActivity.FACEBOOK:
                                LoginManager.getInstance().logOut();
                                Log.e(KAKAO, "로그아웃 성공, SDK에서 토큰 삭제됨");
                                new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, NO_LOGIN);
                                new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO, null);
                                Toast.makeText(getApplicationContext(), getString(R.string.facebook_logout), Toast.LENGTH_SHORT).show();
                                LoadHome();
                                break;
                            default:
                                new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, NO_LOGIN);
                                new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO, null);
                                Toast.makeText(getApplicationContext(), getString(R.string.email_logout), Toast.LENGTH_SHORT).show();
                                LoadHome();
                                break;
                        }
                    } else {
                        hideNavigationBar();
                    }
                })).show();
                break;
            case R.id.iBtnSettingBack:
                int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
                MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
                getParentFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    };

    private void LoadHome() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.fLyMain, homeFragment).commit();
        MainActivity.tabChanger();
    }

    private void hideNavigationBar() {
        View decorView = this.requireActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}