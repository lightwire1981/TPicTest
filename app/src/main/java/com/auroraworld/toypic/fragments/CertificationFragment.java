package com.auroraworld.toypic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.auroraworld.toypic.ChildRegistActivity;
import com.auroraworld.toypic.LoginActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.utils.MakeDate;
import com.auroraworld.toypic.utils.PreferenceSetting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CertificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CertificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String AgreeLevel = "AgreeLevel";
    private String emailForm;
    private boolean emailCheck = false;
    private boolean pwFormCheck = false;
    private boolean passwordCheck = false;

    private final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 500; // time in milliseconds between successive task executions.

    // TODO: Rename and change types of parameters
    private String agreeLevel;

    public CertificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param agreeLevel Parameter 1.
     * @return A new instance of fragment CertificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CertificationFragment newInstance(String agreeLevel) {
        CertificationFragment fragment = new CertificationFragment();
        Bundle args = new Bundle();
        args.putString(AgreeLevel, agreeLevel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            agreeLevel = getArguments().getString(AgreeLevel);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_certification, container, false);
        Spinner spnEmailform = view.findViewById(R.id.sPnrEmailForm);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), R.layout.spnr_item_email_form, getResources().getStringArray(R.array.txt_email_form));
        spnEmailform.setAdapter(adapter);
        spnEmailform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), (String)adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                emailForm = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        view.findViewById(R.id.btnUserPhoneCheck).setOnClickListener(view1 -> {
            view.findViewById(R.id.eTxtUserNameCheck).setEnabled(false);
            view.findViewById(R.id.eTxtUserPhoneCheck).setEnabled(false);
        });
        EditText etxtEmail = view.findViewById(R.id.eTxtUserEmailCheck);
        etxtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 5 && editable.toString().length() < 20) {
                    emailCheck = Pattern.matches("^[a-zA-Z0-9]+$", editable.toString());
                    if (emailCheck) {
                        view.findViewById(R.id.tVwEmailFormWarning).setVisibility(View.INVISIBLE);
                    } else {
                        view.findViewById(R.id.tVwEmailFormWarning).setVisibility(View.VISIBLE);
                    }
                } else {
                    view.findViewById(R.id.tVwEmailFormWarning).setVisibility(View.VISIBLE);
                }
            }
        });
        EditText etxtPassword = view.findViewById(R.id.eTxtUserPasswordCheck);
        etxtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 5 && editable.toString().length() < 20) {
                    pwFormCheck = Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$", editable.toString());
                    if (pwFormCheck) {
                        view.findViewById(R.id.tVwPasswordFormWarning).setVisibility(View.INVISIBLE);
                    } else {
                        view.findViewById(R.id.tVwPasswordFormWarning).setVisibility(View.VISIBLE);
                    }
                } else {
                    view.findViewById(R.id.tVwPasswordFormWarning).setVisibility(View.VISIBLE);
                }
            }
        });
        EditText etxtPasswordCompare = view.findViewById(R.id.eTxtUserPasswordCompare);
        etxtPasswordCompare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals(etxtPassword.getText().toString())) {
                    view.findViewById(R.id.tVwPasswordCompareWarning).setVisibility(View.INVISIBLE);
                    passwordCheck = true;
                } else {
                    view.findViewById(R.id.tVwPasswordCompareWarning).setVisibility(View.VISIBLE);
                    passwordCheck = false;
                }
            }
        });
        view.findViewById(R.id.btnJoinDone).setOnClickListener(view12 -> {
            String userId = MakeDate.makeDateString(MakeDate.DATE_TYPE.ID) + etxtEmail.getText().toString();
            String userName = ((EditText) view.findViewById(R.id.eTxtUserNameCheck)).getText().toString();
            String userPhone = ((EditText) view.findViewById(R.id.eTxtUserPhoneCheck)).getText().toString();
            String userEmail = etxtEmail.getText().toString()+"@"+emailForm;
            String userPwd = etxtPassword.getText().toString();
            JSONObject userData = new JSONObject();
            try {
                userData.put("id", userId);
                userData.put("name", userName);
                userData.put("email", userEmail);
                userData.put("phone", userPhone);
                userData.put("agree", agreeLevel.equals(JoinTermsFragment.AGREE_LEVEL.ALL.name()) ? "1":"0");
                userData.put("password", userPwd);
                userData.put("type", "Email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new DatabaseRequest(getContext(), result -> {
                if (result[0].equals("EXIST_USER")) {
                    Toast.makeText(getContext(), "가입된 이메일", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (result[0].equals("INSERT_OK")) {
                    new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO, userData.toString());
                    new PreferenceSetting(getContext()).savePreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE, LoginActivity.EMAIL);
                    Intent intent = new Intent(getContext(), ChildRegistActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "가입 실패", Toast.LENGTH_SHORT).show();
                }
            }).execute(DBRequestType.JOIN_EMAIL.name(), userData.toString());
        });
        joinButtonCheck(view);

        return view;
    }

    private void joinButtonCheck(View view) {
        Timer timer = new Timer();

        Handler handler = new Handler();
        Runnable update = () -> view.findViewById(R.id.btnJoinDone).setEnabled(emailCheck && pwFormCheck && passwordCheck);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);

    }
}