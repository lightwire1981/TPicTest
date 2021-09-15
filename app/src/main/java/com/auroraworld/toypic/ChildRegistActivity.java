package com.auroraworld.toypic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.fragments.CharacterSelectFragment;
import com.auroraworld.toypic.fragments.ChildRegistFragment;
import com.auroraworld.toypic.fragments.PersonalSelectFragment;
import com.auroraworld.toypic.utils.CustomDialog;
import com.auroraworld.toypic.utils.PreferenceSetting;

import org.json.JSONException;
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
        int selectCount;
        switch(RegistryStep) {
            case ADD:
                Log.i(TAG, CHILD_DATA.toString());
                if (!CHILD_DATA.has("child_nick")) {
                    new CustomDialog(ChildRegistActivity.this, CustomDialog.DIALOG_CATEGORY.FORM_INVALID).show();
                    return;
                }
                CharacterSelectFragment characterSelectFragment = new CharacterSelectFragment();
                fragmentTransaction.addToBackStack(CHILD_REGISTRY_STEP.ADD.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(R.id.fLyChildRegistMain, characterSelectFragment).commit();
                break;
            case CHARACTER:
                selectCount = CharacterSelectFragment.getSelectedChar();
                Log.i(TAG, CHILD_DATA.toString());
//                if (!CHILD_DATA.has("child_character")) {
//                    // Alert Dialog Show
//                    return;
//                }
                if (selectCount < 3) {
                    // Alert Dialog Show
                    new CustomDialog(ChildRegistActivity.this, CustomDialog.DIALOG_CATEGORY.SELECT_INVALID).show();
                    return;
                }
                PersonalSelectFragment personalSelectFragment = new PersonalSelectFragment();
                fragmentTransaction.addToBackStack(CHILD_REGISTRY_STEP.CHARACTER.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(R.id.fLyChildRegistMain, personalSelectFragment).commit();
                break;
            case PERSONALITY:
                selectCount = PersonalSelectFragment.getSelectedPerson();
                Log.i(TAG, CHILD_DATA.toString());
                if (!CHILD_DATA.has("child_personality")||selectCount > 3) {
                    // Alert Dialog show
                    new CustomDialog(ChildRegistActivity.this, CustomDialog.DIALOG_CATEGORY.SELECT_INVALID).show();
                    return;
                }
                ChildRegistration(CHILD_DATA);
                break;
            default:
                break;
        }
    };

    private void ChildRegistration(JSONObject object) {
        String userInfo = new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO);
        try {
            JSONObject jsonObject = new JSONObject(userInfo);
            object.put("id", jsonObject.get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new DatabaseRequest(getBaseContext(), executeListener).execute(DBRequestType.CREATE_CHILD.name(), object.toString());
    }

    private DatabaseRequest.ExecuteListener executeListener = result -> {
        Log.i("Join Result", result[0]);
        if (result[0].equals("OK")) {
            Toast.makeText(getBaseContext(), "자녀가 등록되었습니다.", Toast.LENGTH_SHORT).show();
            while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            }
            finish();
        } else {
            Toast.makeText(getBaseContext(), "자녀가 등록되지 못했습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        switch (RegistryStep) {
            case CHARACTER:
                CHILD_DATA.remove("child_character");
                RegistryStep = CHILD_REGISTRY_STEP.ADD;
                break;
            case PERSONALITY:
                CHILD_DATA.remove("child_personality");
                RegistryStep = CHILD_REGISTRY_STEP.CHARACTER;
                break;
            default:
                Log.i(TAG, "<<<<<<<<<<<< check RegistryStep");
                break;
        }
        super.onBackPressed();
    }

    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}