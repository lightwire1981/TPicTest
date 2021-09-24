package com.auroraworld.toypic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.list_code.ListAdapterGoodsImage;
import com.auroraworld.toypic.list_code.ListAdapterNull;
import com.auroraworld.toypic.list_code.ListAdapterReview;
import com.auroraworld.toypic.list_code.ListAdptReviewImage;
import com.auroraworld.toypic.list_code.ListItemReviewImage;
import com.auroraworld.toypic.list_code.ListItemReviewToy;
import com.auroraworld.toypic.list_code.RecyclerDecoration;
import com.auroraworld.toypic.utils.CustomDialog;
import com.auroraworld.toypic.utils.PreferenceSetting;
import com.auroraworld.toypic.utils.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GoodsInfoActivity extends AppCompatActivity {

    //region ValueSetting
    private JSONObject GoodsInfo;
    private ViewPager2 vpgrGoodsImage;
    private RecyclerView reviewImageListView, reviewListView;
    private TextView totalImgPage, currentImgPage, goodsName;
    private TextView eval5Percentage, eval4Percentage, eval3Percentage, eval2Percentage, eval1Percentage;
    private ProgressBar eval5Progress, eval4Progress, eval3Progress, eval2Progress, eval1Progress;
    private TextView totalEvalCount;
    private RatingBar rtnbrTotalEval;
    private TextView goodsReviewCount, reviewTotalEvalCount;
    private com.hedgehog.ratingbar.RatingBar goodsTotalEval;
    private TextView goodsPrice, goodsReviewJump;
    private TextView reviewPhotoCount;
    private CheckBox ckbGoodsLike;

    public static ArrayList<ListItemReviewToy> reviewInfo;
    public static ArrayList<ListItemReviewImage> imgList;

    public static ListItemReviewToy goodsReview;
    public static JSONObject reviewObject;

    private final String TAG = getClass().getSimpleName();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        setWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGoodsInfo();

        hideNavigationBar();
    }

    private void getGoodsInfo() {
        try {
            GoodsInfo = new JSONArray(getIntent().getStringExtra("goodsInfo")).getJSONObject(0);
            setGoodsInfo();
            getReviews(GoodsInfo.getString("goodsNo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getReviews(String goodsNo) {
        JSONObject goodsData = new JSONObject();
        try {
            goodsData.put("goodsNo", goodsNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new DatabaseRequest(getBaseContext(), result -> {
            if (result[0].equals("null")) {
                ListAdapterNull adapterNull = new ListAdapterNull(getString(R.string.txt_review_null));
                reviewListView.setAdapter(adapterNull);
                adapterNull = new ListAdapterNull(getString(R.string.txt_image_null));
                reviewImageListView.setAdapter(adapterNull);
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
                        Intent intent = new Intent(getBaseContext(), ReviewShowActivity.class);
                        intent.putExtra("CALL_TYPE", TAG);
                        startActivity(intent);
                    }

                    @Override
                    public void onModifyReview(JSONObject jsonObject) {
                        reviewObject = jsonObject;
                        Intent intent = new Intent(getBaseContext(), ReviewWriteActivity.class);
                        intent.putExtra("CALL_TYPE", TAG);
                        intent.putExtra("goodsInfo", GoodsInfo.toString());
                        startActivity(intent);
                    }

                    @Override
                    public void onDeleteReview(String reviewId) {
                        new CustomDialog(GoodsInfoActivity.this, CustomDialog.DIALOG_CATEGORY.DELETE_REVIEW_CONFIRM, (response, data) -> {
                            if (response) {
                                JSONObject object = new JSONObject();
                                try {
                                    object.put("review_id", reviewId);
                                    new DatabaseRequest(getBaseContext(), result1 -> {
                                        if (result1[0].equals("UPDATE_OK")) {
                                            Toast.makeText(getBaseContext(), "리뷰 삭제 완료", Toast.LENGTH_SHORT).show();
                                            getGoodsInfo();
                                        } else {
                                            Toast.makeText(getBaseContext(), "리뷰 삭제 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    }).execute(DBRequestType.DELETE_REVIEW.name(), object.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
                    }
                });
                reviewListView.setAdapter(adapter);

                imgList = new ArrayList<>();
                int index = 0;
                for (ListItemReviewToy item : reviewInfo) {
                    ListItemReviewImage imgItem = new ListItemReviewImage();
                    switch (Integer.parseInt(item.getPhotoCount())) {
                        case 5:
                            imgItem.setPhotoUri(item.getUrlImg5());
                            imgItem.setDataOffset(index);
                            imgList.add(imgItem);
                        case 4:
                            imgItem = new ListItemReviewImage();
                            imgItem.setPhotoUri(item.getUrlImg4());
                            imgItem.setDataOffset(index);
                            imgList.add(imgItem);
                        case 3:
                            imgItem = new ListItemReviewImage();
                            imgItem.setPhotoUri(item.getUrlImg3());
                            imgItem.setDataOffset(index);
                            imgList.add(imgItem);
                        case 2:
                            imgItem = new ListItemReviewImage();
                            imgItem.setPhotoUri(item.getUrlImg2());
                            imgItem.setDataOffset(index);
                            imgList.add(imgItem);
                        case 1:
                            imgItem = new ListItemReviewImage();
                            imgItem.setPhotoUri(item.getUrlImg1());
                            imgItem.setDataOffset(index);
                            imgList.add(imgItem);
                            break;
                    }
                    index++;
                }
                reviewPhotoCount.setText(getString(R.string.txt_goods_info_review_photo, imgList.size()+""));
                reviewPhotoCount.setOnClickListener(view -> {
                    Intent intent = new Intent(getBaseContext(), PhotoActivity.class);
                    startActivity(intent);
                });
                ListAdptReviewImage adptReviewImage = new ListAdptReviewImage(imgList, () -> {
//                    JSONArray imgArray = new JSONArray();
//                    for (ListItemReviewImage item : imgList) {
//                        JSONObject imgObject = new JSONObject();
//                        try {
//                            imgObject.put("url", item.getPhotoUri());
//                            imgObject.put("offset", item.getDataOffset());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        imgArray.put(imgObject);
//                    }
                    Intent intent = new Intent(getBaseContext(), PhotoActivity.class);
//                    intent.putExtra("photoInfo", imgArray.toString());
//                    intent.putExtra("reviewInfo", reviewList.toString());
                    startActivity(intent);
                });
                reviewImageListView.setAdapter(adptReviewImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).execute(DBRequestType.GET_REVIEW_LIST.name(), goodsData.toString());
    }

    private void setWidget() {
        vpgrGoodsImage = findViewById(R.id.vPgrGoodsImages);
        currentImgPage = findViewById(R.id.tVwGoodsInfoCImgPage);
        totalImgPage = findViewById(R.id.tVwGoodsInfoTImgPage);
        goodsName = findViewById(R.id.tVwGoodsInfoName);
        totalEvalCount = findViewById(R.id.tVwGoodsInfoTotalEval);
        rtnbrTotalEval = findViewById(R.id.rtnBrGoodsInfoTotalEval);
        goodsPrice = findViewById(R.id.tVwGoodsInfoPrice);
        goodsReviewJump = findViewById(R.id.tVwGoodsInfoJumpReview);
        goodsReviewCount = findViewById(R.id.tVwGoodsInfoReviewCount);
        reviewTotalEvalCount = findViewById(R.id.tVwGoodsInfoReviewTotalEval);
        goodsTotalEval = findViewById(R.id.rtnBrGoodsInfoReviewTotalEval);
        goodsTotalEval.halfStar(true);
        eval5Percentage = findViewById(R.id.tVwGoodsInfoEval5);
        eval4Percentage = findViewById(R.id.tVwGoodsInfoEval4);
        eval3Percentage = findViewById(R.id.tVwGoodsInfoEval3);
        eval2Percentage = findViewById(R.id.tVwGoodsInfoEval2);
        eval1Percentage = findViewById(R.id.tVwGoodsInfoEval1);
        eval5Progress = findViewById(R.id.pgBrGoodsInfoEval5);
        eval4Progress = findViewById(R.id.pgBrGoodsInfoEval4);
        eval3Progress = findViewById(R.id.pgBrGoodsInfoEval3);
        eval2Progress = findViewById(R.id.pgBrGoodsInfoEval2);
        eval1Progress = findViewById(R.id.pgBrGoodsInfoEval1);
        reviewPhotoCount = findViewById(R.id.tVwGoodsInfoPhotoCount);
        reviewPhotoCount.setText(getString(R.string.txt_goods_info_review_photo, "0"));

        reviewImageListView = findViewById(R.id.rcyclVwGoodsInfoReviewPhoto);
        setLayoutManager(reviewImageListView);

        reviewListView = findViewById(R.id.rcyclVwGoodsInfoReviewList);
        setLayoutManager(reviewListView);

        findViewById(R.id.iBtnGoodsInfoBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnGoodsInfoWriteReview).setOnClickListener(view -> {
            if (PreferenceSetting.loadPreference(getBaseContext(), PreferenceSetting.PREFERENCE_KEY.USER_INFO).isEmpty()) {
                new CustomDialog(GoodsInfoActivity.this, CustomDialog.DIALOG_CATEGORY.LOGIN, (response, data) -> {
                    if (response) {
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        hideNavigationBar();
                    }
                }).show();
                return;
            }
            Intent intent = new Intent(getBaseContext(), ReviewWriteActivity.class);
            intent.putExtra("CALL_TYPE", TAG);
            intent.putExtra("goodsInfo", GoodsInfo.toString());
            startActivity(intent);
        });
        ckbGoodsLike = findViewById(R.id.cKbGoodsInfoLike);
        ckbGoodsLike.setOnCheckedChangeListener((checkBox, isChecked) -> {
            try {
                JSONObject userInfo = new JSONObject(PreferenceSetting.loadPreference(getBaseContext(), PreferenceSetting.PREFERENCE_KEY.USER_INFO));
                String userId = userInfo.getString("id");
                String goodsNo = GoodsInfo.getString("goodsNo");
                JSONObject likeData = new JSONObject();
                likeData.put("id", userId);
                likeData.put("goodsNo", goodsNo);
                if (isChecked) {
                    likeData.put("like", "1");
                } else {
                    likeData.put("like", "0");
                }
                new DatabaseRequest(getBaseContext(), result -> {
                    switch (result[0]) {
                        case "INSERT_FAIL":
                        case "UPDATE_FAIL":
                            Toast.makeText(getBaseContext(), "정보 처리 실패", Toast.LENGTH_SHORT).show();
                            break;
                        case "INSERT_OK":
                        case "UPDATE_OK":
                            if (isChecked) {
                                Log.i(TAG, "좋아요 등록");
                            } else {
                                Log.i(TAG, "좋아요 해제");
                            }
                            break;
                    }
                }).execute(DBRequestType.LIKE.name(), likeData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void setGoodsInfo() {
        ArrayList<String> imgList = setGoodsImages(totalImgPage);
        vpgrGoodsImage.setAdapter(new ListAdapterGoodsImage(imgList));
        vpgrGoodsImage.setPageTransformer(new ZoomOutPageTransformer());
        vpgrGoodsImage.setCurrentItem(996);
        setCurrentImagePage(currentImgPage, 0);
        vpgrGoodsImage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentImagePage(currentImgPage, position%imgList.size());
            }
        });

        try {
            goodsName.setText(GoodsInfo.getString("goodsNm"));
            String evaluateAvg =GoodsInfo.getString("evaluate_avg");
            if (evaluateAvg.equals("null")) {
                evaluateAvg = "3.0";
                eval5Percentage.setText("0%");
                eval5Progress.setProgress(0);
                eval4Percentage.setText("0%");
                eval4Progress.setProgress(0);
                eval3Percentage.setText("100%");
                eval3Progress.setProgress(100);
                eval2Percentage.setText("0%");
                eval2Progress.setProgress(0);
                eval1Percentage.setText("0%");
                eval1Progress.setProgress(0);
            } else {
                ArrayList<String> evaluateList = new ArrayList<>();
                evaluateList.add(GoodsInfo.getString("e1"));
                evaluateList.add(GoodsInfo.getString("e2"));
                evaluateList.add(GoodsInfo.getString("e3"));
                evaluateList.add(GoodsInfo.getString("e4"));
                evaluateList.add(GoodsInfo.getString("e5"));

                ArrayList<Integer> evaluateDecimal = new ArrayList<>();
                for (String evalValue : evaluateList) {
                    if (evalValue.equals("null")) evalValue = "0";
                    evaluateDecimal.add(Integer.parseInt(evalValue));
                }
                int evalSum = 0;
                for (int v : evaluateDecimal) {
                    evalSum += v;
                }
                ArrayList<TextView> evalPercentView = new ArrayList<>();
                evalPercentView.add(eval1Percentage);
                evalPercentView.add(eval2Percentage);
                evalPercentView.add(eval3Percentage);
                evalPercentView.add(eval4Percentage);
                evalPercentView.add(eval5Percentage);

                ArrayList<ProgressBar> evalProgressView = new ArrayList<>();
                evalProgressView.add(eval1Progress);
                evalProgressView.add(eval2Progress);
                evalProgressView.add(eval3Progress);
                evalProgressView.add(eval4Progress);
                evalProgressView.add(eval5Progress);

                for (int index=0; index < evalPercentView.size(); index++) {
                    if (evaluateDecimal.get(index) == 0) {
                        evalPercentView.get(index).setText("0%");
                        evalProgressView.get(index).setProgress(0);
                    } else {
                        int percentage = (evalSum*100)/evaluateDecimal.get(index);
                        evalPercentView.get(index).setText(percentage+"%");
                        evalProgressView.get(index).setProgress(percentage);
                    }
                }
            }
            totalEvalCount.setText(evaluateAvg);
            rtnbrTotalEval.setRating(Float.parseFloat(evaluateAvg));
            reviewTotalEvalCount.setText(evaluateAvg);
            goodsTotalEval.setStar(Float.parseFloat(evaluateAvg));

            DecimalFormat decimalFormat = new DecimalFormat("###,###");

            String rCount = GoodsInfo.getString("review_count");
            if (rCount.equals("null")) rCount = "0";
            goodsReviewJump.setText(decimalFormat.format(Integer.parseInt(rCount)));
            goodsReviewCount.setText(decimalFormat.format(Integer.parseInt(rCount)));

            int value = (int)Float.parseFloat(GoodsInfo.getString("fixedPrice"));
            String price = decimalFormat.format(value);
            goodsPrice.setText(getString(R.string.txt_goods_info_price_template, price));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> setGoodsImages(TextView pageCountView) {
        ArrayList<String> urls = new ArrayList<>();
        try {
            urls.add(GoodsInfo.getString("detailImageData"));
            urls.add(GoodsInfo.getString("listImageData"));
            urls.add(GoodsInfo.getString("mainImageData"));
            urls.add(GoodsInfo.getString("add1ImageData"));
            urls.add(GoodsInfo.getString("add2ImageData"));
            urls.add(GoodsInfo.getString("magnifyImageData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pageCountView.setText(String.valueOf(urls.size()));
        return urls;
    }
    private void setCurrentImagePage(TextView pageCounter, int position) {
        pageCounter.setText(getString(R.string.txt_null, (position+1)+""));
    }

    @SuppressLint("NonConstantResourceId")
    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        switch (recyclerView.getId()) {
            case R.id.rcyclVwGoodsInfoReviewPhoto:
                recyclerView.addItemDecoration(new RecyclerDecoration(25, 0));
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                break;
            case R.id.rcyclVwGoodsInfoReviewList:
                recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                break;
        }
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}