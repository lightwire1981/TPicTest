package com.auroraworld.toypic.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.list_code.ListAdapterRCategory;
import com.auroraworld.toypic.list_code.ListAdptRankingGoods;
import com.auroraworld.toypic.list_code.ListItemRCategory;
import com.auroraworld.toypic.list_code.ListItemRankingGoods;
import com.auroraworld.toypic.list_code.RecyclerDecoration;

import org.json.JSONArray;
import org.json.JSONException;

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

    private RecyclerView goodsRankingList;

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
        setLayoutManager(rankingCategory);
        setRankCategory(rankingCategory);

        goodsRankingList = view.findViewById(R.id.rcyclVwRankToyList);
        setLayoutManager(goodsRankingList);

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
        recyclerView.setAdapter(new ListAdapterRCategory(mList, categoryName -> setRankGoods(categoryName, goodsRankingList)));
    }

    private void setRankGoods(String categoryNm, RecyclerView recyclerView) {

        new DatabaseRequest(getContext(), result -> {
            JSONArray goodsList = new JSONArray();
            try {
                goodsList = new JSONArray(result[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (goodsList.length() < 1) {
                return;
            }
            ArrayList<ListItemRankingGoods> mList = new ArrayList<>();
            for (int index=0; index < goodsList.length(); index++) {
                ListItemRankingGoods item = new ListItemRankingGoods();
                // temporary setting
                item.setGoodsCategory(categoryNm);
                try {
                    item.setItem(goodsList.getJSONObject(index));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mList.add(item);
            }
            recyclerView.setAdapter(new ListAdptRankingGoods(mList));
        }).execute(DBRequestType.GET_RANKING_GOODS_MORE.name());
    }

    @SuppressLint("NonConstantResourceId")
    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        switch(recyclerView.getId()) {
            case R.id.rcyclVwRankCategory:
                recyclerView.addItemDecoration(new RecyclerDecoration(5, 10));
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
                int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
                MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
                getParentFragmentManager().popBackStack();
                MainActivity.tabChanger(getParentFragmentManager());
                break;
            default:
                break;
        }
    };
}