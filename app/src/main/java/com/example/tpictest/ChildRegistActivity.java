package com.example.tpictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.tpictest.fragments.CharacterSelectFragment;
import com.example.tpictest.fragments.ChildRegistFragment;
import com.example.tpictest.fragments.HomeFragment;

public class ChildRegistActivity extends AppCompatActivity {


    enum CHILD_REGISTRY_STEP {
        ADD,
        CHARACTER,
        PERSONALITY
    }
    public static CHILD_REGISTRY_STEP registryStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_regist);
        hideNavigationBar();

        FragmentManager fragmentManager = getSupportFragmentManager();
        ChildRegistFragment childRegistFragment = new ChildRegistFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fLyChildRegistMain, childRegistFragment).commit();

        findViewById(R.id.btnChildRegistNext).setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registryStep = CHILD_REGISTRY_STEP.ADD;
    }

    private final View.OnClickListener onClickListener = v -> {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(registryStep) {
            case ADD:
                CharacterSelectFragment characterSelectFragment = new CharacterSelectFragment();
                fragmentTransaction.addToBackStack(CHILD_REGISTRY_STEP.ADD.name());
                fragmentTransaction.add(R.id.fLyChildRegistMain, characterSelectFragment).commit();
                break;
            case CHARACTER:
                break;
            case PERSONALITY:
                break;
            default:
                break;
        }

    };

    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}