package com.auroraworld.toypic.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.list_code.ListAdapterPersonSelect;
import com.auroraworld.toypic.list_code.ListItemPersonSelect;
import com.auroraworld.toypic.list_code.RecyclerDecoration;
import com.auroraworld.toypic.utils.CustomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CHILD_ID = "CHILD_ID";

    // TODO: Rename and change types of parameters
    private String childId;

    private RecyclerView editPersonalityListView;

    public PersonalEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param childId Parameter 1.
     * @return A new instance of fragment PersonalEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalEditFragment newInstance(String childId) {
        PersonalEditFragment fragment = new PersonalEditFragment();
        Bundle args = new Bundle();
        args.putString(CHILD_ID, childId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childId = getArguments().getString(CHILD_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_edit, container, false);
        editPersonalityListView = view.findViewById(R.id.rcyclVwPersonEditList);
        setLayoutManager(editPersonalityListView);

        view.findViewById(R.id.btnPersonEditComplete).setOnClickListener(onClickListener);
        view.findViewById(R.id.iBtnPersonalEditBack).setOnClickListener(onClickListener);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setPersonalityListTemp(editPersonalityListView);
    }

    private void setPersonalityListTemp(RecyclerView recyclerView) {
        ArrayList<ListItemPersonSelect> mList = new ArrayList<>();
        String[] name = getResources().getStringArray(R.array.txt_kids_personal);

        // 성향 7개 가정하여 셋팅
        int index=0;

        for (int i=0; i < 2; i++) {
            ListItemPersonSelect item = new ListItemPersonSelect();
            item.setItemCount(3);
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

        ListAdapterPersonSelect listAdapterPersonSelect = new ListAdapterPersonSelect(mList);
        recyclerView.setAdapter(listAdapterPersonSelect);
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btnPersonEditComplete:
                String selectedPerson = getSelectedPerson();
                if (selectedPerson.isEmpty()) return;
                JSONObject editedData = new JSONObject();
                try {
                    editedData.put("idx", childId);
                    editedData.put("child_personality", selectedPerson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new DatabaseRequest(getContext(), result -> {
                    if (result[0].equals("UPDATE_OK")) {
                        Toast.makeText(getContext(), "성향 정보가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), "성향 정보 변경이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).execute(DBRequestType.UPDATE_CHILD_PERSON.name(), editedData.toString());
                break;
            case R.id.iBtnPersonalEditBack:
                getParentFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    };

    private String getSelectedPerson() {
        Map<String, String> data = ((ListAdapterPersonSelect) Objects.requireNonNull(editPersonalityListView.getAdapter())).getSelectedPerson();
        if (data.size() < 1 || data.size() > 3) {
            new CustomDialog(requireContext(), CustomDialog.DIALOG_CATEGORY.SELECT_INVALID).show();
            return "";
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
        return selectedPerson.toString();
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
    }
}