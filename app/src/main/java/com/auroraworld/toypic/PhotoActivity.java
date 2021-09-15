package com.auroraworld.toypic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.auroraworld.toypic.list_code.ListAdptPhotoThumbnail;
import com.auroraworld.toypic.list_code.ListItemPhotoThumbnail;
import com.auroraworld.toypic.list_code.ListItemReviewImage;
import com.auroraworld.toypic.list_code.ListItemReviewToy;
import com.auroraworld.toypic.list_code.RecyclerDecoration;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {

    private ArrayList<ListItemReviewToy> reviewInfo;
    private ArrayList<ListItemReviewImage> photoInfo;
    private RecyclerView photoListView;

    public static ListItemReviewToy goodsReview;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        getData();
        setWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPhotoList();
    }

    private void setWidget() {
        photoListView = findViewById(R.id.rclVwPhotoThumbnail);
        setLayoutManager(photoListView);
        findViewById(R.id.iBtnPhotoExit).setOnClickListener(v -> finish());
    }

    private void getData() {
        reviewInfo = GoodsInfoActivity.reviewInfo;
        photoInfo = GoodsInfoActivity.imgList;
//        String reviewInfo = getIntent().getStringExtra("reviewInfo");
//        String photoInfo = getIntent().getStringExtra("photoInfo");
//        reviewInfo = (ArrayList<ListItemReviewToy>) getIntent().getSerializableExtra("reviewInfo");
//        photoInfo = (ArrayList<ListItemReviewImage>) getIntent().getSerializableExtra("photoInfo");

    }

    private void setPhotoList() {
        ArrayList<ListItemPhotoThumbnail> mList = new ArrayList<>();
        int count = photoInfo.size();
        int quotient = count/3;
        int remainder = count%3;
        int index = 0;
        if (quotient > 0) {
            for(int x=0; x < quotient; x++) {
                ListItemPhotoThumbnail item = new ListItemPhotoThumbnail();
                item.setItemCount(3);
                item.setPhoto1Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset1(photoInfo.get(index).getDataOffset());
                index++;
                item.setPhoto2Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset2(photoInfo.get(index).getDataOffset());
                index++;
                item.setPhoto3Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset3(photoInfo.get(index).getDataOffset());
                index++;
                mList.add(item);
            }
        }
        ListItemPhotoThumbnail item = new ListItemPhotoThumbnail();
        switch (remainder) {
            case 1:
                item.setItemCount(1);
                item.setPhoto1Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset1(photoInfo.get(index).getDataOffset());
                break;
            case 2:
                item.setItemCount(2);
                item.setPhoto1Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset1(photoInfo.get(index).getDataOffset());
                index++;
                item.setPhoto2Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset2(photoInfo.get(index).getDataOffset());
                break;
        }
        mList.add(item);
        photoListView.setAdapter(new ListAdptPhotoThumbnail(mList, dataOffset -> {
            goodsReview = reviewInfo.get(dataOffset);
            Intent intent = new Intent(getBaseContext(), ReviewShowActivity.class);
            intent.putExtra("CALL_TYPE", TAG);
            startActivity(intent);
        }));
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}