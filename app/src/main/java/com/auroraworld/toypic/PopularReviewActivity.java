package com.auroraworld.toypic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.fragments.HomeFragment;
import com.auroraworld.toypic.list_code.ListAdapterReviewToy;
import com.auroraworld.toypic.list_code.ListItemReviewToy;
import com.auroraworld.toypic.list_code.RecyclerDecoration;
import com.auroraworld.toypic.utils.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PopularReviewActivity extends AppCompatActivity {

    public static ListItemReviewToy goodsReview;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_review);

        RecyclerView popularReviewList = findViewById(R.id.rclVwPopularReview);
        setLayoutManager(popularReviewList);
        setReviewToyList(popularReviewList);
        findViewById(R.id.iBtnPopularReviewBack).setOnClickListener(view -> finish());
        hideNavigationBar();
    }


    private void setReviewToyList(RecyclerView recyclerView) {
        ArrayList<ListItemReviewToy> mList = new ArrayList<>();
        new DatabaseRequest(getBaseContext(), result -> {
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
                    Intent intent = new Intent(getBaseContext(), ReviewShowActivity.class);
                    intent.putExtra("CALL_TYPE", TAG);
                    startActivity(intent);
                }));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_POPULAR_REVIEW.name());
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onBackPressed() {
        new CustomDialog(PopularReviewActivity.this, CustomDialog.DIALOG_CATEGORY.EXIT, (isAppFinish, data) -> hideNavigationBar()).show();
    }
}