package com.wellstech.tpictest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.wellstech.tpictest.list_code.ListAdapterGoodsImage;
import com.wellstech.tpictest.utils.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GoodsInfoActivity extends AppCompatActivity {

    private JSONObject GoodsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        getGoodsInfo();
        setWidget();
    }

    private void getGoodsInfo() {
        try {
            GoodsInfo = new JSONArray(getIntent().getStringExtra("goodsInfo")).getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setWidget() {
        ViewPager2 vpgrGoodsImage = findViewById(R.id.vPgrGoodsImages);
        TextView currentImgPage = findViewById(R.id.tVwGoodsInfoCImgPage);
        TextView totalImgPage = findViewById(R.id.tVwGoodsInfoTImgPage);
        TextView goodsName = findViewById(R.id.tVwGoodsInfoName);
        TextView totalEvalCount = findViewById(R.id.tVwGoodsInfoTotalEval);
        RatingBar rtnbrTotalEval = findViewById(R.id.rtnBrGoodsInfoTotalEval);
        TextView goodsPrice = findViewById(R.id.tVwGoodsInfoPrice);
        TextView goodsReviewJump = findViewById(R.id.tVwGoodsInfoJumpReview);
        TextView goodsReviewCount = findViewById(R.id.tVwGoodsInfoReviewCount);
        TextView reviewTotalEvalCount = findViewById(R.id.tVwGoodsInfoReviewTotalEval);
        com.hedgehog.ratingbar.RatingBar goodsTotalEval = findViewById(R.id.rtnBrGoodsInfoReviewTotalEval);
        goodsTotalEval.halfStar(true);
        TextView eval5Percentage = findViewById(R.id.tVwGoodsInfoEval5);
        TextView eval4Percentage = findViewById(R.id.tVwGoodsInfoEval4);
        TextView eval3Percentage = findViewById(R.id.tVwGoodsInfoEval3);
        TextView eval2Percentage = findViewById(R.id.tVwGoodsInfoEval2);
        TextView eval1Percentage = findViewById(R.id.tVwGoodsInfoEval1);
        ProgressBar eval5Progress = findViewById(R.id.pgBrGoodsInfoEval5);
        ProgressBar eval4Progress = findViewById(R.id.pgBrGoodsInfoEval4);
        ProgressBar eval3Progress = findViewById(R.id.pgBrGoodsInfoEval3);
        ProgressBar eval2Progress = findViewById(R.id.pgBrGoodsInfoEval2);
        ProgressBar eval1Progress = findViewById(R.id.pgBrGoodsInfoEval1);

        findViewById(R.id.iBtnGoodsInfoBack).setOnClickListener(v -> finish());

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
}