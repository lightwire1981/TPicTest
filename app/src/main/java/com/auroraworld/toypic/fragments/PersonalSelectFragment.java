package com.auroraworld.toypic.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auroraworld.toypic.ChildRegistActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.list_code.ListAdapterPersonSelect;
import com.auroraworld.toypic.list_code.ListItemPersonSelect;
import com.auroraworld.toypic.list_code.RecyclerDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalSelectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static RecyclerView personalityList;
    private ListAdapterPersonSelect listAdapterPersonSelect;

    public PersonalSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalSelectFragment newInstance(String param1, String param2) {
        PersonalSelectFragment fragment = new PersonalSelectFragment();
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
        ChildRegistActivity.RegistryStep = ChildRegistActivity.CHILD_REGISTRY_STEP.PERSONALITY;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_select, container, false);

        personalityList = view.findViewById(R.id.rcyclVwPersonalityList);

        setPersonalityListTemp(personalityList);
        return view;
    }

    private void setPersonalityListTemp(RecyclerView recyclerView) {
        ArrayList<ListItemPersonSelect> mList = new ArrayList<>();
        String[] name = getResources().getStringArray(R.array.txt_kids_personal);

        // 성향 7개 가정하여 셋팅
        int index=0;

        for (int i=0; i < 2; i++) {
            ListItemPersonSelect item = new ListItemPersonSelect();
            item.setItemCount(3);
//            item.setPsnlDrawable1(requireContext().getDrawable(R.drawable.tp_icon_brand09_off));
            item.setPsnlName1(name[index]);
            index++;
            // do set drawable2
            item.setPsnlName2(name[index]);
            index++;
            // do set drawable3
            item.setPsnlName3(name[index]);
            index++;
            mList.add(item);
        }
        ListItemPersonSelect item = new ListItemPersonSelect();
        item.setItemCount(1);
        item.setPsnlName1(name[index]);
        mList.add(item);

        listAdapterPersonSelect = new ListAdapterPersonSelect(mList);
        recyclerView.setAdapter(listAdapterPersonSelect);
        setLayoutManager(recyclerView);
    }


    private void setPersonalityList(RecyclerView recyclerView) {
        ArrayList<ListItemPersonSelect> mList = new ArrayList<>();

        JSONArray jsonArray = getPersonalityData();
        int count = jsonArray.length();

        int quotient = count/3;
        int remainder = count%3;

        if (quotient < 1) {
            for (int index=0; index < jsonArray.length(); index++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    ListItemPersonSelect item = new ListItemPersonSelect();
                    item.setItemCount(count);
                    // do ListItem work
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            int index = 0;
            for (int x = 0; x < quotient; x++) {
                ListItemPersonSelect item = new ListItemPersonSelect();
                item.setItemCount(3);
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    // do ListItem work
                    index++;
                    jsonObject = jsonArray.getJSONObject(index);
                    // do ListItem work
                    index++;
                    jsonObject = jsonArray.getJSONObject(index);
                    // do ListItem work
                    index++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mList.add(item);
            }

            // after quotient work index is full of 3x value -> ex) 3, 6, 9, 12 ...
            // remainder is only 0 or 1 or 2
            ListItemPersonSelect item = new ListItemPersonSelect();
            switch (remainder) {
                case 1:
                    item.setItemCount(1);
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(index);
                        // do ListItem work
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mList.add(item);
                    break;
                case 2:
                    item.setItemCount(2);
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(index);
                        // do ListItem work
                        index++;
                        jsonObject = jsonArray.getJSONObject(index);
                        // do ListItem work
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mList.add(item);
                    break;
                default:
                    break;
            }
        }
    }

    private JSONArray getPersonalityData() {

        // do Database Work;

        return new JSONArray();
    }

    public static int getSelectedPerson() {
        Map<String, String> data = ((ListAdapterPersonSelect) Objects.requireNonNull(personalityList.getAdapter())).getSelectedPerson();
        if (data.size() < 1) {
            ChildRegistActivity.CHILD_DATA.remove("child_personality");
            return 0;
        }
        StringBuilder selectedPerson = new StringBuilder();
        int index = 0;
        for (String key : data.keySet()) {
            selectedPerson.append(data.get(key));
            index++;
            if (index < data.size()) {
                selectedPerson.append(",");
            }
        }

        try {
            ChildRegistActivity.CHILD_DATA.put("child_personality", selectedPerson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.size();
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
    }
}