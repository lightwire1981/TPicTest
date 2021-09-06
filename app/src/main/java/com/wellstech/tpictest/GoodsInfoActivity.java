package com.wellstech.tpictest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.wellstech.tpictest.db.DBRequestType;
import com.wellstech.tpictest.db.DatabaseRequest;
import com.wellstech.tpictest.list_code.ListAdapterGoodsImage;
import com.wellstech.tpictest.list_code.ListAdapterNull;
import com.wellstech.tpictest.list_code.ListAdapterReview;
import com.wellstech.tpictest.list_code.ListAdapterReviewToy;
import com.wellstech.tpictest.list_code.ListItemReviewToy;
import com.wellstech.tpictest.list_code.RecyclerDecoration;
import com.wellstech.tpictest.utils.CustomDialog;
import com.wellstech.tpictest.utils.PreferenceSetting;
import com.wellstech.tpictest.utils.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GoodsInfoActivity extends AppCompatActivity {

    //region ValueSetting
    private JSONObject GoodsInfo;
    private ViewPager2 vpgrGoodsImage;
    private RecyclerView reviewListView;
    private TextView totalImgPage, currentImgPage, goodsName;
    private TextView eval5Percentage, eval4Percentage, eval3Percentage, eval2Percentage, eval1Percentage;
    private ProgressBar eval5Progress, eval4Progress, eval3Progress, eval2Progress, eval1Progress;
    private TextView totalEvalCount;
    private RatingBar rtnbrTotalEval;
    private TextView goodsReviewCount, reviewTotalEvalCount;
    private com.hedgehog.ratingbar.RatingBar goodsTotalEval;
    private TextView goodsPrice, goodsReviewJump;

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
                ListAdapterNull adapterNull = new ListAdapterNull();
                reviewListView.setAdapter(adapterNull);
                return;
            }
            try {
                JSONArray reviewList = new JSONArray(result[0]);
                ArrayList<ListItemReviewToy> list = new ArrayList<>();
                for (int i=0; i<reviewList.length(); i++) {
                    ListItemReviewToy item = new ListItemReviewToy();
                    item.setItem(reviewList.getJSONObject(i));
                    list.add(item);
                }
                ListAdapterReview adapter = new ListAdapterReview(list, reviewData -> Log.i(TAG, reviewData.toString()));
                reviewListView.setAdapter(adapter);
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
            Intent intent = new Intent(getBaseContext(), ReviewActivity.class);
            intent.putExtra("goodsInfo", GoodsInfo.toString());
            startActivity(intent);
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