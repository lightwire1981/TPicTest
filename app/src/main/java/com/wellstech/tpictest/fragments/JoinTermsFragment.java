package com.wellstech.tpictest.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wellstech.tpictest.R;

import java.util.ArrayList;

public class JoinTermsFragment extends Fragment {

    private ArrayList<CheckBox> agreeList = new ArrayList<>();
    private boolean isAllCheck = false;

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
        agreeList.add((CheckBox)view.findViewById(R.id.cKbJoinTermsAgree));
        agreeList.add((CheckBox)view.findViewById(R.id.cKbJoinIndividualAgree));
        agreeList.add((CheckBox)view.findViewById(R.id.cKbJoin3rdAgree));
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

        view.findViewById(R.id.iBtnJoinTermsExit).setOnClickListener(v -> requireActivity().finish());

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    private View.OnClickListener onClickListener = v -> {
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
            default:
                break;
        }
    };
}