package com.example.tpictest.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpictest.MainActivity;
import com.example.tpictest.R;
import com.example.tpictest.db.DBRequestType;
import com.example.tpictest.db.DatabaseRequest;
import com.example.tpictest.list_code.ListAdapterChildInfo;
import com.example.tpictest.list_code.ListItemChildInfo;
import com.example.tpictest.list_code.RecyclerDecoration;
import com.example.tpictest.utils.PreferenceSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link MyPageChildFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MyPageChildFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private final Map<Integer, String> ChildOrder = new HashMap<>();
    private RecyclerView childListView;
    public static String CHILD_ID = "CHILD_ID";
    String TAG = "MyChildMainFragment";

    public MyPageChildFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MyChildMainFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static MyPageChildFragment newInstance(String param1, String param2) {
////        MyPageChildFragment fragment = new MyPageChildFragment();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        return new MyPageChildFragment();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        String[] childOrder = requireContext().getResources().getStringArray(R.array.txt_my_kids_order);
        for (int index=0; index < childOrder.length; index++) {
            ChildOrder.put(index, childOrder[index]);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setChildInfo();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page_child, container, false);

        view.findViewById(R.id.iBtnChildManagerBack).setOnClickListener(v -> {
            MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(0).getName());
            getParentFragmentManager().popBackStack();
        });
        childListView = view.findViewById(R.id.rcyclVwChildList);
        setLayoutManager(childListView);
        return view;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setChildInfo() {
//        TableLayout tableLayout = view.findViewById(R.id.rcyclVwChildList);
//        Log.i(TAG, tableLayout.getChildCount()+"");
//        TableRow tableRow = (TableRow)tableLayout.getChildAt(0);
//        Log.i(TAG, tableRow.getChildCount()+"");
//
//        ConstraintLayout constraintLayout = (ConstraintLayout)tableRow.getChildAt(0);
//        Log.i(TAG, constraintLayout.getChildCount()+"");
        String userInfo = new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO);
        try {
            JSONObject object = new JSONObject();
            object.put("id", (new JSONObject(userInfo)).get("id"));
            new DatabaseRequest(getContext(),
                    result -> childListView.setAdapter(getChildInfo(result[0]))).execute(DBRequestType.GET_CHILD.name(),
                    object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private ListAdapterChildInfo getChildInfo(String data) {
        ArrayList<ListItemChildInfo> mList = new ArrayList<>();
        if (data.equals("NO_CHILD")) {
            ListItemChildInfo item = new ListItemChildInfo();
            item.setItemCount(1); // for add button setting
            item.setAddAction1(true);
            item.setDrawableAddChild(requireContext().getDrawable(R.drawable.btn_kids_add));
            mList.add(item);
        } else {
            try {
                //    public ArrayList<ListItemChildInfo> childList = new ArrayList<>();
                JSONArray childData = new JSONArray(data);

                int count = childData.length();
                int quotient = count/3;
                int remainder = count%3;

                // 나누어 떨어지면 3개씩 작업
                int index = 0;
                if (quotient > 0) {
                    for (int x=0; x<quotient; x++) {
                        ListItemChildInfo item = new ListItemChildInfo();
                        item.setItemCount(3);
                        JSONObject childInfo = childData.getJSONObject(index);
                        item.setUserId(childInfo.getString("user_id"));
                        item.setChildOrder1(ChildOrder.get(Integer.parseInt(childInfo.getString("child_order"))));
                        item.setChildNick1(childInfo.getString("child_nick"));
                        item.setChildIdx1(childInfo.getString("idx"));
                        index++;
                        childInfo = childData.getJSONObject(index);
                        item.setChildOrder2(ChildOrder.get(Integer.parseInt(childInfo.getString("child_order"))));
                        item.setChildNick2(childInfo.getString("child_nick"));
                        item.setChildIdx2(childInfo.getString("idx"));
                        index++;
                        childInfo = childData.getJSONObject(index);
                        item.setChildOrder3(ChildOrder.get(Integer.parseInt(childInfo.getString("child_order"))));
                        item.setChildNick3(childInfo.getString("child_nick"));
                        item.setChildIdx3(childInfo.getString("idx"));
                        index++;
                        mList.add(item);
                    }
                }
                ListItemChildInfo item = new ListItemChildInfo();
                JSONObject childInfo;
                switch (remainder) {
                    case 0: //(3개 작업하고 남은 데이터가 없으면 추가 버튼 생성)
                        item.setItemCount(1); // for add button setting
                        item.setAddAction1(true);
                        break;
                    case 1:
                        item.setItemCount(2); // child1 + add button
                        childInfo = childData.getJSONObject(index);
                        item.setUserId(childInfo.getString("user_id"));
                        item.setChildOrder1(ChildOrder.get(Integer.parseInt(childInfo.getString("child_order"))));
                        item.setChildNick1(childInfo.getString("child_nick"));
                        item.setChildIdx1(childInfo.getString("idx"));
                        item.setAddAction2(true);
                        break;
                    case 2:
                        item.setItemCount(3); // child2 + add button
                        childInfo = childData.getJSONObject(index);
                        item.setUserId(childInfo.getString("user_id"));
                        item.setChildOrder1(ChildOrder.get(Integer.parseInt(childInfo.getString("child_order"))));
                        item.setChildNick1(childInfo.getString("child_nick"));
                        item.setChildIdx1(childInfo.getString("idx"));
                        index++;
                        childInfo = childData.getJSONObject(index);
                        item.setChildOrder2(ChildOrder.get(Integer.parseInt(childInfo.getString("child_order"))));
                        item.setChildNick2(childInfo.getString("child_nick"));
                        item.setChildIdx2(childInfo.getString("idx"));
                        item.setAddAction3(true);
                        break;
                    default:
                        break;
                }
                item.setDrawableAddChild(requireContext().getDrawable(R.drawable.btn_kids_add));
                mList.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "<<<<<<<<<<<< child info setup done");
        return new ListAdapterChildInfo(mList);
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}