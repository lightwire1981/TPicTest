package com.auroraworld.toypic.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.auroraworld.toypic.LoginActivity;
import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.ReviewShowActivity;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.list_code.ListAdapterADSlider;
import com.auroraworld.toypic.list_code.ListAdapterCustomToy;
import com.auroraworld.toypic.list_code.ListAdapterNewToy;
import com.auroraworld.toypic.list_code.ListAdapterReviewToy;
import com.auroraworld.toypic.list_code.ListAdptMainRankingToy;
import com.auroraworld.toypic.list_code.ListItemCustomToy;
import com.auroraworld.toypic.list_code.ListItemMainRankingToy;
import com.auroraworld.toypic.list_code.ListItemNewToy;
import com.auroraworld.toypic.list_code.ListItemReviewToy;
import com.auroraworld.toypic.list_code.RecyclerDecoration;
import com.auroraworld.toypic.utils.PreferenceSetting;
import com.auroraworld.toypic.utils.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("NonConstantResourceId")
public class HomeFragment extends Fragment {

    //region ValueSetting
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

    private ViewPager2 adView;
    private Timer timer;

    private final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    ArrayList<CheckBox> RankingBoxList = new ArrayList<>();
    public static ListItemReviewToy goodsReview;

    private final String TAG = getClass().getSimpleName();

    //endregion

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
        scrollView.addView(inflater.inflate(R.layout.content_home, scrollView, false));

        adView = view.findViewById(R.id.vPgrHomeAD);
        TextView adPages = view.findViewById(R.id.tVwADtotalPage);
        TextView adCurrentPage = view.findViewById(R.id.tVwADcurrentPage);

        adView.setAdapter(new ListAdapterADSlider(setADPages(adPages)));
        adView.setPageTransformer(new ZoomOutPageTransformer());
        adView.setCurrentItem(1000);
        setCurrentADPage(adCurrentPage, 0);
        adView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentADPage(adCurrentPage, position%2);
            }
        });

        String isLogin = new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE);

        if (LoginActivity.NO_LOGIN.equals(isLogin)) {
            view.findViewById(R.id.cLyHomeCustomLabel).setVisibility(View.GONE);
            view.findViewById(R.id.lLyCustomToyList).setVisibility(View.GONE);
        } else {
            try {
                JSONObject jsonObject = new JSONObject(new PreferenceSetting(getContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO));
                ((TextView) view.findViewById(R.id.tVwHomeCustomUsername)).setText(getString(R.string.txt_main_custom_toy, jsonObject.get("name").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RecyclerView customToy1view = view.findViewById(R.id.rcyclVwMainCustomToy1);
            RecyclerView customToy2view = view.findViewById(R.id.rcyclVwMainCustomToy2);
            RecyclerView customToy3view = view.findViewById(R.id.rcyclVwMainCustomToy3);

            setLayoutManager(customToy1view, ListType.CUSTOM);
            setLayoutManager(customToy2view, ListType.CUSTOM);
            setLayoutManager(customToy3view, ListType.CUSTOM);
            setCustomToyList(customToy1view);
            setCustomToyList(customToy2view);
            setCustomToyList(customToy3view);

        }
        view.findViewById(R.id.tVwHomeShowRanking).setOnClickListener(view1 -> MainActivity.CallRankingFragment());
        RecyclerView rankingToyView = view.findViewById(R.id.rcyclVwMainRankingToy);
        setLayoutManager(rankingToyView, ListType.RANK);
        RankingBoxList.add(view.findViewById(R.id.cKbHomeRankBoy));
        RankingBoxList.add(view.findViewById(R.id.cKbHomeRankGirl));
        RankingBoxList.add(view.findViewById(R.id.cKbHomeRankBaby));
        RankingBoxList.add(view.findViewById(R.id.cKbHomeRankBoard));
        RankingBoxList.add(view.findViewById(R.id.cKbHomeRankOutdoor));
        RankingBoxList.add(view.findViewById(R.id.cKbHomeRankLego));
        RankingBoxList.add(view.findViewById(R.id.cKbHomeRankStuffedtoy));
        RankingBoxList.add(view.findViewById(R.id.cKbHomeRankCharacter));
        for (CheckBox cbox : RankingBoxList) {
            cbox.setOnCheckedChangeListener((checkBox, isChecked) -> {
                if (isChecked) {
                    int id = checkBox.getId();
                    for (CheckBox cb : RankingBoxList) {
                        if (cb.getId()!=id) {
                            cb.setChecked(false);
                        }
                    }
                    setRankingToyList(rankingToyView, id);
                }
            });
        }
        setRankingToyList(rankingToyView, R.id.cKbHomeRankBoy);

        RecyclerView newToyView = view.findViewById(R.id.rcyclVwMainNewToy);
        setLayoutManager(newToyView, ListType.NEW);
        setNewToyList(newToyView);

        RecyclerView reviewToyView = view.findViewById(R.id.rcyclVwMainReviewToy);
        setLayoutManager(reviewToyView, ListType.REVIEW);
        setReviewToyList(reviewToyView);

        view.findViewById(R.id.iBtn_Main_Search).setOnClickListener(onClickListener);
        view.findViewById(R.id.btnMainScrollUp).setOnClickListener(v -> scrollView.fullScroll(ScrollView.FOCUS_UP));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.HOME;
        setAdSlider();
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }

    private int[] setADPages(TextView view) {
        int[] adImages = {R.drawable.tp_main_banner1_01,R.drawable.tp_main_banner1_02};

        view.setText(getString(R.string.txt_null, adImages.length+""));

        return adImages;
    }
    private void setCurrentADPage(TextView pageCounter, int position) {
        pageCounter.setText(getString(R.string.txt_null, (position+1)+""));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setCustomToyList(RecyclerView recyclerView) {
        ArrayList<ListItemCustomToy> mList = new ArrayList<>();
        new DatabaseRequest(getContext(), result -> {
            if (result[0].equals("NOT_FOUND")) {
                return;
            }
            try {
                JSONArray goodsArray = new JSONArray(result[0]);
                for (int index=0; index<goodsArray.length(); index++) {
                    ListItemCustomToy item = new ListItemCustomToy();
                    item.setItem(goodsArray.getJSONObject(index));
                    mList.add(item);
                }
                recyclerView.setAdapter(new ListAdapterCustomToy(mList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_CUSTOM_GOODS.name());
    }

    private void setRankingToyList(RecyclerView recyclerView, int categoryId) {
        ArrayList<ListItemMainRankingToy> mList = new ArrayList<>();
        new DatabaseRequest(getContext(), result -> {
            if (result[0].equals("NOT_FOUND")) {
                return;
            }
            try {
                JSONArray goodsArray = new JSONArray(result[0]);
                for (int index=0; index<goodsArray.length(); index++) {
                    ListItemMainRankingToy item = new ListItemMainRankingToy();
                    item.setItem(goodsArray.getJSONObject(index));
                    mList.add(item);
                }
                recyclerView.setAdapter(new ListAdptMainRankingToy(mList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_RANKING_GOODS.name());
    }

    private void setNewToyList(RecyclerView recyclerView) {
        ArrayList<ListItemNewToy> mList = new ArrayList<>();
        new DatabaseRequest(getContext(), result -> {
            if (result[0].equals("NOT_FOUND")) {
                return;
            }
            try {
                JSONArray goodsArray = new JSONArray(result[0]);
                for (int index=0; index<goodsArray.length(); index++) {
                    ListItemNewToy item = new ListItemNewToy();
                    item.setItem1(goodsArray.getJSONObject(index));
                    index++;
                    item.setItem2(goodsArray.getJSONObject(index));
                    mList.add(item);
                }
                recyclerView.setAdapter(new ListAdapterNewToy(mList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_NEW_GOODS.name());
    }

    private void setReviewToyList(RecyclerView recyclerView) {
        ArrayList<ListItemReviewToy> mList = new ArrayList<>();
        new DatabaseRequest(getContext(), result -> {
            if (result[0].equals("NOT_FOUND")) {
                return;
            }
            try {
                JSONArray goodsArray = new JSONArray(result[0]);
                for (int index=0; index<goodsArray.length(); index++) {
                    ListItemReviewToy item = new ListItemReviewToy();
                    item.setItem(goodsArray.getJSONObject(index));
                    mList.add(item);
                }
                recyclerView.setAdapter(new ListAdapterReviewToy(mList, item -> {
                    goodsReview = item;
                    Intent intent = new Intent(requireContext(), ReviewShowActivity.class);
                    intent.putExtra("CALL_TYPE", TAG);
                    startActivity(intent);
                }));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_POPULAR_REVIEW.name());
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
                timer.cancel();
                FragmentManager fragmentManager = getParentFragmentManager();
                SearchFragment searchFragment = new SearchFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_PAGE.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(getId(), searchFragment).commit();
                break;
            default:
                break;
        }
    };

    int currentPage = 0;
    public void setAdSlider() {
        Handler handler = new Handler();
        Runnable update = () -> {
            if (currentPage == Objects.requireNonNull(adView.getAdapter()).getItemCount()) {
                currentPage = 1000;
            }
            adView.setCurrentItem(currentPage);
            currentPage++;
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
}