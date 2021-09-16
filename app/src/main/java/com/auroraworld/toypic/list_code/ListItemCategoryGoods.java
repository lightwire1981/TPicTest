package com.auroraworld.toypic.list_code;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ListItemCategoryGoods {
    private JSONObject item;
    private String goodsId;
    private String goodsImgUrl;
    private String goodsName;
    private String goodsEvaluate;
    private String goodsReviewCount;
    private String goodsPrice;


    public JSONObject getItem() {
        return item;
    }

    public void setItem(JSONObject item) {
        this.item = item;
        setGoodsId();
        setGoodsImgUrl();
        setGoodsName();
        setGoodsEvaluate();
        setGoodsReviewCount();
        setGoodsPrice();
    }

    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId() {
        try {
            this.goodsId = item.getString("goodsNo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }
    public void setGoodsImgUrl() {
        try {
            this.goodsImgUrl = item.getString("detailImageData");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName() {
        try {
            this.goodsName = item.getString("goodsNm");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsEvaluate() {
        return goodsEvaluate;
    }
    public void setGoodsEvaluate() {
        try {
            String temp = item.getString("evaluate_avg");
            if(temp.equals("null")) {
                temp = "3.0";
            }
            this.goodsEvaluate = temp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsEvaluate(String goodsEvaluate) {
        this.goodsEvaluate = goodsEvaluate;
    }

    public String getGoodsReviewCount() {
        return goodsReviewCount;
    }
    public void setGoodsReviewCount() {
        try {
            String temp = item.getString("review_count");
            if(temp.equals("null")) {
                temp = "0";
            }
            this.goodsReviewCount = temp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsReviewCount(String goodsReviewCount) {
        this.goodsReviewCount = goodsReviewCount;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }
    public void setGoodsPrice() {
        try {
            int value = (int)Float.parseFloat(item.getString("fixedPrice"));
            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            String price = decimalFormat.format(value);
            this.goodsPrice = price + "원";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
