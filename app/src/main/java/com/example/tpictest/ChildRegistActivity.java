package com.example.tpictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tpictest.fragments.CharacterSelectFragment;
import com.example.tpictest.fragments.ChildRegistFragment;
import com.example.tpictest.utils.CustomDialog;

import org.json.JSONObject;

public class ChildRegistActivity extends AppCompatActivity {


    public enum CHILD_REGISTRY_STEP {
        ADD,
        CHARACTER,
        PERSONALITY
    }
    public static CHILD_REGISTRY_STEP RegistryStep;

    public static JSONObject CHILD_DATA = new JSONObject();

    private static final String TAG = "ChildRegistActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_regist);


        FragmentManager fragmentManager = getSupportFragmentManager();
        ChildRegistFragment childRegistFragment = new ChildRegistFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fLyChildRegistMain, childRegistFragment).commit();

        findViewById(R.id.btnChildRegistNext).setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar();
        RegistryStep = CHILD_REGISTRY_STEP.ADD;
    }

    private final View.OnClickListener onClickListener = v -> {
        Log.d(TAG, RegistryStep.name());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(RegistryStep) {
            case ADD:
                Log.i(TAG, CHILD_DATA.toString());
                if (!CHILD_DATA.has("child_nick")) {
                    new CustomDialog(ChildRegistActivity.this, CustomDialog.DIALOG_CATEGORY.FORM_INVALID).show();
                    return;
                }
                CharacterSelectFragment characterSelectFragment = new CharacterSelectFragment();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.fLyChildRegistMain, characterSelectFragment).commit();
                break;
            case CHARACTER:
                CharacterSelectFragment.getSelectedChar();
                Log.i(TAG, CHILD_DATA.toString());
                break;
            case PERSONALITY:
                break;
            default:
                break;
        }
    };

    @Override
    public void onBackPressed() {
        switch (RegistryStep) {
            case ADD:
                break;
            case CHARACTER:
                CHILD_DATA.remove("child_character");
                RegistryStep = CHILD_REGISTRY_STEP.ADD;
                break;
            case PERSONALITY:
                RegistryStep = CHILD_REGISTRY_STEP.CHARACTER;
                break;
            default:
                break;
        }
        super.onBackPressed();
    }

    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}