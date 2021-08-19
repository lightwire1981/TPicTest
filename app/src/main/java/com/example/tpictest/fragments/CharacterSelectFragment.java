package com.example.tpictest.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tpictest.ChildRegistActivity;
import com.example.tpictest.R;
import com.example.tpictest.list_code.ListAdapterCharSelect;
import com.example.tpictest.list_code.ListItemCharSelect;
import com.example.tpictest.list_code.RecyclerDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharacterSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterSelectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static RecyclerView characterList;
    private ListAdapterCharSelect listAdapterCharSelect;

    public CharacterSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharacterSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharacterSelectFragment newInstance(String param1, String param2) {
        CharacterSelectFragment fragment = new CharacterSelectFragment();
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
        View view = inflater.inflate(R.layout.fragment_character_select, container, false);

        characterList = view.findViewById(R.id.rcyclVwCharacterList);
//        setCharacterList(characterList);
        setCharacterListTemp(characterList);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ChildRegistActivity.RegistryStep = ChildRegistActivity.CHILD_REGISTRY_STEP.CHARACTER;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setCharacterListTemp(RecyclerView recyclerView) {
        ArrayList<ListItemCharSelect> mList = new ArrayList<>();
        String[] name = getResources().getStringArray(R.array.txt_kids_char);

        // 캐릭터 11개 가정하여 셋팅
        int index = 0;

        for (int i = 0; i < 3; i++) {
            ListItemCharSelect item = new ListItemCharSelect();
            item.setItemCount(3);
            item.setCharDrawable1(requireContext().getDrawable(R.drawable.tp_icon_brand09_off));
            item.setCharName1(name[index]);
            index++;
            item.setCharDrawable2(requireContext().getDrawable(R.drawable.tp_icon_brand09_off));
            item.setCharName2(name[index]);
            index++;
            item.setCharDrawable3(requireContext().getDrawable(R.drawable.tp_icon_brand09_off));
            item.setCharName3(name[index]);
            index++;
            mList.add(item);
        }
        ListItemCharSelect item = new ListItemCharSelect();
        item.setItemCount(2);
        item.setCharDrawable1(requireContext().getDrawable(R.drawable.tp_icon_brand09_off));
        item.setCharName1(name[index]);
        index++;
        item.setCharDrawable2(requireContext().getDrawable(R.drawable.tp_icon_brand09_off));
        item.setCharName2(name[index]);
        mList.add(item);

        listAdapterCharSelect = new ListAdapterCharSelect(mList);
        recyclerView.setAdapter(listAdapterCharSelect);
        setLayoutManager(recyclerView);
    }

    private void setCharacterList(RecyclerView recyclerView) {
        ArrayList<ListItemCharSelect> mList = new ArrayList<>();

        JSONArray jsonArray = getCharacterData();
        int count = jsonArray.length();

        int quotient = count/3;
        int remainder = count%3;

        if (quotient < 1) {
            for (int index=0; index < jsonArray.length(); index++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    ListItemCharSelect item = new ListItemCharSelect();
                    item.setItemCount(count);
                    // do ListItem work

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            int index = 0;
            for (int x = 0; x < quotient; x++) {
                ListItemCharSelect item = new ListItemCharSelect();
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
            ListItemCharSelect item = new ListItemCharSelect();
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

    private JSONArray getCharacterData() {

        // do Database Work;

        return new JSONArray();
    }

    public static int getSelectedChar() {
        Map<String, String> data =  ((ListAdapterCharSelect) Objects.requireNonNull(characterList.getAdapter())).getSelectedChar();
        if (data.size() < 1) {
            ChildRegistActivity.CHILD_DATA.remove("child_character");
            return 0;
        }
        StringBuilder selectedChar= new StringBuilder();
        int index =0;
        for (String key : data.keySet()) {
            selectedChar.append(data.get(key));
            index++;
            if (index < data.size()) {
                selectedChar.append(",");
            }
        }

        try {
            ChildRegistActivity.CHILD_DATA.put("child_character", selectedChar.toString());
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