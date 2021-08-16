package com.example.tpictest.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.tpictest.ChildRegistActivity;
import com.example.tpictest.R;
import com.example.tpictest.utils.CustomDialog;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyChildMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyChildMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String TAG = "MyChildMainFragment";

    public MyChildMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyChildMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyChildMainFragment newInstance(String param1, String param2) {
        MyChildMainFragment fragment = new MyChildMainFragment();
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_child_main, container, false);

        TableLayout tableLayout = view.findViewById(R.id.tLyKidsTable);
        Log.i(TAG, tableLayout.getChildCount()+"");

        for (int index = 0; index < 4; index++) {

        }
        TableRow tableRow = (TableRow)tableLayout.getChildAt(0);
        Log.i(TAG, tableRow.getChildCount()+"");
        ConstraintLayout constraintLayout = (ConstraintLayout)tableRow.getChildAt(0);
        Log.i(TAG, constraintLayout.getChildCount()+"");
        ImageButton imageButton = (ImageButton) constraintLayout.getChildAt(0);
        imageButton.setImageDrawable(requireContext().getDrawable(R.drawable.btn_kids_add));
        imageButton.setOnClickListener(onClickListener);
        return view;
    }

    private final View.OnClickListener onClickListener = v -> {
        if (v.getTag() == null){
                Intent intent = new Intent(getContext(), ChildRegistActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//            new CustomDialog(getContext(), CustomDialog.DIALOG_CATEGORY.ADD_CHILD).show();
        }
    };
}