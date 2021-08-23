package com.example.tpictest.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.tpictest.ChildRegistActivity;
import com.example.tpictest.MainActivity;
import com.example.tpictest.R;
import com.example.tpictest.db.DBRequestType;
import com.example.tpictest.db.DatabaseRequest;
import com.example.tpictest.list_code.ListItemChildInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageChildFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArrayList<ListItemChildInfo> childList = new ArrayList<>();
    String TAG = "MyChildMainFragment";

    public MyPageChildFragment() {
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
    public static MyPageChildFragment newInstance(String param1, String param2) {
        MyPageChildFragment fragment = new MyPageChildFragment();
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
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "액티비티 복귀", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page_child, container, false);

        view.findViewById(R.id.iBtnChildManagerBack).setOnClickListener(onClickListener);

        setChildInfo(view);

        return view;
    }

    private void setChildInfo(View view) {
//        TableLayout tableLayout = view.findViewById(R.id.rcyclVwChildList);
//        Log.i(TAG, tableLayout.getChildCount()+"");
//        TableRow tableRow = (TableRow)tableLayout.getChildAt(0);
//        Log.i(TAG, tableRow.getChildCount()+"");
//
//        ConstraintLayout constraintLayout = (ConstraintLayout)tableRow.getChildAt(0);
//        Log.i(TAG, constraintLayout.getChildCount()+"");

        getChild();

        if (childList.size() > 0) {

        } else {

//            ImageButton imageButton = (ImageButton) constraintLayout.getChildAt(0);
//            imageButton.setImageDrawable(requireContext().getDrawable(R.drawable.btn_kids_add));
//            imageButton.setOnClickListener(onClickListener);
        }
    }

    private void getChild() {
        new DatabaseRequest(getContext(), result -> {
            if (result[0].equals("NO_CHILD")) {
                return;
            }
            try {
                JSONArray childList = new JSONArray(result[0]);
                for (int i=0; i<childList.length(); i++) {
                    JSONObject chilInfo = childList.getJSONObject(i);
                    ListItemChildInfo item = new ListItemChildInfo();
                    item.setUserId(chilInfo.get("user_id").toString());

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_CHILD.name());
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.iBtnChildManagerBack:
                MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(0).getName());
                getParentFragmentManager().popBackStack();
                break;
            default:
                if (v.getTag() == null){
                    Intent intent = new Intent(requireContext(), ChildRegistActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//            new CustomDialog(getContext(), CustomDialog.DIALOG_CATEGORY.ADD_CHILD).show();
                }
                break;
        }

    };
}