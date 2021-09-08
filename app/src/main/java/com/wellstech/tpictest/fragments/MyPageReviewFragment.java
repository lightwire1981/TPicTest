package com.wellstech.tpictest.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.MainActivity;
import com.wellstech.tpictest.R;
import com.wellstech.tpictest.ReviewShowActivity;
import com.wellstech.tpictest.ReviewWriteActivity;
import com.wellstech.tpictest.db.DBRequestType;
import com.wellstech.tpictest.db.DatabaseRequest;
import com.wellstech.tpictest.list_code.ListAdapterNull;
import com.wellstech.tpictest.list_code.ListAdapterReview;
import com.wellstech.tpictest.list_code.ListItemReviewToy;
import com.wellstech.tpictest.list_code.RecyclerDecoration;
import com.wellstech.tpictest.utils.CustomDialog;
import com.wellstech.tpictest.utils.PreferenceSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView myReviewListVw;
    public static ArrayList<ListItemReviewToy> reviewInfo;
    public static ListItemReviewToy goodsReview;
    public static JSONObject reviewObject;

    private ProgressDialog progressDialog;

    private final String TAG = getClass().getSimpleName();

    public MyPageReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageReview.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageReviewFragment newInstance(String param1, String param2) {
        MyPageReviewFragment fragment = new MyPageReviewFragment();
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
    public void onResume() {
        super.onResume();
        MainActivity.CURRENT_PAGE = MainActivity.PAGES.MY_REVIEW;
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setOnDismissListener(dialogInterface -> hideNavigationBar());
        progressDialog.setMessage("리뷰를 조회 중입니다...");
        progressDialog.show();
        getMyReviews();
    }

    private void getMyReviews() {
        String userInfo = PreferenceSetting.loadPreference(requireContext(), PreferenceSetting.PREFERENCE_KEY.USER_INFO);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", new JSONObject(userInfo).get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new DatabaseRequest(requireContext(), result -> {
            if (result[0].equals("null")) {
                ListAdapterNull adapterNull = new ListAdapterNull(getString(R.string.txt_review_null));
                myReviewListVw.setAdapter(adapterNull);
                return;
            }
            try {
                JSONArray reviewList = new JSONArray(result[0]);
                reviewInfo = new ArrayList<>();
                for (int i=0; i<reviewList.length(); i++) {
                    ListItemReviewToy item = new ListItemReviewToy();
                    item.setItem(reviewList.getJSONObject(i));
                    reviewInfo.add(item);
                }
                ListAdapterReview adapter = new ListAdapterReview(reviewInfo, new ListAdapterReview.SelectReviewListener() {
                    @Override
                    public void onSelectReview(ListItemReviewToy item) {
                        goodsReview = item;
                        Intent intent = new Intent(requireContext(), ReviewShowActivity.class);
                        intent.putExtra("CALL_TYPE", TAG);
                        startActivity(intent);
                    }

                    @Override
                    public void onModifyReview(JSONObject jsonObject) {
                        reviewObject = jsonObject;
                        Intent intent = new Intent(requireContext(), ReviewWriteActivity.class);
                        intent.putExtra("CALL_TYPE", TAG);
                        startActivity(intent);
                    }

                    @Override
                    public void onDeleteReview(String reviewId) {
                        new CustomDialog(requireActivity(), CustomDialog.DIALOG_CATEGORY.DELETE_REVIEW_CONFIRM, (response, data) -> {
                            if (response) {
                                JSONObject object = new JSONObject();
                                try {
                                    object.put("review_id", reviewId);
                                    new DatabaseRequest(requireContext(), result1 -> {
                                        if (result1[0].equals("UPDATE_OK")) {
                                            Toast.makeText(requireContext(), "리뷰 삭제 완료", Toast.LENGTH_SHORT).show();
                                            progressDialog = new ProgressDialog(requireContext());
                                            progressDialog.setOnDismissListener(dialogInterface -> hideNavigationBar());
                                            progressDialog.setMessage("리뷰를 조회 중입니다...");
                                            progressDialog.show();
                                            getMyReviews();
                                        } else {
                                            Toast.makeText(requireContext(), "리뷰 삭제 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    }).execute(DBRequestType.DELETE_REVIEW.name(), object.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            hideNavigationBar();
                        }).show();
                    }
                });
                myReviewListVw.setAdapter(adapter);
                progressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_MY_REVIEW.name(), jsonObject.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page_review, container, false);
        myReviewListVw = view.findViewById(R.id.rclVwMyReviewList);
        setLayoutManager(myReviewListVw);
        view.findViewById(R.id.iBtnReviewManagerBack).setOnClickListener(view1 -> {
            int fragmentCount = getParentFragmentManager().getBackStackEntryCount();
            MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(fragmentCount-1).getName());
            getParentFragmentManager().popBackStack();
        });
        return view;
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void hideNavigationBar(){
        View decorView = requireActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}