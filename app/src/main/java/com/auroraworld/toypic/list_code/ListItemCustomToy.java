package com.auroraworld.toypic.list_code;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

public class ListItemCustomToy {
    private JSONObject item;
    private String goodsId;
    private String goodsName;
    private String goodsRate;
    private boolean isLike;
    private String goodsImgUrl;
    private Drawable imgDrawable;

    public JSONObject getItem() {
        return item;
    }

    public void setItem(JSONObject item) {
        this.item = item;
        setGoodsId();
        setGoodsName();
        setGoodsRate();
        setGoodsImgUrl();
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

    public void setImgDrawable(Drawable img) {
        imgDrawable = img;
    }

    public void setGoodsName() {
        try {
            this.goodsName = item.getString("goodsNm");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsName(String pName) {
        goodsName = pName;
    }

    public void setGoodsRate() {
        try {
            String temp = item.getString("evaluate_avg");
            if(temp.equals("null")) {
                temp = "3.0";
            }
            this.goodsRate = temp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsRate(String pNumber) {
        goodsRate = pNumber;
    }

    public void setLike(boolean like) {
        isLike = like;
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

    public boolean isLike() {
        return isLike;
    }

    public Drawable getImgDrawable() {
        return imgDrawable;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsRate() {
        return goodsRate;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }


}
