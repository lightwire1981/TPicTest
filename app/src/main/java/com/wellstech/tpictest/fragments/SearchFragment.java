package com.wellstech.tpictest.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wellstech.tpictest.MainActivity;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.db.DBRequestType;
import com.wellstech.tpictest.db.DatabaseRequest;
import com.wellstech.tpictest.utils.PreferenceSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("NonConstantResourceId")
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText etxtSearchKeyword;
    private TextView tvwKeyword, tvwMiddle, tvwSearchResult, tvwSuffix;

    private final String TAG = getClass().getName();


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        view.findViewById(R.id.iBtnSearchBack).setOnClickListener(onClickListener);
        view.findViewById(R.id.iBtnWordDelete).setOnClickListener(onClickListener);
        view.findViewById(R.id.iBtnSearchStart).setOnClickListener(onClickListener);
        tvwKeyword = view.findViewById(R.id.tVwSearchKeyword);
        tvwMiddle = view.findViewById(R.id.tVwSearchMiddleText);
        tvwSearchResult = view.findViewById(R.id.tVwSearchResultCount);
        tvwSuffix = view.findViewById(R.id.tVwSearchResultSuffix);

        etxtSearchKeyword = view.findViewById(R.id.eTxtSearchKeywords);
        etxtSearchKeyword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) hideNavigationBar();
        });

        KeywordFragment keywordFragment = new KeywordFragment(keyword -> etxtSearchKeyword.setText(keyword));
        getParentFragmentManager().beginTransaction().replace(R.id.fLySearchMain, keywordFragment).commit();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.SEARCH;
    }

    private final DatabaseRequest.ExecuteListener listener = result -> {
        if (result[0].equals("INSERT_FAIL")) {
            return;
        }
        if (result[0].equals("NOT_FOUND")) {
            tvwSearchResult.setVisibility(View.INVISIBLE);
            tvwSuffix.setVisibility(View.INVISIBLE);
            tvwKeyword.setText(getString(R.string.txt_search_keyword_template, etxtSearchKeyword.getText()));
            tvwMiddle.setText(getString(R.string.txt_search_result_none));
            return;
        }
        try {
            JSONArray goodsArray = new JSONArray(result[0]);
            Log.i(TAG, goodsArray.toString());
            tvwSearchResult.setVisibility(View.VISIBLE);
            tvwSuffix.setVisibility(View.VISIBLE);
            tvwKeyword.setText(getString(R.string.txt_search_keyword_template, etxtSearchKeyword.getText()));
            tvwMiddle.setText(getString(R.string.txt_search_result_get));
            tvwSearchResult.setText(getString(R.string.txt_null, goodsArray.length()+""));

            SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(goodsArray.toString());
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.fLySearchMain, searchResultFragment).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.iBtnSearchStart:
                String keyWord = etxtSearchKeyword.getText().toString();
                String userId;
                if (keyWord.isEmpty()) return;
                try {
                    String info = new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO);
                    JSONObject data;
                    if (info.equals("")) {
                        userId = "guest";
                    } else {
                        data  = new JSONObject(info);
                        userId = data.getString("id");
                    }
                    data = new JSONObject();
                    data.put("id", userId);
                    data.put("key_word", keyWord);

                    new DatabaseRequest(getContext(), listener).execute(DBRequestType.SEARCH_GOODS.name(), data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iBtnWordDelete:
                etxtSearchKeyword.setText("");
                KeywordFragment keywordFragment = new KeywordFragment(keyword -> etxtSearchKeyword.setText(keyword));
                getParentFragmentManager().beginTransaction().replace(R.id.fLySearchMain, keywordFragment).commit();
                break;
            case R.id.iBtnSearchBack:
//                FragmentManager fragmentManager = getParentFragmentManager();
//                HomeFragment homeFragment = new HomeFragment();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(getId(), homeFragment).commit();
                int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
                MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
//                if (getParentFragment() != null) {
//                    ((HomeFragment)getParentFragment()).setAdSlider();
//                }
                getParentFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    };



    private void hideNavigationBar(){
        View decorView = requireActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}