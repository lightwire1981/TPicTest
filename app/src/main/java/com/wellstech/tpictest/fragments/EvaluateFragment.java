package com.wellstech.tpictest.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.MainActivity;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.db.DBRequestType;
import com.wellstech.tpictest.db.DatabaseRequest;
import com.wellstech.tpictest.list_code.ListAdapterEvalChild;
import com.wellstech.tpictest.list_code.ListItemEvalChild;
import com.wellstech.tpictest.list_code.RecyclerDecoration;
import com.wellstech.tpictest.utils.ChildOrderConvert;
import com.wellstech.tpictest.utils.PreferenceSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EvaluateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView evaluateChildList;
    private ArrayList<CheckBox> ChildBoxList;

    public EvaluateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluateFragment newInstance(String param1, String param2) {
        EvaluateFragment fragment = new EvaluateFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluate, container, false);
        evaluateChildList = view.findViewById(R.id.rcyclVwEvaluateChild);
        setLayoutManager(evaluateChildList);

        view.findViewById(R.id.iBtnEvaluateBack).setOnClickListener(button -> {
            int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
            MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
            getParentFragmentManager().popBackStack();
        });
        return view;
    }
    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(15, 15));
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        setChildList();
    }

    private void setChildList() {
        String userInfo = new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO);
        try {
            JSONObject object = new JSONObject();
            object.put("id", (new JSONObject(userInfo)).get("id"));
            new DatabaseRequest(getContext(), result -> {
                if(result[0].equals("NO_CHILD")){
                    // do no child action
                }
                ArrayList<ListItemEvalChild> mList = new ArrayList<>();
                try {
                    JSONArray list = new JSONArray(result[0]);
                    for(int index=0; index<list.length(); index++) {
                        JSONObject child = list.getJSONObject(index);
                        ListItemEvalChild item = new ListItemEvalChild();
                        item.setChildIdx(child.getString("idx"));
                        item.setChildOrder(ChildOrderConvert.ConvertIntToOrder(requireContext(), child.getString("child_order")));
                        item.setChildNick(child.getString("child_nick"));
                        mList.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ListAdapterEvalChild listAdapterEvalChild = new ListAdapterEvalChild(mList, result[0], checkBoxSelectListener);
                ChildBoxList = listAdapterEvalChild.getCheckBoxes();
                evaluateChildList.setAdapter(listAdapterEvalChild);
            }).execute(DBRequestType.GET_CHILD.name(),
                    object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ListAdapterEvalChild.CheckBoxSelectListener checkBoxSelectListener = position -> {
        int boxCount = ChildBoxList.size();
        Log.i("<<<<<<<< 체크 박스 갯수", boxCount+"");
        int index = 0;
        for (CheckBox checkBox : ChildBoxList) {
            if (index != position) {
                checkBox.setChecked(false);
            }
            index++;
        }
    };
}