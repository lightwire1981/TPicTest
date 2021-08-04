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

import com.example.tpictest.MainActivity;
import com.example.tpictest.R;
import com.example.tpictest.code.ListAdapterCustomToy;
import com.example.tpictest.code.ListAdapterNewToy;
import com.example.tpictest.code.ListAdapterRankingToy;
import com.example.tpictest.code.ListAdapterReviewToy;
import com.example.tpictest.code.ListItemCustomToy;
import com.example.tpictest.code.ListItemNewToy;
import com.example.tpictest.code.ListItemRankingToy;
import com.example.tpictest.code.ListItemReviewToy;
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

    private enum ListType {
        CUSTOM, RANK, NEW, REVIEW
    }

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

        RecyclerView customToy1view = view.findViewById(R.id.rcyclVwMainCustomToy1);
        RecyclerView customToy2view = view.findViewById(R.id.rcyclVwMainCustomToy2);
        RecyclerView customToy3view = view.findViewById(R.id.rcyclVwMainCustomToy3);

        setCustomToyList(customToy1view);
        setCustomToyList(customToy2view);
        setCustomToyList(customToy3view);

        RecyclerView rankingToyView = view.findViewById(R.id.rcyclVwMainRankingToy);
        setRankingToyList(rankingToyView);

        RecyclerView newToyView = view.findViewById(R.id.rcyclVwMainNewToy);
        setNewToyList(newToyView);

        RecyclerView reviewToyView = view.findViewById(R.id.rcyclVwMainReviewToy);
        setReviewToyList(reviewToyView);

        view.findViewById(R.id.iBtn_Main_Search).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnMainScrollUp).setOnClickListener(v -> scrollView.fullScroll(ScrollView.FOCUS_UP));

        return view;
    }

    private void setCustomToyList(RecyclerView recyclerView) {
        ArrayList<ListItemCustomToy> mList = new ArrayList<>();
        getCustomToyList(mList);
        recyclerView.setAdapter(new ListAdapterCustomToy(mList));
        setLayoutManager(recyclerView, ListType.CUSTOM);
    }

    private void setRankingToyList(RecyclerView recyclerView) {
        ArrayList<ListItemRankingToy> mList = new ArrayList<>();
        getRankingToyList(mList, "boy");
        recyclerView.setAdapter(new ListAdapterRankingToy(mList));
        setLayoutManager(recyclerView, ListType.RANK);
    }

    private void setNewToyList(RecyclerView recyclerView) {
        ArrayList<ListItemNewToy> mList = new ArrayList<>();
        getNewToyList(mList);
        recyclerView.setAdapter(new ListAdapterNewToy(mList));
        setLayoutManager(recyclerView, ListType.NEW);
    }

    private void setReviewToyList(RecyclerView recyclerView) {
        ArrayList<ListItemReviewToy> mList = new ArrayList<>();
        getReviewToyList(mList);
        recyclerView.setAdapter(new ListAdapterReviewToy(mList));
        setLayoutManager(recyclerView, ListType.REVIEW);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void getCustomToyList(ArrayList<ListItemCustomToy> mList) {

        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), getString(R.string.txt_main_custom_product_name1), "5.0"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), "테스트 상품2", "4.8"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), getString(R.string.txt_main_custom_product_name1), "4.0"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), "테스트 상품4", "4.2"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01), getString(R.string.txt_main_custom_product_name1), "3.8"));

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
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb01),getString(R.string.txt_main_custom_product_name1), "1", "5.0"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_c001_thumb01),getString(R.string.txt_main_custom_product_name1), "2", "4.8"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a004_thumb01),getString(R.string.txt_main_custom_product_name1), "3", "4.7"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a005_thumb01),getString(R.string.txt_main_custom_product_name1), "4", "4.5"));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a002_thumb01),getString(R.string.txt_main_custom_product_name1), "5", "4.2"));
    }

    private ListItemRankingToy addItem(Drawable img, String pName, String rank, String rate) {
        ListItemRankingToy item = new ListItemRankingToy();

        item.setImgDrawable(img);
        item.setProductName(pName);
        item.setRanking(rank);
        item.setRatingNumber(rate);

        return item;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void getNewToyList(ArrayList<ListItemNewToy> mList) {
        mList.add(addITem(new Drawable[]{requireContext().getDrawable(R.drawable.tp_prod_d001_thumb02), requireContext().getDrawable(R.drawable.tp_prod_d002_thumb02)},
                new String[]{requireContext().getString(R.string.txt_main_new_product_name1), requireContext().getString(R.string.txt_main_new_product_name2)}));
        mList.add(addITem(new Drawable[]{requireContext().getDrawable(R.drawable.tp_prod_d001_thumb02), requireContext().getDrawable(R.drawable.tp_prod_d002_thumb02)},
                new String[]{requireContext().getString(R.string.txt_main_new_product_name1), requireContext().getString(R.string.txt_main_new_product_name2)}));
        mList.add(addITem(new Drawable[]{requireContext().getDrawable(R.drawable.tp_prod_d001_thumb02), requireContext().getDrawable(R.drawable.tp_prod_d002_thumb02)},
                new String[]{requireContext().getString(R.string.txt_main_new_product_name1), requireContext().getString(R.string.txt_main_new_product_name2)}));
    }

    private ListItemNewToy addITem(Drawable[] imgs, String[] pNames) {
        ListItemNewToy item = new ListItemNewToy();

        item.setImgProduct1(imgs[0]);
        item.setImgProduct2(imgs[1]);
        item.setNameProduct1(pNames[0]);
        item.setNameProduct2(pNames[1]);

        return item;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void getReviewToyList(ArrayList<ListItemReviewToy> mList) {
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_a001_thumb03), new String[] {"5.0", "250", requireContext().getString(R.string.txt_review_sample)} ));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_e001_thumb03), new String[] {"4.5", "200", requireContext().getString(R.string.txt_review_sample)} ));
        mList.add(addItem(requireContext().getDrawable(R.drawable.tp_prod_f001_thumb03), new String[] {"4.0", "180", requireContext().getString(R.string.txt_review_sample)} ));
    }

    private ListItemReviewToy addItem(Drawable img, String[] values) {
        ListItemReviewToy item = new ListItemReviewToy();

        item.setImgProduct(img);
        item.setNumberRate(values[0]);
        item.setNumberLike(values[1]);
        item.setCommentReview(values[2]);
        return item;
    }

    private void setLayoutManager(RecyclerView recyclerView, ListType type) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        switch (type) {
            case CUSTOM:
                recyclerView.addItemDecoration(new RecyclerDecoration(25, 25));
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                break;
            case RANK:
            case NEW:
            case REVIEW:
                recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                break;
            default:
                break;
        }
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.iBtn_Main_Search:
                FragmentManager fragmentManager = getParentFragmentManager();
                SearchFragment searchFragment = new SearchFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(getId(), searchFragment).commit();
                MainActivity.CURRENT_PAGE = MainActivity.PAGES.SEARCH;
                break;
            case R.id.iBtnSearchBack:
                Toast.makeText(getContext(), R.string.txt_back_message, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    };
}