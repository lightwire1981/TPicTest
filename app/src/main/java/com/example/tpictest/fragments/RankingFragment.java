package com.example.tpictest.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tpictest.MainActivity;
import com.example.tpictest.R;
import com.example.tpictest.list_code.ListAdapterRCategory;
import com.example.tpictest.list_code.ListAdptRankingToy;
import com.example.tpictest.list_code.ListItemRCategory;
import com.example.tpictest.list_code.ListItemRankingToy;
import com.example.tpictest.list_code.RecyclerDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
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
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        RecyclerView rankingCategory = view.findViewById(R.id.rcyclVwRankCategory);
        setRankCategory(rankingCategory);
        RecyclerView rankingProduct = view.findViewById(R.id.rcyclVwRankToyList);
        setRankProduct(rankingProduct);

        view.findViewById(R.id.iBtnRankingBack).setOnClickListener(onClickListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.RANKING;
    }

    private void setRankCategory(RecyclerView recyclerView) {
        ArrayList<ListItemRCategory> mList = new ArrayList<>();

        String[] categoryList = {
                getString(R.string.btn_rank_total),
                getString(R.string.btn_rank_boy),
                getString(R.string.btn_rank_girl),
                getString(R.string.btn_rank_baby),
                getString(R.string.btn_rank_board),
                getString(R.string.btn_rank_outdoor),
                getString(R.string.btn_rank_lego),
                getString(R.string.btn_rank_stuffedtoy),
                getString(R.string.btn_rank_character)
        };

        for (String list : categoryList) {
            ListItemRCategory item = new ListItemRCategory();
            item.setRankCategory(list);
            mList.add(item);
        }
        recyclerView.setAdapter(new ListAdapterRCategory(mList));
        setLayoutManager(recyclerView);
    }

    private void setRankProduct(RecyclerView recyclerView) {
        ArrayList<ListItemRankingToy> mList = new ArrayList<>();
        getRankingToyList(mList);
        recyclerView.setAdapter(new ListAdptRankingToy(mList));
        setLayoutManager(recyclerView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void getRankingToyList(ArrayList<ListItemRankingToy> mList) {
        for (int index=0; index < 30; index++) {
            ListItemRankingToy item = new ListItemRankingToy();

            item.setNumberRank(String.valueOf(index+1));
            item.setImgProduct(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01));
            item.setRankCategory(getString(R.string.btn_rank_boy));
            item.setNameProduct(getString(R.string.txt_rank_product_name1));
            item.setPriceProduct(getString(R.string.txt_rank_product_price1));
            item.setNumberRate(getString(R.string.txt_rating_default));
            item.setReviewCount(getString(R.string.txt_rank_product_reviewcount));

            mList.add(item);
        }
    }


    @SuppressLint("NonConstantResourceId")
    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        switch(recyclerView.getId()) {
            case R.id.rcyclVwRankCategory:
                recyclerView.addItemDecoration(new RecyclerDecoration(25, 25));
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                break;
            case R.id.rcyclVwRankToyList:
                recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                break;
            default:
                break;
        }
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.iBtnRankingBack:
                getParentFragmentManager().popBackStack();
                MainActivity.CURRENT_PAGE = MainActivity.PAGES.HOME;
                break;
            default:
                break;
        }
    };
}