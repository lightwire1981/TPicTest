package com.example.tpictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.tpictest.fragments.HomeFragment;
import com.example.tpictest.fragments.RankingFragment;

public class MainActivity extends AppCompatActivity {

    public enum PAGES {
        HOME, CATEGORY, EVALUATE, RANKING, MY_PAGE, SEARCH
    }

    public static PAGES CURRENT_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CURRENT_PAGE = PAGES.HOME;
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fLyMain, homeFragment).commit();


        findViewById(R.id.iBtn_MHome).setOnClickListener(onClickListener);
        findViewById(R.id.iBtn_MRank).setOnClickListener(onClickListener);

    }

    @SuppressLint("NonConstantResourceId")
    View.OnClickListener onClickListener = v -> {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.iBtn_MHome:
                if (CURRENT_PAGE.equals(PAGES.HOME)) {
                    return;
                }
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.fLyMain, homeFragment).commit();
                CURRENT_PAGE = PAGES.HOME;
                break;
            case R.id.iBtn_MRank:
                if (CURRENT_PAGE.equals(PAGES.RANKING)) {
                    return;
                }
                RankingFragment rankingFragment = new RankingFragment();
                fragmentTransaction.replace(R.id.fLyMain, rankingFragment).commit();
                CURRENT_PAGE = PAGES.RANKING;
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