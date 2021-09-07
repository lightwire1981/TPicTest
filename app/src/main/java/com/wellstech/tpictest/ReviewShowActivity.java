package com.wellstech.tpictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.wellstech.tpictest.fragments.MyPageReviewFragment;
import com.wellstech.tpictest.list_code.ListAdptShowPhoto;
import com.wellstech.tpictest.list_code.ListItemReviewToy;
import com.wellstech.tpictest.utils.ZoomOutPageTransformer;

import java.util.ArrayList;

public class ReviewShowActivity extends AppCompatActivity {
    private ListItemReviewToy reviewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_show);
        getData();
        setPhtoView();
        setWidget();
    }

    private void getData() {
        switch (getIntent().getStringExtra("CALL_TYPE")) {
            case "PhotoActivity":
                reviewData = PhotoActivity.goodsReview;
                break;
            case "GoodsInfoActivity":
                reviewData = GoodsInfoActivity.goodsReview;
                break;
            case "MyPageReviewFragment":
                reviewData = MyPageReviewFragment.goodsReview;
                break;
        }
    }

    private void setPhtoView() {
        ArrayList<String> photoList = new ArrayList<>();
        switch (Integer.parseInt(reviewData.getPhotoCount())) {
            case 1:
                photoList.add(reviewData.getUrlImg1());
                break;
            case 2:
                photoList.add(reviewData.getUrlImg1());
                photoList.add(reviewData.getUrlImg2());
                break;
            case 3:
                photoList.add(reviewData.getUrlImg1());
                photoList.add(reviewData.getUrlImg2());
                photoList.add(reviewData.getUrlImg3());
                break;
            case 4:
                photoList.add(reviewData.getUrlImg1());
                photoList.add(reviewData.getUrlImg2());
                photoList.add(reviewData.getUrlImg3());
                photoList.add(reviewData.getUrlImg4());
                break;
            case 5:
                photoList.add(reviewData.getUrlImg1());
                photoList.add(reviewData.getUrlImg2());
                photoList.add(reviewData.getUrlImg3());
                photoList.add(reviewData.getUrlImg4());
                photoList.add(reviewData.getUrlImg5());
                break;
        }
        ViewPager2 photoView = findViewById(R.id.vPgrShowReviewImages);
        DotsIndicator photoIndicator = findViewById(R.id.indCtrShowCurrentPhoto);
        photoView.setAdapter(new ListAdptShowPhoto(photoList));
        photoView.setPageTransformer(new ZoomOutPageTransformer());
        photoIndicator.setViewPager2(photoView);
    }

    private void setWidget() {
        findViewById(R.id.iBtnReviewShowExit).setOnClickListener(view -> finish());
        ((RatingBar)findViewById(R.id.rtnBrShowEvaluate)).setRating(Float.parseFloat(reviewData.getNumberRate()));
        ((TextView)findViewById(R.id.tVwShowEvaluate)).setText(reviewData.getNumberRate());
        ((TextView)findViewById(R.id.tVwShowLike)).setText(getString(R.string.txt_goods_info_review_like_template, reviewData.getNumberLike()));
        ((TextView)findViewById(R.id.tVwShowReviewerName)).setText(reviewData.getReviewerName());
        ((TextView)findViewById(R.id.tVwShowReviewDate)).setText(reviewData.getDate());
        ((TextView)findViewById(R.id.tVwShowReview)).setText(reviewData.getCommentReview());
    }
}