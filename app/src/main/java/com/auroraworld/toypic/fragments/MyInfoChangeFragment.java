package com.auroraworld.toypic.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyInfoChangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyInfoChangeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyInfoChangeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyInfoChangeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyInfoChangeFragment newInstance(String param1, String param2) {
        MyInfoChangeFragment fragment = new MyInfoChangeFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_info_change, container, false);
        view.findViewById(R.id.iBtnMyInfoChangeBack).setOnClickListener(v -> {
            int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
            MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
            getParentFragmentManager().popBackStack();
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.MY_PAGE_SUB;
    }
}