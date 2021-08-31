package com.wellstech.tpictest.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.R;
import com.wellstech.tpictest.db.DBRequestType;
import com.wellstech.tpictest.db.DatabaseRequest;
import com.wellstech.tpictest.list_code.ListAdapterRecentKeyword;
import com.wellstech.tpictest.list_code.ListItemRecentKeyword;
import com.wellstech.tpictest.list_code.RecyclerDecoration;
import com.wellstech.tpictest.utils.PreferenceSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KeywordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KeywordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final ArrayList<RadioButton> keywordGroup = new ArrayList<>();
    private final ArrayList<String> FavoriteKeyword = new ArrayList<>();
    private ArrayList<ListItemRecentKeyword> mList;
    private ConstraintLayout favoriteKeywordView;
    private RecyclerView recentKeywordListView;

    public interface SelectKeywordListener {
        void onSelectKeyword(String keyword);
    }

    private SelectKeywordListener selectKeywordListener;

    public KeywordFragment() {

    }

    public KeywordFragment(SelectKeywordListener selectKeywordListener) {
        this.selectKeywordListener = selectKeywordListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KeywordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KeywordFragment newInstance(String param1, String param2) {
        KeywordFragment fragment = new KeywordFragment();
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
        View view = inflater.inflate(R.layout.fragment_keyword, container, false);
        favoriteKeywordView = view.findViewById(R.id.cLyFavoriteKeyword);
        getFavoriteKeyword();
        recentKeywordListView = view.findViewById(R.id.rcyclVwRecentKeyword);
        setLayoutManager(recentKeywordListView);
        getRecentKeyword();
        return view;
    }

    private void getFavoriteKeyword() {
        new DatabaseRequest(getContext(), result -> {
            if (result[0].equals("NOT_FOUND")) {
                // do favorite keyword setting
                return;
            }
            try {
                JSONArray keywordList = new JSONArray(result[0]);
                for (int i=0; i<keywordList.length(); i++) {
                    FavoriteKeyword.add(keywordList.getJSONObject(i).getString("key_word"));
                }
                for (int i=0; i < favoriteKeywordView.getChildCount(); i++) {
                    RadioButton button = (RadioButton)favoriteKeywordView.getChildAt(i);
                    button.setText(FavoriteKeyword.get(i));
                    button.setOnCheckedChangeListener(listener);
                    keywordGroup.add(button);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_FAVORITE_KEYWORD.name());
    }

    private final CompoundButton.OnCheckedChangeListener listener = (compoundButton, isChecked) -> {
        if (isChecked) {
            for (int index=0; index < keywordGroup.size(); index++) {
                if (compoundButton != keywordGroup.get(index) ) {
                    keywordGroup.get(index).setChecked(false);
                }
            }
            selectKeywordListener.onSelectKeyword(compoundButton.getText().toString());
        }
        textViewStyleChange(isChecked, compoundButton);
    };

    private void textViewStyleChange(boolean checked, CompoundButton radioButton) {
        if (checked) {
            radioButton.setTextAppearance(R.style.FavoriteKeywordSelectStyle);
        } else {
            radioButton.setTextAppearance(R.style.FavoriteKeywordNormalStyle);
        }
    }

    private void getRecentKeyword() {
        try {
            JSONObject data = new JSONObject(new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO));
            String userId = data.getString("id");
            if (userId.isEmpty()) userId = "guest";
            JSONObject userData = new JSONObject();
            userData.put("id", userId);
            new DatabaseRequest(getContext(), result -> {
                if (result[0].equals("NOT_FOUND")) {
                    // do Recent keyword setting
                    return;
                }
                try {
                    JSONArray keywordList = new JSONArray(result[0]);
                    mList = new ArrayList<>();

                    for (int i=0; i<keywordList.length(); i++) {
                        ListItemRecentKeyword item = new ListItemRecentKeyword();
                        item.setKeyWord(keywordList.getJSONObject(i).getString("key_word"));
                        item.setKeyWordId(keywordList.getJSONObject(i).getString("idx"));
                        mList.add(item);
                    }
                    setRecentKeyword();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }).execute(DBRequestType.GET_RECENT_KEYWORD.name(), userData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setRecentKeyword() {
        recentKeywordListView.setAdapter(new ListAdapterRecentKeyword(mList, new ListAdapterRecentKeyword.KeywordSelectListener() {
            @Override
            public void onSelectKeyword(String keyword) {
                selectKeywordListener.onSelectKeyword(keyword);
            }

            @Override
            public void onDeleteKeyword(String keywordId) {
                JSONObject data = new JSONObject();
                try {
                    data.put("keyword_id", keywordId);
                    new DatabaseRequest(getContext(), result -> {
                        if (result[0].equals("UPDATE_OK")) {
                            getRecentKeyword();
                        } else {
                            Toast.makeText(getContext(), "키워드 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }).execute(DBRequestType.DELETE_RECENT_KEYWORD.name(), data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }));

    }
    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}