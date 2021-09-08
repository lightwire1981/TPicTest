package com.wellstech.tpictest.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.wellstech.tpictest.R;
import com.wellstech.tpictest.utils.CustomDialog;

import java.util.ArrayList;

public class JoinTermsFragment extends Fragment {

    private final ArrayList<CheckBox> agreeList = new ArrayList<>();
    private boolean isAllCheck = false;

    public enum AGREE_LEVEL {
        PART, ALL
    }

    public JoinTermsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_join_terms, container, false);
        agreeList.add(view.findViewById(R.id.cKbJoinTermsAgree));
        agreeList.add(view.findViewById(R.id.cKbJoinIndividualAgree));
        agreeList.add(view.findViewById(R.id.cKbJoin3rdAgree));
        CheckBox ckbAllAgree = view.findViewById(R.id.cKbJoinTermAllAgree);
        ckbAllAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (CheckBox checkBox : agreeList) {
                    checkBox.setChecked(true);
                }
            }
        });

        for (CheckBox checkBox : agreeList) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (ckbAllAgree.isChecked()) {
                        return;
                    }
                    isAllCheck = agreeList.get(0).isChecked() && agreeList.get(1).isChecked() && agreeList.get(2).isChecked();
                    ckbAllAgree.setChecked(isAllCheck);
                } else {
                    ckbAllAgree.setChecked(false);
                }
            });
        }
        TextView tvwTerm = view.findViewById(R.id.tVwJoinTermsShowContent);
        tvwTerm.setTag(false);
        tvwTerm.setOnClickListener(onClickListener);
        TextView tvwIndi = view.findViewById(R.id.tVwJoinIndiShowContent);
        tvwIndi.setTag(false);
        tvwIndi.setOnClickListener(onClickListener);
        TextView tvw3rd = view.findViewById(R.id.tVwJoin3rdShowContent);
        tvw3rd.setTag(false);
        tvw3rd.setOnClickListener(onClickListener);
        view.findViewById(R.id.btnJoinTermsNext).setOnClickListener(onClickListener);
        view.findViewById(R.id.iBtnJoinTermsExit).setOnClickListener(v -> requireActivity().finish());
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.tVwJoinTermsShowContent:
                if (!(boolean) v.getTag()) {
                    ((TextView)v).setText(getString(R.string.txt_terms_close_content));
                    v.setTag(true);
                    v.getRootView().findViewById(R.id.nsclVwTerms).setVisibility(View.VISIBLE);
                } else {
                    ((TextView)v).setText(getString(R.string.txt_terms_show_content));
                    v.setTag(false);
                    v.getRootView().findViewById(R.id.nsclVwTerms).setVisibility(View.GONE);
                }
                break;
            case R.id.tVwJoinIndiShowContent:
                if (!(boolean) v.getTag()) {
                    ((TextView)v).setText(getString(R.string.txt_terms_close_content));
                    v.setTag(true);
                    v.getRootView().findViewById(R.id.nsclVwIndividual).setVisibility(View.VISIBLE);
                } else {
                    ((TextView)v).setText(getString(R.string.txt_terms_show_content));
                    v.setTag(false);
                    v.getRootView().findViewById(R.id.nsclVwIndividual).setVisibility(View.GONE);
                }
                break;
            case R.id.tVwJoin3rdShowContent:
                if (!(boolean) v.getTag()) {
                    ((TextView)v).setText(getString(R.string.txt_terms_close_content));
                    v.setTag(true);
                    v.getRootView().findViewById(R.id.nsclVw3rd).setVisibility(View.VISIBLE);
                } else {
                    ((TextView)v).setText(getString(R.string.txt_terms_show_content));
                    v.setTag(false);
                    v.getRootView().findViewById(R.id.nsclVw3rd).setVisibility(View.GONE);
                }
                break;
            case R.id.btnJoinTermsNext:
                if (!(agreeList.get(0).isChecked() && agreeList.get(1).isChecked())) {
                    new CustomDialog(requireActivity(), CustomDialog.DIALOG_CATEGORY.JOIN_AGREE_CONFIRM, (response, data) -> {
                        hideNavigationBar();
                    }).show();
                }
                String agreeLevel = AGREE_LEVEL.PART.name();
                if (agreeList.get(2).isChecked()) {
                    agreeLevel = AGREE_LEVEL.ALL.name();
                }
                CertificationFragment certificationFragment = CertificationFragment.newInstance(agreeLevel);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(getId(), certificationFragment).commit();
                break;
            default:
                break;
        }
    };

    private void hideNavigationBar(){
        View decorView = requireActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}