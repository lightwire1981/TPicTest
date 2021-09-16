package com.auroraworld.toypic.list_code;

import org.json.JSONException;
import org.json.JSONObject;

public class ListItemMainRankingToy {
    private JSONObject item;
    private String goodsId;
    private String goodsName;
    private String goodsRate;
    private String ranking;
    private String goodsImgUrl;


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

    public String getGoodsRate() {
        return goodsRate;
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
    public void setGoodsRate(String goodsRate) {
        this.goodsRate = goodsRate;
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

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }


}
