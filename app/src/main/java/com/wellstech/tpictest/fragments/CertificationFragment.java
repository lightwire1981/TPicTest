package com.wellstech.tpictest.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wellstech.tpictest.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CertificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CertificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String AgreeLevel = "AgreeLevel";

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
        return view;
    }
}