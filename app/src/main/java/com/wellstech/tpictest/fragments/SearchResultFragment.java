package com.wellstech.tpictest.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SEARCH_RESULT = "searchResult";

    // TODO: Rename and change types of parameters
    private String searchResult;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param searchResult Parameter 1.
     * @return A new instance of fragment SearchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance(String searchResult) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_RESULT, searchResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchResult = getArguments().getString(SEARCH_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        Spinner resultCategorySpnr = view.findViewById(R.id.sPnrResultCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), R.layout.spnr_item_result_category, getResultCategory()
        );
        adapter.setDropDownViewResource(R.layout.spnr_item_result_category);
        resultCategorySpnr.setAdapter(adapter);
        resultCategorySpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner orderPopularSpnr = view.findViewById(R.id.sPnrOrderPopular);
        adapter = new ArrayAdapter<>(
                getContext(), R.layout.spnr_item_order_popular,
                (requireContext().getResources().getStringArray(R.array.array_order_popular))
        );
        adapter.setDropDownViewResource(R.layout.spnr_item_order_popular);
        orderPopularSpnr.setAdapter(adapter);
        orderPopularSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rcyclVwSearchResultList);

        return view;
    }

    private ArrayList<String> getResultCategory() {
        ArrayList<String> temp = new ArrayList<>();

        // get category from Goods Array of Search Result

        temp.add("전체");
        return temp;
    }
}