package com.auroraworld.toypic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.fragments.CategoryFragment;
import com.auroraworld.toypic.fragments.EvaluateFragment;
import com.auroraworld.toypic.fragments.HomeFragment;
import com.auroraworld.toypic.fragments.MyPageFragment;
import com.auroraworld.toypic.fragments.RankingFragment;
import com.auroraworld.toypic.utils.CustomDialog;
import com.auroraworld.toypic.utils.PreferenceSetting;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public enum PAGES {
        HOME, CATEGORY, EVALUATE, RANKING, MY_PAGE, MY_CHILD, MY_CHILD_EDIT, MY_REVIEW, MY_PAGE_SUB, SEARCH, SETTING
    }

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public static PAGES CURRENT_PAGE;
    private static final String TAG = "TAG-MainActivity";
    public static String ALL_GOODS_INFO;

    private static RadioGroup homeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO));

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        homeRadioGroup = findViewById(R.id.rGrpMainRadioButton);
        homeRadioGroup.setOnCheckedChangeListener(homeTapChangeListener);
        homeRadioGroup.getChildAt(0).performClick();
//        findViewById(R.id.iBtnMainHome).setOnClickListener(onClickListener);
//        findViewById(R.id.iBtnMainRank).setOnClickListener(onClickListener);
//        findViewById(R.id.iBtnMainEvaluate).setOnClickListener(onClickListener);
//        findViewById(R.id.iBtnMainCategory).setOnClickListener(onClickListener);
//        findViewById(R.id.iBtnMainMypage).setOnClickListener(onClickListener);
        new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.POPUP_IMAGE, (response, data) -> {
            hideNavigationBar();
            switch (data.toString()) {
                case "close":
                    Log.i(TAG, data.toString());
                    break;
                case "today":
                    // set today action
                    break;
            }
        }, setPopupContent()).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private ArrayList<Bitmap> setPopupContent() {
        ArrayList<Bitmap> contentList = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.popup_sample01);
        list.add(R.drawable.popup_sample02);
        list.add(R.drawable.popup_sample03);
        list.add(R.drawable.popup_sample04);
        list.add(R.drawable.popup_sample05);

        for (int i : list) {
            Glide.with(getBaseContext()).asBitmap().load(i).listener(
                    new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            contentList.add(resource);
                            return true;
                        }
                    }).submit();
        }
        return contentList;
    }

    @SuppressLint("NonConstantResourceId")
    private final RadioGroup.OnCheckedChangeListener homeTapChangeListener = (radioGroup, radiobuttonId) -> {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (radiobuttonId) {
            case R.id.rBtnMainHome:
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.replace(R.id.fLyMain, homeFragment).commit();
                break;
            case R.id.rBtnMainCategory:
                CategoryFragment categoryFragment = new CategoryFragment();
                fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(R.id.fLyMain, categoryFragment).commit();
                break;
            case R.id.rBtnMainEvaluate:
                EvaluateFragment evaluateFragment = new EvaluateFragment();
                fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                if (new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE).equals(LoginActivity.NO_LOGIN)) {
                    new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.LOGIN, (response, data) -> {
                        if (response) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            tabChanger(fragmentManager);
                            hideNavigationBar();
                        }
                    }).show();
                } else {
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.add(R.id.fLyMain, evaluateFragment).commit();
                }
                break;
            case R.id.rBtnMainRanking:
                RankingFragment rankingFragment = new RankingFragment();
                fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(R.id.fLyMain, rankingFragment).commit();
                break;
            case R.id.rBtnMainMypage:
                MyPageFragment myPageFragment = new MyPageFragment();
                fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                if (new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE).equals(LoginActivity.NO_LOGIN)) {
                    new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.LOGIN, (response, data) -> {
                        if (response) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            tabChanger(fragmentManager);
                            hideNavigationBar();
                        }
                    }).show();
                } else {
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.add(R.id.fLyMain, myPageFragment).commit();
                }
                break;
        }
    };

    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    View.OnClickListener onClickListener = v -> {
        Log.i(TAG, CURRENT_PAGE.name());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.iBtnMainHome:
                if (CURRENT_PAGE.equals(PAGES.HOME)) {
                    return;
                }
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.replace(R.id.fLyMain, homeFragment).commit();
                break;
            case R.id.iBtnMainCategory:
                Log.i(TAG, CURRENT_PAGE.name());
                if (CURRENT_PAGE.equals(PAGES.CATEGORY)) {
                    return;
                }
                CategoryFragment categoryFragment = new CategoryFragment();
                fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(R.id.fLyMain, categoryFragment).commit();
                break;
            case R.id.iBtnMainEvaluate:
                Log.i(TAG, CURRENT_PAGE.name());
                if (CURRENT_PAGE.equals(PAGES.EVALUATE)) {
                    return;
                }
                if (new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE).equals(LoginActivity.NO_LOGIN)) {
                    new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.LOGIN, (response, data) -> {
                        if (response) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            hideNavigationBar();
                        }
                    }).show();
                } else {
                    EvaluateFragment evaluateFragment = new EvaluateFragment();
                    fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.add(R.id.fLyMain, evaluateFragment).commit();
                }
                break;
            case R.id.iBtnMainRank:
                Log.i(TAG, CURRENT_PAGE.name());
                if (CURRENT_PAGE.equals(PAGES.RANKING)) {
                    return;
                }
                RankingFragment rankingFragment = new RankingFragment();
                fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(R.id.fLyMain, rankingFragment).commit();
                break;
            case R.id.iBtnMainMypage:
                if (CURRENT_PAGE.equals(PAGES.MY_PAGE)) {
                    return;
                }
                if (new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE).equals(LoginActivity.NO_LOGIN)) {
                    new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.LOGIN, (response, data) -> {
                        if (response) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            hideNavigationBar();
                        }
                    }).show();
                } else {
                    MyPageFragment myPageFragment = new MyPageFragment();
                    fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.add(R.id.fLyMain, myPageFragment).commit();
//                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                }
                break;
            default:
                break;
        }
    };

    public static void tabChanger(FragmentManager fragmentManager) {
        int fragmentCount = fragmentManager.getBackStackEntryCount();
        if (fragmentCount > 0) {
            fragmentManager.popBackStack();
            homeRadioGroup.getChildAt(PAGES.valueOf(CURRENT_PAGE.name()).ordinal()).performClick();
        } else {
            homeRadioGroup.getChildAt(0).performClick();
        }
    }

    public static void CallRankingFragment() {
        homeRadioGroup.getChildAt(3).performClick();
    }



    @Override
    public void onBackPressed() {
        new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.EXIT, (isAppFinish, data) -> hideNavigationBar()).show();
    }

    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//                decorView.setSystemUiVisibility(uiOptions);
            } else {

            }
        });
    }
}