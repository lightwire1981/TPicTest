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
import com.auroraworld.toypic.list_code.ListAdapterCharSelect;
import com.auroraworld.toypic.list_code.ListItemCharSelect;
import com.auroraworld.toypic.list_code.RecyclerDecoration;
import com.auroraworld.toypic.utils.CustomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharacterEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CHILD_ID = "CHILD_ID";

    // TODO: Rename and change types of parameters
    private String childId;

    private RecyclerView editCharacterListView;
    private ListAdapterCharSelect listAdapterCharSelect;

    public CharacterEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param childId Parameter 1.
     * @return A new instance of fragment CharacterEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharacterEditFragment newInstance(String childId) {
        CharacterEditFragment fragment = new CharacterEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_character_edit, container, false);
        editCharacterListView = view.findViewById(R.id.rcyclVwCharEditList);
        setLayoutManager(editCharacterListView);

        view.findViewById(R.id.btnCharEditComplete).setOnClickListener(onClickListener);
        view.findViewById(R.id.iBtnCharacterEditBack).setOnClickListener(onClickListener);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setCharacterListTemp(editCharacterListView);
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
    }


    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btnCharEditComplete:
                String selectedChar = getSelectedChar();
                if (selectedChar.isEmpty()) return;
                JSONObject editedData = new JSONObject();
                try {
                    editedData.put("idx", childId);
                    editedData.put("child_character", selectedChar);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new DatabaseRequest(getContext(), result -> {
                    if (result[0].equals("UPDATE_OK")) {
                        Toast.makeText(getContext(), "캐릭터 정보가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), "캐릭터 정보 변경이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).execute(DBRequestType.UPDATE_CHILD_CHAR.name(), editedData.toString());
                break;
            case R.id.iBtnCharacterEditBack:
//                int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
//                MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
                getParentFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    };

    private String getSelectedChar() {
        Map<String, String> data = ((ListAdapterCharSelect) Objects.requireNonNull(editCharacterListView.getAdapter())).getSelectedChar();
        if (data.size() < 3) {
            new CustomDialog(getContext(), CustomDialog.DIALOG_CATEGORY.SELECT_INVALID).show();
            return "";
        }
        StringBuilder selectedChar = new StringBuilder();
        int index = 0;
        for (String key : data.keySet()) {
            selectedChar.append(data.get(key));
            index++;
            if (index < data.size()) {
                selectedChar.append(",");
            }
        }
        return selectedChar.toString();
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
    }
}