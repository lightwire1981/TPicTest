package com.auroraworld.toypic.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.auroraworld.toypic.LoginActivity;
import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.utils.PreferenceSetting;

import org.json.JSONException;
import org.json.JSONObject;

public class MyPageFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        try {
            JSONObject jsonObject = new JSONObject(new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO));
            ((TextView)view.findViewById(R.id.tVwMyPageUserName)).setText(getString(R.string.txt_my_page_sir, jsonObject.get("name")));
            ((TextView)view.findViewById(R.id.tVwMyPageUserMail)).setText(jsonObject.get("email").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.findViewById(R.id.btnMyPageLike).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnMyPageReview).setOnClickListener(onClickListener);
        view.findViewById(R.id.iBtnMyPageChangeInfo).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnMyPageChild).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnMyPageNotice).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnMyPageSetting).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnLoginCall).setOnClickListener(onClickListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.MY_PAGE;
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (v.getId()) {
            case R.id.btnMyPageLike:
                MyPageLikeFragment myPageLikeFragment = MyPageLikeFragment.newInstance(
                        PreferenceSetting.loadPreference(requireContext(), PreferenceSetting.PREFERENCE_KEY.USER_ID));
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                fragmentTransaction.add(getId(), myPageLikeFragment).commit();
                break;
            case R.id.btnMyPageReview:
                MyPageReviewFragment myPageReviewFragment = new MyPageReviewFragment();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                fragmentTransaction.add(getId(), myPageReviewFragment).commit();
                break;
            case R.id.iBtnMyPageChangeInfo:
                MyInfoChangeFragment myInfoChangeFragment = new MyInfoChangeFragment();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                fragmentTransaction.add(getId(), myInfoChangeFragment).commit();
                break;
            case R.id.btnMyPageChild:
                MyPageChildFragment myPageChildFragment = new MyPageChildFragment();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                fragmentTransaction.replace(getId(), myPageChildFragment).commit();
                break;
            case R.id.btnMyPageNotice:
                NoticeFragment noticeFragment = new NoticeFragment();
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(getId(), noticeFragment).commit();
                break;
            case R.id.btnMyPageSetting:
                SettingFragment settingFragment = new SettingFragment();
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(getId(), settingFragment).commit();
                break;
            case R.id.btnLoginCall:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                break;
        }
    };
}