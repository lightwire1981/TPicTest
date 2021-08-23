package com.example.tpictest.fragments;

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

import com.example.tpictest.LoginActivity;
import com.example.tpictest.MainActivity;
import com.example.tpictest.R;
import com.example.tpictest.utils.PreferenceSetting;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
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
        view.findViewById(R.id.iBtnMyPageChangeInfo).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnMyPageChild).setOnClickListener(onClickListener);
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
            case R.id.iBtnMyPageChangeInfo:
                MyInfoChangeFragment myInfoChangeFragment = new MyInfoChangeFragment();
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(getId(), myInfoChangeFragment).commit();
                break;
            case R.id.btnMyPageChild:
                MyPageChildFragment myPageChildFragment = new MyPageChildFragment();
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                fragmentTransaction.add(getId(), myPageChildFragment).commit();
                break;
            case R.id.btnMyPageSetting:
                SettingFragment settingFragment = new SettingFragment();
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
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