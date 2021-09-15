package com.auroraworld.toypic.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.utils.ChildOrderConvert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageChildEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageChildEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CHILD_ID = "param1";
    private static final String CHILD_ALL_INFO = "param2";

    // TODO: Rename and change types of parameters
    private String childId;
    private String childAllInfo;

    private EditText childNickVw;
    private CalendarView childBirthVw;

    private String childBirth = "";

    private final String TAG = this.getClass().getName();

    public MyPageChildEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageChildEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageChildEditFragment newInstance(String param1, String param2) {
        MyPageChildEditFragment fragment = new MyPageChildEditFragment();
        Bundle args = new Bundle();
        args.putString(CHILD_ID, param1);
        args.putString(CHILD_ALL_INFO, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childId = getArguments().getString(CHILD_ID);
            childAllInfo = getArguments().getString(CHILD_ALL_INFO);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.MY_CHILD_EDIT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_page_child_edit, container, false);
        childNickVw = view.findViewById(R.id.eTxtChildNickName);
        childBirthVw = view.findViewById(R.id.calendarVwChildBirth);
        childBirthVw.setOnDateChangeListener(onDateChangeListener);
        JSONObject child = getChild();
        if (child == null) {
            Log.e(TAG, "NO Child error");
            return view;
        }
        setChildInfo(view, child);
        view.findViewById(R.id.iBtnChildEditBack).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnChildEdit).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnChildDelete).setOnClickListener(onClickListener);
        return view;
    }

    /**
     * 수정하려는 자녀 선택
     * @return 선택 된 자녀 정보
     */
    private JSONObject getChild() {
        try {
            JSONArray childList = new JSONArray(childAllInfo);
            for (int index=0; index < childList.length(); index++) {
                JSONObject child = childList.getJSONObject(index);
                if (child.getString("idx").equals(childId)) {
                    return child;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setChildInfo(View view, JSONObject child) {
        try {
            String order = ChildOrderConvert.ConvertIntToOrder(requireContext(), child.getString("child_order"));
            ((TextView)view.findViewById(R.id.tVwChildEditTitle)).setText(getString(R.string.txt_kids_order_suffix, order));
            childNickVw.setText(child.getString("child_nick"));
            childBirth = child.getString("child_birth");
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                Date birth = dateFormat.parse(childBirth);
                childBirthVw.setDate(Objects.requireNonNull(birth).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String[] favorChars = child.getString("child_character").split(",");
            ((TextView)view.findViewById(R.id.tVwChildFavorChar)).setText(getString(R.string.txt_kids_favor_char_count, favorChars.length+""));
            ((TextView)view.findViewById(R.id.tVwChildPersonality)).setText(child.getString("child_personality"));
            ((TextView)view.findViewById(R.id.tVwChildEvaluateAvgr)).setText(getString(R.string.txt_kids_evaluate_template, "0", "0"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private final CalendarView.OnDateChangeListener onDateChangeListener = (view, year, month, dayOfMonth) -> {
        String dateMonth = (month+1 >= 10) ? String.valueOf(month+1):"0"+(month+1);
        String dateDay = (dayOfMonth >= 10) ? String.valueOf(dayOfMonth):"0"+dayOfMonth;
        childBirth = year+"-"+dateMonth+"-"+dateDay;
    };

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.iBtnChildEditBack:
                fragmentBack();
                break;
            case R.id.btnChildEdit:
                JSONObject editedData = new JSONObject();
                try {
                    editedData.put("idx", childId);
                    editedData.put("child_nick", childNickVw.getText().toString());
                    editedData.put("child_birth", childBirth);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new DatabaseRequest(getContext(), result -> {
                    if (result[0].equals("UPDATE_OK")) {
                        Toast.makeText(getContext(), "자녀 정보가 변경되었습니다.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "자녀 정보 변경이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).execute(DBRequestType.UPDATE_CHILD.name(), editedData.toString());
                break;
            case R.id.btnChildDelete:
                JSONObject deleteChild = new JSONObject();
                try {
                    deleteChild.put("idx", childId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new DatabaseRequest(getContext(), result -> {
                    if (result[0].equals("OK")) {
                        Toast.makeText(getContext(), "자녀 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        fragmentBack();
                    } else {
                        Toast.makeText(getContext(), "자녀 정보 삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).execute(DBRequestType.DELETE_CHILD.name(), deleteChild.toString());
                break;
            default:
                break;
        }
    };
    private void fragmentBack() {
//        int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
//        MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
//        getParentFragmentManager().popBackStack();

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyPageChildFragment myPageChildFragment = new MyPageChildFragment();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(getId(), myPageChildFragment).commit();
    }
}