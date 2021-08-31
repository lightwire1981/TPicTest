package com.wellstech.tpictest.fragments;

import static com.wellstech.tpictest.LoginActivity.KAKAO;
import static com.wellstech.tpictest.LoginActivity.NO_LOGIN;
import static com.facebook.FacebookSdk.getApplicationContext;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wellstech.tpictest.LoginActivity;
import com.wellstech.tpictest.MainActivity;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.utils.CustomDialog;
import com.wellstech.tpictest.utils.PreferenceSetting;
import com.facebook.login.LoginManager;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                                break;
                            default:
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
    private void hideNavigationBar() {
        View decorView = this.requireActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}