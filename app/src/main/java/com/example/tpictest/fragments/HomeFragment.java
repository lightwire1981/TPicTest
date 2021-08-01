package com.example.tpictest.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.tpictest.R;
import com.example.tpictest.code.ListAdapterCustomToy;
import com.example.tpictest.code.ListAdapterRankingToy;
import com.example.tpictest.code.ListItemCustomToy;
import com.example.tpictest.code.ListItemRankingToy;
import com.example.tpictest.code.RecyclerDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("NonConstantResourceId")
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ScrollView scrollView = view.findViewById(R.id.scrlVwMain);
        scrollView.addView(inflater.inflate(R.layout.layout_main, scrollView, false));

        RecyclerView view1 = view.findViewById(R.id.rcyclVwMainCustomToy1);
        RecyclerView view2 = view.findViewById(R.id.rcyclVwMainCustomToy2);
        RecyclerView view3 = view.findViewById(R.id.rcyclVwMainCustomToy3);

        setCustomToyList(view1);
        setCustomToyList(view2);
        setCustomToyList(view3);

        RecyclerView rankingView = view.findViewById(R.id.rcyclVwMainRankingToy);
        setRankingToyList(rankingView);


        view.findViewById(R.id.iBtn_Main_Search).setOnClickListener(onClickListener);

        return view;
    }

    private void setCustomToyList(RecyclerView recyclerView) {
        ArrayList<ListItemCustomToy> mList = new ArrayList<>();
        getCustomToyList(mList);

        recyclerView.addItemDecoration(new RecyclerDecoration(25, 25));
        recyclerView.setAdapter(new ListAdapterCustomToy(mList));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setRankingToyList(RecyclerView recyclerView) {
        ArrayList<ListItemRankingToy> mList = new ArrayList<>();
        getRankingToyList(mList, "boy");

        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        recyclerView.setAdapter(new ListAdapterRankingToy(mList));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void getCustomToyList(ArrayList<ListItemCustomToy> mList) {

        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), getString(R.string.product_name), "5.0"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), "테스트 상품2", "4.8"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), getString(R.string.product_name), "4.0"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), "테스트 상품4", "4.2"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), getString(R.string.product_name), "3.8"));

    }

    private ListItemCustomToy addItem(Drawable img, String pName, String predict) {
        ListItemCustomToy item = new ListItemCustomToy();

        item.setImgDrawable(img);
        item.setProductName(pName);
        item.setPredictNumber(predict);

        return item;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void getRankingToyList(ArrayList<ListItemRankingToy> mList, String category) {
        /*
            get Ranking Data by [category]
         */
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01),getString(R.string.product_name), "1", "5.0"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_c001_thumb01),getString(R.string.product_name), "2", "4.8"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a004_thumb01),getString(R.string.product_name), "3", "4.7"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a005_thumb01),getString(R.string.product_name), "4", "4.5"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a002_thumb01),getString(R.string.product_name), "5", "4.2"));
    }

    private ListItemRankingToy addItem(Drawable img, String pName, String rank, String rate) {
        ListItemRankingToy item = new ListItemRankingToy();

        item.setImgDrawable(img);
        item.setProductName(pName);
        item.setRanking(rank);
        item.setRatingNumber(rate);

        return item;
    }

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.iBtn_Main_Search:
                FragmentManager fragmentManager = getParentFragmentManager();
                SearchFragment searchFragment = new SearchFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(getId(), searchFragment).commit();
                break;
            case R.id.iBtnSearchBack:
                Toast.makeText(getContext(), R.string.txt_back_message, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    };
}